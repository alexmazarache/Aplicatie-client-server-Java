package Services;

import Models.DTOAngajat;
import Models.DTOBJCursa;

public interface IObserver {
    void AngajatLoggedIn(DTOAngajat angajat) throws ServerException;
    void AngajatLoggedOut(DTOAngajat angajat) throws ServerException;
    void AngajatSubmitted(DTOBJCursa[] curse) throws ServerException;


}
