package Service;

import Models.Angajat;
import Models.Inscriere;
import Models.Participant;
import Repository.Repo.*;

public class Service {
    private AngajatRepository angajatRepository;
    private CursaRepository cursaRepository;
    private EchipaRepository echipaRepository;
    private ParticipantRepository participantRepository;
    private InscriereRepository inscriereRepository;
    private CursaService cursaService;

    public Service(AngajatRepository a, CursaRepository c, EchipaRepository e, ParticipantRepository p, InscriereRepository i){
        this.angajatRepository=a;
        this.cursaRepository=c;
        this.echipaRepository=e;
        this.inscriereRepository=i;
        this.participantRepository=p;
        this.cursaService=new CursaService(c);
    }
    public Iterable<Angajat> findAllEmployees(){return this.angajatRepository.findAll();}
    public void AddAngajat(Angajat a){this.angajatRepository.save(a);}
    public void AddParticipant(Participant p){this.participantRepository.save(p);}
    public void AddInscriere(Inscriere i ){this.inscriereRepository.save(i);}
    public void inscriere(int c){
        this.cursaService.addParticipanti(c);
    }

}
