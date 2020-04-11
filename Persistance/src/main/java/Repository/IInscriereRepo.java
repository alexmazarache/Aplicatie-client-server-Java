package Repository;

import Models.Entity;

public interface IInscriereRepo<ID,E extends Entity> {
    E save(E entity);
    Iterable<E> findAll();

}
