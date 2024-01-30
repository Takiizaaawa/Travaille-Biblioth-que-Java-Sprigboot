package org.cd.spring.bibliotheque.service;

import org.cd.spring.bibliotheque.model.Book;
import org.cd.spring.bibliotheque.model.Emprunt;
import org.cd.spring.bibliotheque.model.User;
import org.cd.spring.bibliotheque.repository.EmpruntRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionEmprunt implements ServiceEmprunt {
    private UserService serviceUtilisateur;
    private JwtService serviceJwt;
    private EmpruntRepository repositoryEmprunt;

    @Override
    public List<Emprunt> recupererEmpruntsParUtilisateur(User utilisateur) {
        return repositoryEmprunt.findByUser(utilisateur);
    }

    @Override
    public Emprunt realiserEmpruntLivre(User utilisateur, Book livre) {
        Emprunt nouvelEmprunt = new Emprunt();
        nouvelEmprunt.setBook(livre);
        nouvelEmprunt.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return repositoryEmprunt.save(nouvelEmprunt);
    }
}
