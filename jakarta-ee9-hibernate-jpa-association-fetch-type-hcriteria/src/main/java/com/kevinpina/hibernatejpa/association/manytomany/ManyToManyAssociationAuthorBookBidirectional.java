package com.kevinpina.hibernatejpa.association.manytomany;

import com.kevinpina.hibernatejpa.repository.db.JpaUtil;
import com.kevinpina.hibernatejpa.repository.entities.AuthorEntity;
import com.kevinpina.hibernatejpa.repository.entities.BookEntity;
import jakarta.persistence.EntityManager;

public class ManyToManyAssociationAuthorBookBidirectional {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntitymanagerfactory();

        try {
            BookEntity bookEntity1 = BookEntity.builder().title("Union Europea").build();

            AuthorEntity authorEntity1 = AuthorEntity.builder().fullName("Franz Kafka").build();
            BookEntity bookEntity2 = BookEntity.builder().title("The Castle").build();
            BookEntity bookEntity3 = BookEntity.builder().title("Metamorphosis").build();

            authorEntity1.getBookEntities().add(bookEntity1);
            authorEntity1.getBookEntities().add(bookEntity2);
            authorEntity1.getBookEntities().add(bookEntity3);

            bookEntity1.getAuthorEntities().add(authorEntity1);
            bookEntity2.getAuthorEntities().add(authorEntity1);
            bookEntity3.getAuthorEntities().add(authorEntity1);

            AuthorEntity authorEntity2 = AuthorEntity.builder().fullName("Arturo Uslar Pietri").build();
            BookEntity bookEntity4 = BookEntity.builder().title("Lanzas coloradas").build();
            BookEntity bookEntity5 = BookEntity.builder().title("La isla de Robinson").build();

            authorEntity2.addBook(bookEntity1);
            authorEntity2.addBook(bookEntity4);
            authorEntity2.addBook(bookEntity5);

            em.getTransaction().begin();

            em.persist(authorEntity1);
            em.persist(authorEntity2);

            em.getTransaction().commit();

            System.out.println(authorEntity1);
            System.out.println(authorEntity2);

            // Delete Author and Book
            em.getTransaction().begin();

            AuthorEntity authorEntityFound1 = em.find(AuthorEntity.class, authorEntity1.getId());
            BookEntity bookEntityFound1 = em.find(BookEntity.class, bookEntity2.getId());

            authorEntityFound1.getBookEntities().remove(bookEntityFound1);  // Removing Book2 of Author1 from "author_book"

            em.remove(bookEntityFound1);    // Removing Book2 from "book"

            em.remove(bookEntity3); // Can't remove it from "book", because it is associated with Author1

            em.remove(authorEntity2);   // Removing Author2 from "author" and its related books Book1, Book2, Book3 from "author_book" but not from "book"

            em.getTransaction().commit();

            System.out.println(authorEntityFound1);
            System.out.println(authorEntity2);

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
