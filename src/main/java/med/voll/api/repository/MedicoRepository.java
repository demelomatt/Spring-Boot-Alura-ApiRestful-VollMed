package med.voll.api.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;

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

    @Query("""
            SELECT m.ativo
            FROM Medico m
            WHERE
            m.id = :id
            """)
    Boolean findAtivoById(Long id);

}
