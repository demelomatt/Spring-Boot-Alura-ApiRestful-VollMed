package med.voll.api.domain.paciente;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import med.voll.api.domain.util.Endereco;
import med.voll.api.dto.paciente.PacienteAtualizarDto;
import med.voll.api.dto.paciente.PacienteDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Paciente(PacienteDto data) {
        this.nome = data.nome();
        this.email = data.email();
        this.telefone = data.telefone();
        this.cpf = data.cpf();
        this.endereco = new Endereco(data.endereco());
        this.ativo = true;
    }

    public void atualizar(PacienteAtualizarDto pacienteDto) {
        this.nome = (pacienteDto.nome() != null) ? pacienteDto.nome() : this.nome;
        this.telefone = (pacienteDto.telefone() != null) ? pacienteDto.telefone() : this.nome;
        this.endereco = (pacienteDto.endereco() != null) ? new Endereco(pacienteDto.endereco()) : this.endereco;
    }

    public void excluir() {
        this.ativo = false;
    }
}