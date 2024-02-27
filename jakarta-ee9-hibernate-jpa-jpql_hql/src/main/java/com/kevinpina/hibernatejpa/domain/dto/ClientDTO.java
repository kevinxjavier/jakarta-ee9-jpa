package com.kevinpina.hibernatejpa.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ClientDTO {

    private String name;
    private String surname;

    public ClientDTO(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

}
