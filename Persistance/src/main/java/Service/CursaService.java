package Service;

import Models.DTOBJCursa;
import Repository.Repo.CursaRepository;

public class CursaService {
    private CursaRepository cursaRepository;

    public CursaService(CursaRepository c){
        cursaRepository =c;
    }
    public Iterable<DTOBJCursa> getAll(){
        return cursaRepository.findAll();
    }
    public void addParticipanti(int id){
        cursaRepository.addParticipant(id);
    }
    public int getIdCursa(String nume){
        return cursaRepository.getIdCursa(nume);
    }


}
