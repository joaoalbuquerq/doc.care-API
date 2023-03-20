package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.MedicoService;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dadosRequisicao, UriComponentsBuilder uri){
        return service.cadastrar(dadosRequisicao, uri);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 20, sort = {"nome"}) Pageable paginacao){
        return service.listar(paginacao);
    }

    @PutMapping
    @Transactional
    public ResponseEntity ataulizar(@RequestBody @Valid DadosAtualizacaoMedico dadosRequisicao){
        return service.atualizar(dadosRequisicao);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        return service.excluir(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        return service.pesquisar(id);
    }

}
