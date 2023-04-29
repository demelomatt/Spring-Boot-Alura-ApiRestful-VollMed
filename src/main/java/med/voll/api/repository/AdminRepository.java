package med.voll.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import med.voll.api.domain.admin.admin;

public interface AdminRepository extends JpaRepository<admin, Long> {
    UserDetails findByLogin(String login);
}
