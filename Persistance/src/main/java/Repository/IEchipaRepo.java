package Repository;

import Models.Entity;

public interface IEchipaRepo<ID,E extends Entity> {
    Iterable<E> findAll();
}
