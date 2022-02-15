package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.*;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.Attachment;
import com.takeaway.takeaway.dataaccess.model.Item;
import com.takeaway.takeaway.dataaccess.model.ItemCategory;
import com.takeaway.takeaway.dataaccess.model.User;
import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import com.takeaway.takeaway.dataaccess.model.enums.ItemApprovalStates;
import com.takeaway.takeaway.dataaccess.model.enums.ItemPublishStates;
import com.takeaway.takeaway.dataaccess.model.geo.Geolocation;
import com.takeaway.takeaway.dataaccess.model.geo.Location;
import com.takeaway.takeaway.dataaccess.repository.GeolocationRepository;
import com.takeaway.takeaway.dataaccess.repository.ItemRepository;
import com.takeaway.takeaway.dataaccess.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class UserItemLogic {

    private final ValidationLogic validationLogic;
    private final ItemRepository itemRepository;
    private final GeolocationRepository geolocationRepository;
    private final LocationRepository locationRepository;
    private final AttachmentLogic attachmentLogic;

    public UserItemLogic(ValidationLogic validationLogic, ItemRepository itemRepository, GeolocationRepository geolocationRepository, LocationRepository locationRepository, AttachmentLogic attachmentLogic) {
        this.validationLogic = validationLogic;
        this.itemRepository = itemRepository;
        this.geolocationRepository = geolocationRepository;
        this.locationRepository = locationRepository;
        this.attachmentLogic = attachmentLogic;
    }

    public UUID createNewItem(UUID userId, CreateItemDto data) throws TakeawayException {
        // validation
        User user = validationLogic.validateGetUserById(userId);
        validationLogic.validateItemTitle(data.getTitle());
        validationLogic.validateItemDescription(data.getDescription());
        ItemCategory itemCategory = validationLogic.validateGetItemCategoryById(data.getItemCategoryId());
        ValidModifyLocationDto validLocationDto = validationLogic.validateGetLocation(data.getLocation());
        List<CreateAttachmentDto> validPreparedImages = new ArrayList<>();
        for (CreateAttachmentDto imageDto : data.getImages()) {
            validPreparedImages.add(CreateAttachmentDto.builder()
                    .fileData(attachmentLogic.prepareImage(imageDto.getFileData(), null))
                    .filename(imageDto.getFilename())
                    .build());
        }

        // business
        Geolocation geolocation = new Geolocation();
        geolocation.setLatitude(validLocationDto.getLatitude());
        geolocation.setLongitude(validLocationDto.getLongitude());
        geolocation.setAccuracyM(validLocationDto.getAccuracyM());
        geolocationRepository.save(geolocation);

        Location location = new Location();
        location.setGeolocation(geolocation);
        location.setCountry(validLocationDto.getCountry());
        location.setState(validLocationDto.getState());
        location.setCity(validLocationDto.getCity());
        location.setTitle(validLocationDto.getTitle());
        location.setStreetName(validLocationDto.getStreetName());
        location.setStreetName2(validLocationDto.getStreetName2());
        location.setHouseNumber(validLocationDto.getHouseNumber());
        location.setAdditionalInfo(validLocationDto.getAdditionalInfo());
        locationRepository.save(location);

        Item item = new Item();
        item.setUser(user);
        item.setLocation(location);
        item.setApprovalState(ItemApprovalStates.QUEUE);
        item.setPublishState(ItemPublishStates.INACTIVE);
        item.setPublishStart(new Date());
        item.setDescription(data.getDescription());
        item.setCategory(itemCategory);
        item.setTitle(data.getTitle());

        for (CreateAttachmentDto validImage : validPreparedImages) {
            Attachment attachment = attachmentLogic.createAttachment(validImage, AttachmentTypes.IMAGE);
            item.getPictures().add(attachment);
        }

        itemRepository.save(item);
        return item.getId();
    }

    public void updateItemDetails(UUID userId, UpdateItemDto data) throws TakeawayException {

    }

    public void deactivateItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void renewItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void deleteItem(UUID userId, UUID itemId) throws TakeawayException {

    }

    public void changeItemAttachmentOrder(UUID userId, ChangeItemAttachmentOrderDto data) throws TakeawayException {

    }

    public void addAttachmentToItem(UUID userId, CreateAttachmentDto attachmentDto) throws TakeawayException {

    }

    public void deleteAttachmentFromItem(UUID userId, UUID itemId, UUID attachmentId) throws TakeawayException {

    }

    public void reportItem(UUID userId, ReportItemDto reportItemDto) throws TakeawayException {

    }
}
