package org.cd.spring.bibliotheque.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface ServiceJwt {
    String obtenirNomUtilisateurDepuisToken(String token);
    String creerTokenPourUtilisateur(UserDetails detailsUtilisateur);
    boolean verifierValiditeToken(String token, UserDetails detailsUtilisateur);
}
