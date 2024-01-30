package org.cd.spring.bibliotheque.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService {
    UserDetailsService userDetailsService();
}
