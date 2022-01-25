package com.takeaway.takeaway.business;

import com.takeaway.takeaway.business.dto.CreateAttachmentDto;
import com.takeaway.takeaway.business.dto.GetAttachmentDto;
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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class AttachmentLogic {

    @Value("${spring.application.attachmentsPath}")
    private String attachmentsPath;

    @Value("${spring.application.profilePictureMaxSize}")
    private Integer profilePictureMaxSize;

    private ValidationLogic validationLogic;

    private String getFullPath(UUID fileId) {
        return String.format("%s/%s.TAKE", attachmentsPath, fileId);
    }

    private final AttachmentRepository attachmentRepository;

    public AttachmentLogic(ValidationLogic validationLogic, AttachmentRepository attachmentRepository) {
        this.validationLogic = validationLogic;
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

    public GetAttachmentDto getAttachment(UUID attachmentId, UUID attachmentKey, AttachmentTypes typeCheck) throws TakeawayException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findByIdAndKey(attachmentId, attachmentKey);
        if (optionalAttachment.isEmpty()) {
            throw new EntityNotFound(EntityTypes.ATTACHMENT, attachmentId);
        }
        Attachment attachment = optionalAttachment.get();
        if (typeCheck != null && attachment.getType() != typeCheck) {
            throw new EntityNotFound(EntityTypes.ATTACHMENT, attachmentId);
        }
        byte[] fileData;
        try {
            try (FileInputStream fileStream = new FileInputStream(getFullPath(attachment.getFileId()))) {
                fileData = fileStream.readAllBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnrecognizedException("Unable to access attachment");
        }
        return GetAttachmentDto.builder()
                .type(attachment.getType())
                .filename(attachment.getFilename())
                .fileData(fileData)
                .build();
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
        this.attachmentRepository.save(newAttachment);
        try {
            try (FileOutputStream fileStream = new FileOutputStream(getFullPath(newAttachment.getFileId()))) {
                fileStream.write(attachmentDto.getFileData());
                fileStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnrecognizedException("Unable to create attachment");
        }
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
            Files.delete(Path.of(getFullPath(attachment.getFileId())));
            attachmentRepository.delete(attachment);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnrecognizedException("Unable to access attachment");
        }
    }
}
