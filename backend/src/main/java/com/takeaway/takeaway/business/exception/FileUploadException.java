package com.takeaway.takeaway.business.exception;

public class FileUploadException extends TakeawayException {
    public FileUploadException() {
        super("The system was unable to access the uploaded file", "");
    }
}
