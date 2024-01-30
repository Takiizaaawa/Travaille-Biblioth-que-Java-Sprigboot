package org.cd.spring.bibliotheque.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.cd.spring.bibliotheque.dao.request.InscriptionRequest;
import org.cd.spring.bibliotheque.dao.request.ConnexionRequest;
import org.cd.spring.bibliotheque.dao.response.JwtAuthenticationResponse;
import org.cd.spring.bibliotheque.model.Utilisateur;
import org.cd.spring.bibliotheque.repository.UtilisateurRepository;
import org.cd.spring.bibliotheque.service.AuthenticationService;
import org.cd.spring.bibliotheque.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplementationAuthenticationService implements AuthenticationService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse inscription(InscriptionRequest demandeInscription) {
        Utilisateur utilisateur = Utilisateur.builder()
                .prenom(demandeInscription.getPrenom())
                .nom(demandeInscription.getNom())
                .email(demandeInscription.getEmail())
                .motDePasse(passwordEncoder.encode(demandeInscription.getMotDePasse()))
                .build();

        utilisateurRepository.save(utilisateur);

        String jetonJwt = jwtService.genererToken(utilisateur);

        return JwtAuthenticationResponse.builder().token(jetonJwt).build();
    }

    @Override
    public JwtAuthenticationResponse connexion(ConnexionRequest demandeConnexion) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(demandeConnexion.getEmail(), demandeConnexion.getMotDePasse()));

        Utilisateur utilisateur = utilisateurRepository.findByEmail(demandeConnexion.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe incorrect."));

        String jetonJwt = jwtService.genererToken(utilisateur);

        return JwtAuthenticationResponse.builder().token(jetonJwt).build();
    }
}
