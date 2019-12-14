package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

public enum Status {
    pending,
    approved,
    deleted,
    canceled,
    active,
    inactive;

    @JsonCreator
    public static Status setValue(String key){
        return Arrays.stream(Status.values()).filter(v -> v.name().equals(key)).findFirst().orElse(null);
    }

}
