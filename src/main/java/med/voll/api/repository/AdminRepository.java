package med.voll.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import med.voll.api.domain.admin.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    UserDetails findByLogin(String login);
}
