package org.cd.spring.bibliotheque.service;

import org.cd.spring.bibliotheque.model.Book;
import org.cd.spring.bibliotheque.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLivres {

    @Autowired
    private BookRepository repository;

    public void ajouterLivre(Book livre) {
        repository.save(livre);
    }

    public Book trouverLivreParId(int id) {
        return repository.findById(id).orElse(null);
    }

    public Book mettreAJourLivre(int id, int quantiteAjoutee) {
        Book livre = repository.findById(id).orElse(null);

        if (livre != null) {
            livre.setNombreDisponible(livre.getNombreDisponible() + quantiteAjoutee);
            repository.save(livre);
        }

        return livre;
    }

    public List<Book> trouverLivresParAuteur(String auteur) {
        return repository.findBookByAuteur(auteur);
    }

    public List<Book> trouverLivresParTitre(String titre) {
        return repository.findBookByTitre(titre);
    }

    public List<Book> trouverTousLesLivres() {
        return repository.findAll();
    }
}
