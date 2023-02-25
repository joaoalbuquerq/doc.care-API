package med.voll.api.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;
@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedico dadosRequisicao) {
        this.nome = dadosRequisicao.nome();
        this.email = dadosRequisicao.email();
        this.telefone = dadosRequisicao.telefone();
        this.crm = dadosRequisicao.crm();
        this.especialidade = dadosRequisicao.especialidade();
        this.endereco = new Endereco(dadosRequisicao.endereco());
    }

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        if(dados.nome() != null)
            this.nome = dados.nome();
        if(dados.telefone() != null)
            this.telefone = dados.telefone();
        if(dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }
}
