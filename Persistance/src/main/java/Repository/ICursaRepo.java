package Repository;

import Models.DTOBJCursa;
import Models.Entity;

public interface ICursaRepo <ID,E extends Entity>{
    void addParticipant(int n);
    Iterable<DTOBJCursa> findAll();
    int getIdCursa(String nume);
}
