package med.voll.api.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRespository;

    public ResponseEntity cadastrar(DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){

        var medico = new Medico(dados);
        medicoRespository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId());

        return ResponseEntity.created(uri.toUri()).body(new DadosDetalhamentoMedico(medico));
    }

    public ResponseEntity<Page<DadosListagemMedico>> listar(Pageable paginacao){
        var page =  medicoRespository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity atualizar(DadosAtualizacaoMedico dados){
        var medico = medicoRespository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    public ResponseEntity excluir(Long id){
        var medico = medicoRespository.getReferenceById(id);
        medico.inativar();

        return ResponseEntity.noContent().build();
    }
}
