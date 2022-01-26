package com.takeaway.takeaway.presentation;

import com.google.common.base.Strings;

public abstract class BaseController {

    protected String trim(String input) {
        if (!Strings.isNullOrEmpty(input)) {
            return input.trim();
        }
        return input;
    }

    protected String trimLower(String input) {
        final String trimmed = trim(input);
        if (!Strings.isNullOrEmpty(trimmed)) {
            return trimmed.toLowerCase();
        }
        return trimmed;
    }
}
