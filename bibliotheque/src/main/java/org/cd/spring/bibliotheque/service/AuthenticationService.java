package org.cd.spring.bibliotheque.service;

import org.cd.spring.bibliotheque.dao.request.SignUpRequest;
import org.cd.spring.bibliotheque.dao.request.SigninRequest;
import org.cd.spring.bibliotheque.dao.response.JwtAuthenticationResponse;

import java.io.Serializable;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}