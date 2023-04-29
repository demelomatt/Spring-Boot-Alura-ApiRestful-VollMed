package med.voll.api.domain.consulta;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import lombok.*;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

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
    
    public Consulta(Paciente paciente, Medico medico, LocalDateTime date) {
        this.paciente = paciente;
        this.medico = medico;
        this.data = date;
    }
}
