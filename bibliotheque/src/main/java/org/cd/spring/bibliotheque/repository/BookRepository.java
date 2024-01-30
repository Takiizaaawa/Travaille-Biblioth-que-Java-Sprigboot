package org.cd.spring.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.cd.spring.bibliotheque.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByAuteur(String auteur);
    List<Book> findBookByTitre(String titre);
}
