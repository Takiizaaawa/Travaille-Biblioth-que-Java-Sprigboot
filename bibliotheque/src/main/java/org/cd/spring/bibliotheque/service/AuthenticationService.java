package org.cd.spring.bibliotheque.service;

import org.cd.spring.bibliotheque.dao.request.InscriptionRequest;
import org.cd.spring.bibliotheque.dao.request.ConnexionRequest;
import org.cd.spring.bibliotheque.dao.response.JwtAuthenticationResponse;

public interface ServiceAuthentification extends Serializable {

    JwtAuthenticationResponse inscription(InscriptionRequest demandeInscription);

    JwtAuthenticationResponse connexion(ConnexionRequest demandeConnexion);
}
