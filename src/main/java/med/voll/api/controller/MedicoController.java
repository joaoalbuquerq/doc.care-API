package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dadosRequisicao){
        service.cadastrar(new Medico(dadosRequisicao));
    }

    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 20, sort = {"nome"}) Pageable paginacao){
        return service.listar(paginacao);
    }

    @PutMapping
    @Transactional
    public void ataulizar(@RequestBody @Valid DadosAtualizacaoMedico dadosRequisicao){
        service.atualizar(dadosRequisicao);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
        service.excluir(id);
    }

}
