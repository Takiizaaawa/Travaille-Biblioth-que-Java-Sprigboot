package org.cd.spring.bibliotheque.repository;

import org.cd.spring.bibliotheque.model.Utilisateur;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> trouverParEmail(String email);
}
