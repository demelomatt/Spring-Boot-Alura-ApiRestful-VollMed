package med.voll.api.domain.entity.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import med.voll.api.application.dto.medico.MedicoDto;
import med.voll.api.domain.entity.util.Endereco;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

@Entity(name = "Medico")
@Table(name = "medicos")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(MedicoDto data) {
        this.nome = data.nome();
        this.email = data.email();
        this.telefone = data.telefone();
        this.crm = data.crm();
        this.especialidade = data.especialidade();
        this.endereco = new Endereco(data.endereco());
        this.ativo = true;
    }

    public void atualizar(MedicoDto medico) {
        this.nome = (medico.nome() != null) ? medico.nome() : this.nome;
        this.telefone = (medico.telefone() != null) ? medico.telefone() : this.telefone;
        this.endereco = (medico.endereco() != null) ? new Endereco(medico.endereco()) : this.endereco;
    }

    public void excluir() {
        this.ativo = false;
    }
}