package med.voll.api.repository;

import med.voll.api.domain.medico.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import med.voll.api.domain.medico.Medico;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query(
            """
            SELECT m FROM Medico m
            WHERE m.ativo = 1
            AND m.especialidade = :especialidade
            AND m.id NOT IN(
                SELECT c.medico.id FROM Consulta c
                WHERE 
                c.data = :date
            )
            ORDER BY RAND()
            LIMIT 1
            """
    )
    Medico getAvailableMedico(Especialidade especialidade, LocalDateTime date);
}
