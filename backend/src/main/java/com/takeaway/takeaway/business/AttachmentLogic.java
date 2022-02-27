package com.takeaway.takeaway.business;

import com.google.cloud.storage.*;
import com.takeaway.takeaway.business.dto.CreateAttachmentDto;
import com.takeaway.takeaway.business.exception.ExceptionEntities;
import com.takeaway.takeaway.business.exception.ExceptionTypes;
import com.takeaway.takeaway.business.exception.TakeawayException;
import com.takeaway.takeaway.dataaccess.model.Attachment;
import com.takeaway.takeaway.dataaccess.model.enums.AttachmentTypes;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class AttachmentLogic {

    @Value("${spring.application.attachmentsBucket}")
    private String attachmentsBucket;

    @Value("${spring.application.pictureMaxSize}")
    private Integer pictureMaxSize;

    private final ValidationLogic validationLogic;
    private final Storage storage;

    private BlobId getBlobId(Attachment attachment) {
        String extension = attachment.getType() == AttachmentTypes.IMAGE ? "PNG" : "DAT";
        return BlobId.of(attachmentsBucket, String.format("%s/%s_%s.%s", extension, attachment.getFileId(), attachment.getSecurityKey(), extension));
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
            int height = image.getHeight();
            int width = image.getWidth();
            int maxSide = Math.max(height, width);
            if (rescaleSide == null && maxSide > pictureMaxSize) {
                rescaleSide = pictureMaxSize;
            }
            if (rescaleSide != null) {
                height = (int) Math.ceil((double) height / maxSide * rescaleSide);
                width = (int) Math.ceil((double) width / maxSide * rescaleSide);
            }
            BufferedImage resized = Scalr.resize(image, Scalr.Method.AUTOMATIC, width, height);
            ImageIO.write(resized, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TakeawayException(ExceptionTypes.UNABLE_TO_PARSE_IMAGE);
        }
        return outputStream.toByteArray();
    }

    // only used internally
    public Attachment createAttachment(CreateAttachmentDto attachmentDto, AttachmentTypes type) throws TakeawayException {
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
            Blob blob = storage.create(
                    BlobInfo.newBuilder(getBlobId(newAttachment))
                            .setAcl(List.of(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                            .build(),
                    attachmentDto.getFileData()
            );
            newAttachment.setMediaLink(blob.getMediaLink());
        } catch (Exception e) {
            e.printStackTrace();
            throw new TakeawayException(
                    ExceptionTypes.UNRECOGNIZED,
                    ExceptionEntities.ATTACHMENT,
                    "Unable to create attachment"
            );
        }
        this.attachmentRepository.save(newAttachment);
        return newAttachment;
    }

    // only used internally
    public void removeAttachment(UUID attachmentId) throws TakeawayException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (optionalAttachment.isEmpty()) {
            throw new TakeawayException(
                    ExceptionTypes.ENTITY_NOT_FOUND,
                    ExceptionEntities.ATTACHMENT,
                    attachmentId.toString()
            );
        }
        Attachment attachment = optionalAttachment.get();
        try {
            storage.delete(getBlobId(attachment));
        } catch (Exception e) {
            e.printStackTrace();
            throw new TakeawayException(
                    ExceptionTypes.UNRECOGNIZED,
                    ExceptionEntities.ATTACHMENT,
                    "Unable to access attachment"
            );
        }
        attachmentRepository.delete(attachment);
    }
}
