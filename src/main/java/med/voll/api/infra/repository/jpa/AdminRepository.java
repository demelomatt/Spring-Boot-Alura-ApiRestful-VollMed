package med.voll.api.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import med.voll.api.domain.entity.admin.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    UserDetails findByLogin(String login);
}
