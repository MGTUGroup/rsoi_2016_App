package org.psg.repository.passengerRepository;

/**
 * Created by user on 08.04.16.
 */
import org.psg.model.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
//@Configuration
//@EnableAutoConfiguration
//@EntityScan(basePackages = {"org.psg.model"})
public interface PassengerRepository extends CrudRepository<Passenger,Long> {

    @Query("SELECT p FROM Passenger p WHERE p.email = ?1 and p.password = ?2")
    Passenger findByEmailAndPassword(String email, String password);
}
