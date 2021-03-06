package org.psg.repository.statusRepository;

import org.psg.model.Status;
import java.util.List;

/**
 * Created by user on 10.04.16.
 */
public interface StatusService {
    List<Status> findAll();
    Status findById(Long Id);
    Status findByStatus(String status);
    Status save(Status status);
}

