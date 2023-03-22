package med.voll.api.domain.paciente;

import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

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

}
