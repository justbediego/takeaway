package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.ExceptionEntities;
import com.takeaway.takeaway.business.exception.ExceptionTypes;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.*;
import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import com.takeaway.takeaway.dataaccess.model.enums.ItemApprovalStates;
import com.takeaway.takeaway.dataaccess.model.enums.ItemPublishStates;
import com.takeaway.takeaway.dataaccess.model.geo.Location;
import com.takeaway.takeaway.dataaccess.repository.AttachmentRepository;
import com.takeaway.takeaway.dataaccess.repository.GeolocationRepository;
import com.takeaway.takeaway.dataaccess.repository.ItemRepository;
import com.takeaway.takeaway.dataaccess.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
public class UserItemLogic {

    private final ValidationLogic validationLogic;
    private final ItemRepository itemRepository;
    private final GeolocationRepository geolocationRepository;
    private final LocationRepository locationRepository;
    private final AttachmentLogic attachmentLogic;
    private final AttachmentRepository attachmentRepository;

    public UserItemLogic(ValidationLogic validationLogic, ItemRepository itemRepository, GeolocationRepository geolocationRepository, LocationRepository locationRepository, AttachmentLogic attachmentLogic, AttachmentRepository attachmentRepository) {
        this.validationLogic = validationLogic;
        this.itemRepository = itemRepository;
        this.geolocationRepository = geolocationRepository;
        this.locationRepository = locationRepository;
        this.attachmentLogic = attachmentLogic;
        this.attachmentRepository = attachmentRepository;
    }

    public UUID createItem(UUID userId, CreateItemDto data) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        validationLogic.validateItemTitle(data.getTitle());
        validationLogic.validateItemDescription(data.getDescription());
        ItemCategory itemCategory = validationLogic.validateGetItemCategoryById(data.getItemCategoryId());
        Location newLocation = validationLogic.validateNewLocation(data.getLocation());
        List<CreateAttachmentDto> validPreparedImages = new ArrayList<>();
        for (CreateAttachmentDto imageDto : data.getImages()) {
            validPreparedImages.add(CreateAttachmentDto.builder()
                    .fileData(attachmentLogic.prepareImage(imageDto.getFileData(), null))
                    .filename(imageDto.getFilename())
                    .build());
        }

        // business
        if (newLocation.getGeolocation() != null) {
            geolocationRepository.save(newLocation.getGeolocation());
        }
        locationRepository.save(newLocation);
        Item item = new Item();
        item.setUser(user);
        item.setTitle(data.getTitle());
        item.setDescription(data.getDescription());
        item.setCategory(itemCategory);
        item.setLocation(newLocation);
        // user wants it published
        item.setPublishState(ItemPublishStates.ACTIVE);
        item.setPublishStart(new Date());
        // but it needs approval
        item.setApprovalState(ItemApprovalStates.QUEUE);

        int pictureOrder = 0;
        for (CreateAttachmentDto validImage : validPreparedImages) {
            Attachment attachment = attachmentLogic.createAttachment(validImage, AttachmentTypes.IMAGE);
            attachment.setOrderIndex(pictureOrder++);
            item.getPictures().add(attachment);
        }

