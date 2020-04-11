package Repository;

import Models.DTOBJParticipant;
import Models.Entity;

public interface IParticipantRepo<ID,E extends Entity> {
    Iterable<DTOBJParticipant> findAll();
    Iterable<DTOBJParticipant> findAllEchipa(String echipa);
    E save(E entity);
    E findOne(String nume);
}
