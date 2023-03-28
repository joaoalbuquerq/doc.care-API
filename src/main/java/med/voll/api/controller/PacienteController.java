package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dto, UriComponentsBuilder uri) {
        return service.cadastrar(dto,uri);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return service.listar(paginacao);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dadosRequisicao){
        return service.atualizar(dadosRequisicao);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        return service.excluir(id);
    }

    @GetMapping("{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        return service.detalhar(id);
    }

}
