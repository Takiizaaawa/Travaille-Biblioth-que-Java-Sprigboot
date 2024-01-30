package org.cd.spring.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.cd.spring.bibliotheque.model.Livre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    List<Livre> trouverLivresParAuteur(String auteur);
    List<Livre> trouverLivresParTitre(String titre);
}
