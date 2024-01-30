package org.cd.spring.bibliotheque.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class ImplementationJwtService implements JwtService {
    @Value("${token.signing.key}")
    private String cleSignatureJwt;

    @Override
    public String obtenirNomUtilisateurDuToken(String token) {
        return extraireRequeteToken(token, Claims::getSubject);
    }

    @Override
    public String creerToken(UserDetails userDetails) {
        return construireToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean tokenEstValide(String token, UserDetails userDetails) {
        final String nomUtilisateur = obtenirNomUtilisateurDuToken(token);
        return (nomUtilisateur.equals(userDetails.getUsername())) && !tokenEstExpire(token);
    }

    private <T> T extraireRequeteToken(String token, Function<Claims, T> resolveurClaims) {
        final Claims claims = extraireTousLesClaims(token);
        return resolveurClaims.apply(claims);
    }

    private String construireToken(Map<String, Object> claimsSupplementaires, UserDetails userDetails) {
        return Jwts.builder().setClaims(claimsSupplementaires).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expiration après 24 heures
                .signWith(cleDeSignature(), SignatureAlgorithm.HS256).compact();
    }

    private boolean tokenEstExpire(String token) {
        return extraireExpiration(token).before(new Date());
    }

    private Date extraireExpiration(String token) {
        return extraireRequeteToken(token, Claims::getExpiration);
    }

    private Claims extraireTousLesClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(cleDeSignature()).build().parseClaimsJws(token).getBody();
    }

    private Key cleDeSignature() {
        byte[] octetsCle = Decoders.BASE64.decode(cleSignatureJwt);
        return Keys.hmacShaKeyFor(octetsCle);
    }
}
