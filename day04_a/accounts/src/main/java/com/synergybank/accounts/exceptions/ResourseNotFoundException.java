package com.synergybank.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourseNotFoundException extends RuntimeException {
    public ResourseNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found for %s : %s", resourceName, fieldName, fieldValue));
    }
}
