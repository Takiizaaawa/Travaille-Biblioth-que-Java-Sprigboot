package org.cd.spring.bibliotheque.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.jvnet.hk2.annotations.Service;

public interface ServiceUtilisateur {
    UserDetailsService fournirServiceDetailsUtilisateur();
}
