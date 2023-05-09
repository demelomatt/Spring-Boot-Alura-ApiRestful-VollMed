package med.voll.api.infra.repository.jpa;

import java.time.LocalDateTime;

import med.voll.api.domain.entity.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import med.voll.api.domain.entity.consulta.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Page<Consulta> findAllByAtivoTrue(Pageable paginacao);

    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);
}
