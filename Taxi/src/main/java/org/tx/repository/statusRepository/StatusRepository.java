package org.tx.repository.statusRepository;

import org.tx.model.Status;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 10.04.16.
 */
public interface StatusRepository extends CrudRepository<Status,Long> {
    Status findByStatus(String status);
}


