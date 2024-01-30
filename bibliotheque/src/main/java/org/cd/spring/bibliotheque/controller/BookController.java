package org.cd.spring.bibliotheque.controller;

import jakarta.ws.rs.QueryParam;
import org.cd.spring.bibliotheque.model.Book;
import org.cd.spring.bibliotheque.model.User;
import org.cd.spring.bibliotheque.repository.BookRepository;
import org.cd.spring.bibliotheque.service.BookService;
import org.cd.spring.bibliotheque.service.EmpruntService;
import org.cd.spring.bibliotheque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;
    private UserService userService;
    private EmpruntService empruntService;

    public BookController(BookService bookService){
        this.bookService = bookService;
        System.out.println("BookController running...");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") int id){
        try{
            return new ResponseEntity<>(bookService.findBook(id) ,HttpStatus.FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book){
        bookService.addBook(book);
        return new ResponseEntity<>("The book has been added to the library successfully!",HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getBooks(
            @RequestParam(value="auteur", required = false) String auteur,
            @RequestParam(value="titre", required = false) String titre
    ){
        List<Book> books;
        try{
            if(auteur != null){
                books = bookService.findBookByAuteur(auteur);
            } else if (titre != null){
                books = bookService.findBookByTitre(titre);
            } else {
                books = bookService.findAllBooks();
            }
        } catch(Exception e){
           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if(books.isEmpty()){
            return new ResponseEntity<>(books, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable("id") int id,
            @RequestParam(value="update", required = true) int update
    ){
        try{
            Book book = bookService.updateBook(id, update);
            return new ResponseEntity<>(book, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{id}/emprunt")
    public ResponseEntity<String> borrowBook(@PathVariable Long id) {
        // Retrieve the currently authenticated user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        // Retrieve the book by ID
        Book book = bookService.findBook(Math.toIntExact(id));

        // Check if the book is available
        if (book.getNombreDisponible() > 0) {
            // Borrow the book
            empruntService.empruntBook(currentUser, book);

            return new ResponseEntity<>("Book borrowed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not available for borrowing", HttpStatus.BAD_REQUEST);
        }
    }



}
