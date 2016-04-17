package org.tx.repository.taxiRepository;

/**
 * Created by user on 10.04.16.
 */

import com.google.common.collect.Lists;
import org.tx.model.Taxi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Service("taxiService")
public class TaxiServiceImpl implements TaxiService {
    private TaxiRepository taxiRepository;

    @Override
    @Transactional(readOnly=true)
    public List<Taxi> findAll() {
        return Lists.newArrayList(taxiRepository.findAll());
    }

    @Override
    @Transactional(readOnly=true)
    public Taxi findById(Long id) {
        return taxiRepository.findOne(id);
    }

    @Override
    public Taxi save(Taxi taxi) {
        return taxiRepository.save(taxi);
    }

    @Override
    public Taxi findByEmailAndPassword(String email, String password){
        return taxiRepository.findByEmailAndPassword(email, password);
    }

    @Autowired
    public void setPassengerRepository(TaxiRepository taxiRepository){
        this.taxiRepository = taxiRepository;
    }

}
