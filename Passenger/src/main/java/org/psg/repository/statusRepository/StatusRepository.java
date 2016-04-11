package org.psg.repository.statusRepository;

import org.psg.model.Status;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 10.04.16.
 */
public interface StatusRepository extends CrudRepository<Status,Long> {
    Status findByStatus(String status);
}


