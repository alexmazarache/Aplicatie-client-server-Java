package Service;

import Models.Angajat;
import Models.DTOAngajat;
import Repository.Repo.AngajatRepository;

public class AngajatService {
    AngajatRepository angajatRepository;

    public AngajatService(AngajatRepository r){
        this.angajatRepository=r;

    }
    public Angajat findOne(String nume, String parola){
        return (Angajat) angajatRepository.findOneLogin(nume,parola);
    }

    public  Iterable<Angajat> getAll(){
        return angajatRepository.findAll();
    }
    public Angajat deleteAngajat(Integer id){
        return (Angajat) angajatRepository.delete(id);
    }
    public Angajat update(Integer id, String n, String p){
        Angajat a = new Angajat(id,n,p);
        return (Angajat) angajatRepository.update(a);
    }
    public Angajat addAngajat(Integer id, String n, String p){
        Angajat a = new Angajat(id,n,p);
        return (Angajat) angajatRepository.save(a);
    }
    public Iterable<DTOAngajat> findOthers(DTOAngajat a){
        return angajatRepository.findOtherEmployees(a);
    }


}

