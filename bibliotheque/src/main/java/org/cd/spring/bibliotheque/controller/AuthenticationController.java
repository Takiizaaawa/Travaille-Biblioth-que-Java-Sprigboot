package org.cd.spring.bibliotheque.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.cd.spring.bibliotheque.dao.request.DemandeInscription;
import org.cd.spring.bibliotheque.dao.request.DemandeConnexion;
import org.cd.spring.bibliotheque.dao.response.JwtAuthenticationResponse;
import org.cd.spring.bibliotheque.service.ServiceAuthentification;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/authentification")
@RequiredArgsConstructor
public class ControleurAuthentification {

    private final ServiceAuthentification serviceAuthentification;

    @PostMapping("/inscription")
    public ResponseEntity<JwtAuthenticationResponse> inscription(@RequestBody DemandeInscription demandeInscription) {
        return ResponseEntity.ok(serviceAuthentification.inscription(demandeInscription));
    }

    @PostMapping("/connexion")
    public ResponseEntity<JwtAuthenticationResponse> connexion(@RequestBody DemandeConnexion demandeConnexion) {
        return ResponseEntity.ok(serviceAuthentification.connexion(demandeConnexion));
    }
}
