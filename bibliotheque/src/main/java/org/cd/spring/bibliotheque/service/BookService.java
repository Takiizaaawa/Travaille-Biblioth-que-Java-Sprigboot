package org.cd.spring.bibliotheque.service;


import org.cd.spring.bibliotheque.model.Book;
import org.cd.spring.bibliotheque.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public void addBook(Book book){
        repository.save(book);
    }
    public Book findBook(int id){
        return repository.findById(id).get();
    }

    public Book updateBook(int id, int update){
        Book book = repository.findById(id).get();
        book.setNombreDisponible(book.getNombreDisponible()+update);
        repository.save(book);
        return book;
    }
    public List<Book> findBookByAuteur(String auteur){
        return repository.findBookByAuteur(auteur);
    }

    public List<Book> findBookByTitre(String titre){
        return repository.findBookByTitre(titre);
    }

    public List<Book> findAllBooks(){
        return repository.findAll();
    }

}
