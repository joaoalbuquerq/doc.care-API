package med.voll.api.domain.paciente;

import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public ResponseEntity cadastrar(DadosCadastroPaciente dto, UriComponentsBuilder uriBuilder){

        Paciente paciente = new Paciente(dto);
        repository.save(paciente);
        var uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId());
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Page<DadosListagemPaciente>> listar(Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity atualizar(DadosAtualizacaoPaciente dadosRequisicao) {
        var paciente = repository.getReferenceById(dadosRequisicao.id());
        paciente.atualizarDadosCadastrais(dadosRequisicao);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    public ResponseEntity excluir(Long id){
        var paciente = repository.getReferenceById(id);
        paciente.inativar();

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity detalhar(Long id){
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
