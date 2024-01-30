package org.cd.spring.bibliotheque.controller;

import org.cd.spring.bibliotheque.model.Book;
import org.cd.spring.bibliotheque.model.User;
import org.cd.spring.bibliotheque.service.BookService;
import org.cd.spring.bibliotheque.service.EmpruntService;
import org.cd.spring.bibliotheque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class AuthorizationController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmpruntService empruntService;


    @GetMapping
    public ResponseEntity<?> sayHello() {

        return new ResponseEntity<>("CONNECTED!!",HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.findBook(Math.toIntExact(id));
        return new ResponseEntity<>(book, HttpStatus.OK);
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