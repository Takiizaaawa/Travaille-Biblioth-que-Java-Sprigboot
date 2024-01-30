package org.cd.spring.bibliotheque.controller;

import org.cd.spring.bibliotheque.model.Livre;
import org.cd.spring.bibliotheque.model.Utilisateur;
import org.cd.spring.bibliotheque.service.ServiceLivre;
import org.cd.spring.bibliotheque.service.ServiceEmprunt;
import org.cd.spring.bibliotheque.service.ServiceUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ressource")
@RequiredArgsConstructor
public class ControleurAutorisation {

    @Autowired
    private ServiceLivre serviceLivre;
    @Autowired
    private ServiceUtilisateur serviceUtilisateur;
    @Autowired
    private ServiceEmprunt serviceEmprunt;

    @GetMapping
    public ResponseEntity<?> direBonjour() {
        return new ResponseEntity<>("CONNECTÉ !!", HttpStatus.OK);
    }

    @GetMapping("/livres")
    public ResponseEntity<List<Livre>> obtenirTousLesLivres() {
        List<Livre> livres = serviceLivre.obtenirTousLesLivres();
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    @GetMapping("/livres/{id}")
    public ResponseEntity<Livre> obtenirLivreParId(@PathVariable Long id) {
        Livre livre = serviceLivre.trouverLivre(Math.toIntExact(id));
        return new ResponseEntity<>(livre, HttpStatus.OK);
    }

    @PostMapping("/{id}/emprunt")
    public ResponseEntity<String> emprunterLivre(@PathVariable Long id) {
        // Récupérer l'utilisateur actuellement authentifié
        Utilisateur utilisateurActuel = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getDetails();
        // Récupérer le livre par ID
        Livre livre = serviceLivre.trouverLivre(Math.toIntExact(id));

        // Vérifier si le livre est disponible
        if (livre.getNombreDisponible() > 0) {
            // Emprunter le livre
            serviceEmprunt.emprunterLivre(utilisateurActuel, livre);

            return new ResponseEntity<>("Livre emprunté avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Livre non disponible pour emprunt", HttpStatus.BAD_REQUEST);
        }
    }
}
