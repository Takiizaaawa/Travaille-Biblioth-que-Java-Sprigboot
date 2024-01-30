package org.cd.spring.bibliotheque.service;

import org.cd.spring.bibliotheque.model.Book;
import org.cd.spring.bibliotheque.model.Emprunt;
import org.cd.spring.bibliotheque.model.User;
import org.cd.spring.bibliotheque.repository.EmpruntRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpruntServiceImpl implements EmpruntService{
    private UserService userService;
    private JwtService jwtService;
    private EmpruntRepository empruntRepository;
    @Override
    public List<Emprunt> getEmpruntByUser(User user) {
        return empruntRepository.findByUser(user);
    }

    @Override
    public Emprunt empruntBook(User user, Book book) {
        Emprunt emprunt = new Emprunt();
        emprunt.setBook(book);
        emprunt.setUser((User) SecurityContextHolder.getContext().getAuthentication().getDetails());
        return empruntRepository.save(emprunt);
    }
}
