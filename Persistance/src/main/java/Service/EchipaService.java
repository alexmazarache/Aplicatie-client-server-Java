package Service;

import Models.Echipa;
import Repository.Repo.EchipaRepository;

public class EchipaService {
    private EchipaRepository echipaRepository;

    public EchipaService(EchipaRepository e){
        echipaRepository=e;
    }
    public Iterable<Echipa> getAll(){
        return echipaRepository.findAll();
    }
}
