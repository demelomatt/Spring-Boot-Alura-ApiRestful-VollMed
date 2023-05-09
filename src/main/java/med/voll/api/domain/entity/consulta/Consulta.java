package med.voll.api.domain.entity.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import med.voll.api.application.dto.consulta.ConsultaDto;
import med.voll.api.domain.entity.medico.Medico;
import med.voll.api.domain.entity.paciente.Paciente;

import java.time.LocalDateTime;

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
        this.medico = dados.medico() != null ? dados.medico() : this.medico;
        this.data = dados.date() != null ? dados.date() : this.data;
    }

    public void excluir() {
        this.ativo = false;
    }
}
