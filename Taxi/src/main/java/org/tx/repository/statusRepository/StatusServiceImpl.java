package org.tx.repository.statusRepository;

/**
 * Created by user on 10.04.16.
 */

import com.google.common.collect.Lists;
import org.tx.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Repository
@Transactional
@Service("statusService")
public class StatusServiceImpl implements StatusService{
    private StatusRepository statusRepository;

    @Override
    @Transactional(readOnly=true)
    public List<Status> findAll() {
        return Lists.newArrayList(statusRepository.findAll());
    }

    @Override
    @Transactional(readOnly=true)
    public Status findById(Long id) {
        return statusRepository.findOne(id);
    }

    @Override
    public Status save(Status passenger) {
        return statusRepository.save(passenger);
    }

    @Override
    public Status findByStatus(String status) {
        return statusRepository.findByStatus(status);
    }

    @Autowired
    public void setStatusRepository(StatusRepository statusRepository){

        this.statusRepository = statusRepository;

        final List<Status> resultList = new ArrayList<>();
        final Iterable<Status> all = statusRepository.findAll();
        all.forEach(new Consumer<Status>() {
            @Override
            public void accept(Status _status) {
                resultList.add(_status);
            }
        });

        if(resultList.size() <= 0)
        {
            statusRepository.save(new Status("free"));
            statusRepository.save(new Status("busy"));
            statusRepository.save(new Status("transit"));
            statusRepository.save(new Status("awaiting"));
        }
    }

}
