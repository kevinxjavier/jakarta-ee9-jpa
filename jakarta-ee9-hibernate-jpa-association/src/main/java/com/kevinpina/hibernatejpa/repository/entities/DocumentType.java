package com.kevinpina.hibernatejpa.repository.entities;

import lombok.Getter;

@Getter
public enum DocumentType {

    PASSPORT("passport"),
    RUT("rut"),
    DNI("dni"),
    NIE("nie"),
    CI("ci");

    private final String type;
    DocumentType(String type) {
        this.type = type;
    }

}
