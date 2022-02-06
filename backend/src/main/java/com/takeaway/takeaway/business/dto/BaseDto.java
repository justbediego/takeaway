package com.takeaway.takeaway.business.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public abstract class BaseDto implements Serializable {

    protected static String trim(String input) {

        if (StringUtils.isNotBlank(input)) {
            return input.trim();
        }
        return input;
    }

    protected static String trimLower(String input) {
        final String trimmed = trim(input);
        if (StringUtils.isNotBlank(trimmed)) {
            return trimmed.toLowerCase();
        }
        return trimmed;
    }
}
