package med.voll.api.infra.repository.jpa;

import med.voll.api.domain.entity.admin.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    UserDetails findByLogin(String login);
}
