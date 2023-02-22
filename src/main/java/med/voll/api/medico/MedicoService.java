package med.voll.api.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRespository;

    public void cadastrarMedico(Medico medico){
        medicoRespository.save(medico);
    }

    public List<DadosListagemMedico> listar(){
        return medicoRespository.findAll().stream().map(DadosListagemMedico::new).toList();
    }
}
