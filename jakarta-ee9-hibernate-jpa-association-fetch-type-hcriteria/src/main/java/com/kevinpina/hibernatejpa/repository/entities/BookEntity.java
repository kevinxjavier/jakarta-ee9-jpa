package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @ToString    // Commenting to avoid recursive call in AuthorEntity.toString.BookEntity
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // This relationship is bidirectional because it is also in defined in AuthorEntity as: @ManyToMany List<BookEntity> bookEntities.
    // mappedBy: for a relationship bidirectional, we have to indicate the inverse relationship with this property "mappedBy",
    // 		putting the attribute defined in AuthorEntity that point to this class that is in this case AuthorEntity.bookEntities.
    @ManyToMany(mappedBy = "bookEntities")
    @Builder.Default // Lets you configure default values for your fields when using @Builder.
    private List<AuthorEntity> authorEntities = new ArrayList<>();

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
