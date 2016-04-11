package org.psg.repository.passengerRepository;

/**
 * Created by user on 10.04.16.
 */
import org.psg.model.*;
import java.util.List;

public interface PassengerService {
    List<Passenger> findAll();
    Passenger findById(Long Id);
    Passenger save(Passenger passenger);
    Passenger findByEmailAndPassword(String email, String password);
}
