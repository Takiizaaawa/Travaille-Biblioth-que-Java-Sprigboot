package org.cd.spring.bibliotheque.service;

import org.cd.spring.bibliotheque.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.cd.spring.bibliotheque.repository.UserRepository;
import org.cd.spring.bibliotheque.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImplUserService implements UserService {
    private final UserRepository userRepo;

    @Override
    public UserDetailsService getUserDetailsService() {
        return username -> userRepo.findByEmail(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User modifyUser(User user) {
        return userRepo.save(user);
    }

    public void removeUser(Long userId) {
        userRepo.deleteById(Math.toIntExact(userId));
    }
}
