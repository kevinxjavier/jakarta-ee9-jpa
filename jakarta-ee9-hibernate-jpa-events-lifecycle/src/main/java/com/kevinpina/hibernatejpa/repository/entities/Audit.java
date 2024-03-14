package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable // It means we can include in another class @Entity
public class Audit {

    @Column(name = "created")
    private LocalDateTime createdTime;

    @Column(name = "modified")
    private LocalDateTime modifiedTime;

    @PrePersist
    public void prePersist() {
        System.out.println("Init something before persist");
        createdTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("Init something before update");
        modifiedTime = LocalDateTime.now();
    }

}
