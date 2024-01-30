package org.cd.spring.bibliotheque.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandeInscription {
    private String prenom;
    private String nom;
    private String email;
    private String motDePasse;
}
