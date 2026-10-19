package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdClientNot(String email, Long idClient);
    boolean existsByNumPermis(String numPermis);
    boolean existsByNumPermisAndIdClientNot(String numPermis, Long idClient);
}
