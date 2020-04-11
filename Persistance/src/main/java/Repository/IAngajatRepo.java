package Repository;

import Models.Entity;

public interface IAngajatRepo<ID,E extends Entity> {

    E findOneLogin(String s, String p);
    E save(E entity);
    E update(E entity) ;
    E delete(ID id);
    Iterable<E> findAll();

}
