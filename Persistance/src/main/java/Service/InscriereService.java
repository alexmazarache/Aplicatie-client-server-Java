package Service;

import Models.Inscriere;
import Repository.Repo.InscriereRepository;

public class InscriereService {
    InscriereRepository inscriereRepository;
    public InscriereService(InscriereRepository i){
        this.inscriereRepository=i;
    }
    public Inscriere addInscriere(Integer id, Integer ci, Integer pi, String a){
        return inscriereRepository.save(new Inscriere(id,ci,pi,a));
    }
    public Iterable<Inscriere> getAll(){
        return inscriereRepository.findAll();
    }
}
