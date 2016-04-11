package org.psg.repository.passengerRepository;

/**
 * Created by user on 10.04.16.
 */
import java.util.List;

import org.psg.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;

@Repository
@Transactional
@Service("passengerService")
public class PassengerServiceImpl implements PassengerService {
    private PassengerRepository passengerRepository;

    @Override
    @Transactional(readOnly=true)
    public List<Passenger> findAll() {
        return Lists.newArrayList(passengerRepository.findAll());
    }

    @Override
    @Transactional(readOnly=true)
    public Passenger findById(Long id) {
        return passengerRepository.findOne(id);
    }

    @Override
    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger findByEmailAndPassword(String email, String password){
        return passengerRepository.findByEmailAndPassword(email, password);
    }

    @Autowired
    public void setPassengerRepository(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }

}
