package org.tx.repository.taxiRepository;

/**
 * Created by user on 08.04.16.
 */
import org.tx.model.Taxi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

//@Repository
//@Configuration
//@EnableAutoConfiguration
//@EntityScan(basePackages = {"org.psg.model"})
public interface TaxiRepository extends CrudRepository<Taxi,Long> {

    @Query("SELECT p FROM Taxi p WHERE p.email = ?1 and p.password = ?2")
    Taxi findByEmailAndPassword(String email, String password);
}
