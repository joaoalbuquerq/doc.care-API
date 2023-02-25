package med.voll.api.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRespository;

    public void cadastrar(Medico medico){
        medicoRespository.save(medico);
    }

    public Page<DadosListagemMedico> listar(Pageable paginacao){
        return medicoRespository.findAll(paginacao).map(DadosListagemMedico::new);
    }

    public void atualizar(DadosAtualizacaoMedico dados){
        var medico = medicoRespository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }
}