        itemRepository.save(item);
        return item.getId();
    }

    public void updateItem(UUID userId, UUID itemId, UpdateItemDto data) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        validationLogic.validateItemTitle(data.getTitle());
        validationLogic.validateItemDescription(data.getDescription());
        ItemCategory itemCategory = validationLogic.validateGetItemCategoryById(data.getItemCategoryId());
        Location newLocation = validationLogic.validateNewLocation(data.getLocation());
        Item item = validationLogic.validateGetModifiableItemById(user.getId(), itemId);

        // business
        if (item.getLocation() != null) {
            if (item.getLocation().getGeolocation() != null) {
                geolocationRepository.delete(item.getLocation().getGeolocation());
            }
            locationRepository.delete(item.getLocation());
        }
        if (newLocation.getGeolocation() != null) {
            geolocationRepository.save(newLocation.getGeolocation());
        }
        locationRepository.save(newLocation);

        item.setTitle(data.getTitle());
        item.setDescription(data.getDescription());
        item.setCategory(itemCategory);
        item.setLocation(newLocation);
        // but it needs approval
        item.setApprovalState(ItemApprovalStates.QUEUE);

        itemRepository.save(item);
    }

    public void unPublishItem(UUID userId, UUID itemId) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        Item item = validationLogic.validateGetModifiableItemById(user.getId(), itemId);
        item.setPublishState(ItemPublishStates.INACTIVE);
        itemRepository.save(item);
    }

    public void rePublishItem(UUID userId, UUID itemId) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        Item item = validationLogic.validateGetModifiableItemById(user.getId(), itemId);
        item.setPublishState(ItemPublishStates.ACTIVE);
        item.setPublishStart(new Date());
        itemRepository.save(item);
    }

    public void deleteItem(UUID userId, UUID itemId) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        Item item = validationLogic.validateGetModifiableItemById(user.getId(), itemId);
        if (item.getLocation() != null) {
            if (item.getLocation().getGeolocation() != null) {
                geolocationRepository.delete(item.getLocation().getGeolocation());
            }
            locationRepository.delete(item.getLocation());
        }
        itemRepository.delete(item);
    }

    public void changeItemAttachmentOrder(UUID userId, UUID itemId, ChangeItemAttachmentOrderDto data) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        Item item = validationLogic.validateGetModifiableItemById(user.getId(), itemId);
        List<UUID> pictureIds = item.getPictures().stream()
                .map(Attachment::getId)
                .collect(Collectors.toList());

        // containing the same elements with any order
        if (!new HashSet<>(pictureIds).equals(new HashSet<>(data.getAttachmentIdsInOrder()))) {
            throw new TakeawayException(ExceptionTypes.INVALID_IDS_TO_REORDER);
        }

        for (Attachment picture : item.getPictures()) {
            picture.setOrderIndex(data.getAttachmentIdsInOrder().indexOf(picture.getId()));
        }
        attachmentRepository.saveAll(item.getPictures());
    }

    public void addAttachmentToItem(UUID userId, UUID itemId, CreateAttachmentDto attachmentDto) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        Item item = validationLogic.validateGetModifiableItemById(user.getId(), itemId);
        CreateAttachmentDto preparedImage = CreateAttachmentDto.builder()
                .fileData(attachmentLogic.prepareImage(attachmentDto.getFileData(), null))
                .filename(attachmentDto.getFilename())
                .build();
        int maxOrder = item.getPictures().stream()
                .mapToInt(Attachment::getOrderIndex)
                .max()
                .orElse(0);
        Attachment attachment = attachmentLogic.createAttachment(preparedImage, AttachmentTypes.IMAGE);
        attachment.setOrderIndex(maxOrder + 1);
        item.getPictures().add(attachment);
        itemRepository.save(item);
    }

    public void deleteAttachmentFromItem(UUID userId, UUID itemId, UUID attachmentId) throws TakeawayException {
        User user = validationLogic.validateGetUserById(userId);
        Item item = validationLogic.validateGetModifiableItemById(user.getId(), itemId);
        Optional<Attachment> foundPicture = item.getPictures().stream()
                .filter(attachment -> attachment.getId().equals(attachmentId))
                .findFirst();
        if (foundPicture.isEmpty()) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.ATTACHMENT,
                    attachmentId.toString()
            );
        }
        attachmentRepository.delete(foundPicture.get());
    }

    public void reportItem(UUID userId, UUID itemId, ReportItemDto reportItemDto) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        Item item = validationLogic.validateGetPublishedItemById(itemId);
        if (reportItemDto.getCategory() == null) {
            throw new TakeawayException(ExceptionTypes.INVALID_REPORT_CATEGORY);
        }
        validationLogic.validateReportDescription(reportItemDto.getDescription());
        if (item.getItemReports().stream()
                .anyMatch(itemReport -> itemReport.getUser().getId() == user.getId())) {
            throw new TakeawayException(ExceptionTypes.ITEM_ALREADY_REPORTED);
        }

        // business
        ItemReport itemReport = new ItemReport();
        itemReport.setUser(user);
        itemReport.setCategory(reportItemDto.getCategory());
        itemReport.setDescription(reportItemDto.getDescription());
        item.getItemReports().add(itemReport);
        itemRepository.save(item);
    }
}
