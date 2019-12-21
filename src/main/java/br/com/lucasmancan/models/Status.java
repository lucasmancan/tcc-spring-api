package br.com.lucasmancan.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public enum Status {
    @JsonProperty("pending")
    pending,
    @JsonProperty("approved")
    approved,
    @JsonProperty("deleted")
    deleted,
    @JsonProperty("canceled")
    canceled,
    @JsonProperty("active")
    active,
    @JsonProperty("inactive")
    inactive;

    @JsonCreator
    public static Status setValue(String key){

        System.out.println("TESTE:" + key);

        return Arrays.stream(Status.values()).filter(v -> v.name().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status não encontrado."));
    }

}
