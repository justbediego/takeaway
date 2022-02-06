package com.takeaway.takeaway.presentation;

import org.apache.commons.lang3.StringUtils;

public abstract class BaseController {

    protected String trim(String input) {

        if (StringUtils.isNotBlank(input)) {
            return input.trim();
        }
        return input;
    }

    protected String trimLower(String input) {
        final String trimmed = trim(input);
        if (StringUtils.isNotBlank(trimmed)) {
            return trimmed.toLowerCase();
        }
        return trimmed;
    }
}
