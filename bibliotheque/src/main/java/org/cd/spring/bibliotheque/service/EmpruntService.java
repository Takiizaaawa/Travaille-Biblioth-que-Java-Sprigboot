package org.cd.spring.bibliotheque.service;

import org.cd.spring.bibliotheque.model.Book;
import org.cd.spring.bibliotheque.model.Emprunt;
import org.cd.spring.bibliotheque.model.User;

import java.util.List;

public interface EmpruntService {
    List<Emprunt> getEmpruntByUser(User user);
    Emprunt empruntBook(User user, Book book);
}


