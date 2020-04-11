package Service;

import Models.DTOBJParticipant;
import Models.Participant;
import Repository.Repo.ParticipantRepository;

public class ParticipantService {
    ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository p){
        participantRepository=p;

    }
    public Iterable<DTOBJParticipant> getAll(){
        return participantRepository.findAll();

    }
    public Iterable<DTOBJParticipant> getAllEchipa(String echipa){
        //returneaza toti jucatorii de la o anumita echipa
        return participantRepository.findAllEchipa(echipa);
    }
    public Participant addParticipant(Integer id, String n, Integer cap, String echipa){
        Participant p = new Participant(id,n,cap,echipa);
        return (Participant) participantRepository.save(p);
    }
    public Participant findOne(String nume){
        return participantRepository.findOne(nume);
    }

}
