package com.kevinpina.hibernatejpa.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "author")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    // This relationship is bidirectional because it is also in defined in BookEntity as: @ManyToMany List<AuthorEntity> authorEntities.
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // To personalized the creation of table author_book in line 27 @See StudentEntity.courses or ClientEntity.addressEntities
    @Builder.Default // Lets you configure default values for your fields when using @Builder.
    private List<BookEntity> bookEntities = new ArrayList<>();
    // CREATE TABLE `author_book` (
    //  `AuthorEntity_id` bigint NOT NULL,
    //  `bookEntities_id` bigint NOT NULL,
    //  KEY `FK3u9ypl74d2srq0byu74ixs9ld` (`bookEntities_id`),
    //  KEY `FKggmdn8qswqw374ua85nambuh5` (`AuthorEntity_id`),
    //  CONSTRAINT `FK3u9ypl74d2srq0byu74ixs9ld` FOREIGN KEY (`bookEntities_id`) REFERENCES `book` (`id`),
    //  CONSTRAINT `FKggmdn8qswqw374ua85nambuh5` FOREIGN KEY (`AuthorEntity_id`) REFERENCES `author` (`id`)
    //) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

    public void addBook(BookEntity bookEntity) {
        this.bookEntities.add(bookEntity);
        bookEntity.getAuthorEntities().add(this);
    }

}
