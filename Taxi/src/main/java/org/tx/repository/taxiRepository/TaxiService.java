package org.tx.repository.taxiRepository;

/**
 * Created by user on 10.04.16.
 */

import org.tx.model.*;

import java.util.List;

public interface TaxiService {
    List<Taxi> findAll();
    Taxi findById(Long Id);
    Taxi save(Taxi passenger);
    Taxi findByEmailAndPassword(String email, String password);
}
