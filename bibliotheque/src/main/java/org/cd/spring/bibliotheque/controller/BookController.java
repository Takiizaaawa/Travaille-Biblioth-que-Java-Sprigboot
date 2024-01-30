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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livre")
public class ControleurLivre {

    private ServiceLivre serviceLivre;
    private ServiceUtilisateur serviceUtilisateur;
    private ServiceEmprunt serviceEmprunt;

    public ControleurLivre(ServiceLivre serviceLivre){
        this.serviceLivre = serviceLivre;
        System.out.println("ControleurLivre en cours d'exécution...");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livre> obtenirLivre(@PathVariable("id") int id){
        try{
            return new ResponseEntity<>(serviceLivre.trouverLivre(id), HttpStatus.FOUND);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ajouter")
    public ResponseEntity<String> ajouterLivre(@RequestBody Livre livre){
        serviceLivre.ajouterLivre(livre);
        return new ResponseEntity<>("Le livre a été ajouté à la bibliothèque avec succès !", HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Livre>> obtenirLivres(
            @RequestParam(value="auteur", required = false) String auteur,
            @RequestParam(value="titre", required = false) String titre
    ){
        List<Livre> livres;
        try{
            if(auteur != null){
                livres = serviceLivre.trouverLivresParAuteur(auteur);
            } else if (titre != null){
                livres = serviceLivre.trouverLivresParTitre(titre);
            } else {
                livres = serviceLivre.obtenirTousLesLivres();
            }
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if(livres.isEmpty()){
            return new ResponseEntity<>(livres, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(livres, HttpStatus.FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Livre> mettreAJourLivre(
            @PathVariable("id") int id,
            @RequestParam(value="update", required = true) int update
    ){
        try{
            Livre livre = serviceLivre.mettreAJourLivre(id, update);
            return new ResponseEntity<>(livre, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
