package med.voll.api.domain.entity.consulta;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import lombok.*;

import med.voll.api.application.dto.consulta.ConsultaDto;
import med.voll.api.application.dto.consulta.ConsultaIdDto;
import med.voll.api.domain.entity.paciente.Paciente;
import med.voll.api.domain.entity.medico.Medico;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity(name = "Consulta")
@Table(name = "consultas")
public class Consulta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    private LocalDateTime data;
    private boolean ativo;
    
    public Consulta(Paciente paciente, Medico medico, LocalDateTime date) {
        this.paciente = paciente;
        this.medico = medico;
        this.data = date;
        this.ativo = true;
    }

    public void atualizar(ConsultaDto dados) {
        this.medico = dados.medico();
        this.data = dados.date();
    }

    public void excluir() {
        this.ativo = false;
    }
}
