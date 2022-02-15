package com.takeaway.takeaway.business;

import com.google.cloud.storage.*;
import com.takeaway.takeaway.business.dto.CreateAttachmentDto;
import com.takeaway.takeaway.business.exception.EntityNotFound;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.business.exception.UnableToParseImage;
import com.takeaway.takeaway.business.exception.UnrecognizedException;
import com.takeaway.takeaway.dataaccess.model.Attachment;
import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
import com.takeaway.takeaway.dataaccess.model.enums.EntityTypes;
import com.takeaway.takeaway.dataaccess.repository.AttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Transactional
public class AttachmentLogic {

    @Value("${spring.application.attachmentsBucket}")
    private String attachmentsBucket;

    @Value("${spring.application.profilePictureMaxSize}")
    private Integer profilePictureMaxSize;

    private final ValidationLogic validationLogic;
    private final Storage storage;

    private BlobId getBlobId(Attachment attachment) {
        String extension = attachment.getType() == AttachmentTypes.IMAGE ? "PNG" : "DAT";
        return BlobId.of(attachmentsBucket, String.format("%s/%s.%s", extension, attachment.getFileId(), extension));
    }

    private final AttachmentRepository attachmentRepository;

    public AttachmentLogic(ValidationLogic validationLogic, Storage storage, AttachmentRepository attachmentRepository) {
        this.validationLogic = validationLogic;
        this.storage = storage;
        this.attachmentRepository = attachmentRepository;
    }

    // only used internally
    public byte[] prepareImage(byte[] imageData, Integer rescaleSide) throws TakeawayException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            BufferedImage image = ImageIO.read(inputStream);
            Integer height = image.getHeight();
            Integer width = image.getWidth();
            Integer maxSide = Math.max(height, width);
            if (rescaleSide == null && maxSide > profilePictureMaxSize) {
                rescaleSide = profilePictureMaxSize;
            }
            if (rescaleSide != null) {
                height = (int) Math.ceil((double) height / maxSide * rescaleSide);
                width = (int) Math.ceil((double) width / maxSide * rescaleSide);
            }
            BufferedImage resized = Scalr.resize(image, Scalr.Method.AUTOMATIC, width, height);
            ImageIO.write(resized, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnableToParseImage();
        }
        return outputStream.toByteArray();
    }

    public String getAttachmentLink(UUID attachmentId, UUID attachmentKey) throws TakeawayException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findByIdAndKey(attachmentId, attachmentKey);
        if (optionalAttachment.isEmpty()) {
            throw new EntityNotFound(EntityTypes.ATTACHMENT, attachmentId);
        }
        Attachment attachment = optionalAttachment.get();
        BlobId blobId = getBlobId(attachment);
        Blob blob = storage.get(blobId);
        URL url = blob.signUrl(
                3,
                TimeUnit.HOURS,
                Storage.SignUrlOption.httpMethod(HttpMethod.GET)
        );
        return url.toString();
    }

    // only used internally
    public UUID createAttachment(CreateAttachmentDto attachmentDto, AttachmentTypes type) throws TakeawayException {
        // validation
        validationLogic.validateFilename(attachmentDto.getFilename());
        validationLogic.validateFileData(attachmentDto.getFileData());

        // business
        Attachment newAttachment = new Attachment();
        newAttachment.setFilename(attachmentDto.getFilename());
        newAttachment.setFileId(UUID.randomUUID());
        newAttachment.setSecurityKey(UUID.randomUUID());
        newAttachment.setFileSize(attachmentDto.getFileData().length);
        newAttachment.setType(type);
        try {
            storage.create(BlobInfo.newBuilder(getBlobId(newAttachment)).build(), attachmentDto.getFileData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnrecognizedException("Unable to create attachment");
        }
        this.attachmentRepository.save(newAttachment);
        return newAttachment.getId();
    }

    // only used internally
    public void removeAttachment(UUID attachmentId) throws TakeawayException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (optionalAttachment.isEmpty()) {
            throw new EntityNotFound(EntityTypes.ATTACHMENT, attachmentId);
        }
        Attachment attachment = optionalAttachment.get();
        try {
            storage.delete(getBlobId(attachment));
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnrecognizedException("Unable to access attachment");
        }
        attachmentRepository.delete(attachment);
    }
}
