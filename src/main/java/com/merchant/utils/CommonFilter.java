package com.merchant.utils;

import com.merchant.controller.advice.RestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class CommonFilter {

    @Getter
    @Setter
    private String filter;

    private Map<String, Object> filterAttributes;

    public CommonFilter(String value) {
        this.filter = value;
        this.loadFilterAttributes();
    }
    public boolean loadFilterAttributes() {
        boolean ret = false;
        ObjectMapper mapper = new ObjectMapper();

        try {
            filterAttributes = mapper.readValue(filter, Map.class);
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == filterAttributes)
            throw new RestException(HttpStatus.BAD_REQUEST,"Filter attributes are not correct. Kindly pass correct filter attributes.");
        return ret;
    }

    public boolean isEmpty() {
        return filterAttributes.isEmpty();
    }

    public boolean has(String attributeName) {
        return filterAttributes.containsKey(attributeName);
    }

    public Set<String> keys() {
        return filterAttributes.keySet();
    }

    public Optional<Object> get(String attributeName) {
        if (filterAttributes.containsKey(attributeName)) {
            if(null==filterAttributes.get(attributeName))
                return Optional.empty();
            return Optional.of(filterAttributes.get(attributeName));
        }
        return Optional.empty();
    }

    public Optional<Object> get(String attributeName, Object returnIfNotPresent) {
        if (filterAttributes.containsKey(attributeName)) {
            if(null==filterAttributes.get(attributeName))
                return Optional.empty();
            return Optional.of(filterAttributes.get(attributeName));
        } if (null != returnIfNotPresent)
            return Optional.of(returnIfNotPresent);
        return Optional.empty();
    }

    public String getString(String attributeName, String defaultValue) {
        Optional<Object> value = get(attributeName);
        if (value.isPresent()) {
            return value.get().toString();
        }
        return defaultValue;
    }

    public int getNumber(String attributeName, int defaultValue) {
        Optional<Object> value = get(attributeName);
        return value.map(o -> Integer.parseInt(o.toString())).orElse(defaultValue);
    }

    public float getFloat(String attributeName, float defaultValue) {
        Optional<Object> value = get(attributeName);
        return value.map(o -> Float.parseFloat(o.toString())).orElse(defaultValue);
    }

    public boolean getBoolean(String attributeName, boolean defaultValue) {
        Optional<Object> value = get(attributeName);
        return value.map(o -> Boolean.parseBoolean(o.toString())).orElse(defaultValue);
    }
}