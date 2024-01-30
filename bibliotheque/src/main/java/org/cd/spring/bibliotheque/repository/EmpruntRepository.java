package org.cd.spring.bibliotheque.repository;

import org.cd.spring.bibliotheque.model.Emprunt;
import org.cd.spring.bibliotheque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    List<Emprunt> findByUser(User user);

}
