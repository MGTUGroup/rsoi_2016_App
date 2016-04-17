package org.order.container;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 13.04.16.
 */
@Service("containerService")
public class ContainerTaxi {

    private ConcurrentHashMap<Long, TaxiData> taximap = new ConcurrentHashMap<>();

    public void addTaxiCoordinate(Long taxi_id, int x, int y){

        TaxiData td = taximap.get(taxi_id);
        if(td != null) {
            td.addCoordinate(x, y);
        }
        else
        {
            taximap.put(taxi_id, new TaxiData(x, y));
        }
    }

    public boolean addOrder(Long taxi_id, int pass_id){
        TaxiData td = taximap.get(taxi_id);
        if(td == null)
            return false;

        td.setPassengerId(pass_id);
        return true;
    }

    public boolean startCalculation(Long taxi_id){
        TaxiData td = taximap.get(taxi_id);
        if(td == null)
            return false;

        td.setCalculatoin(true);
        return true;
    }

    public Tuple stopCalculation(Long taxi_id){
        TaxiData td = taximap.get(taxi_id);
        Tuple tuple = new Tuple(td.getLenghtWay(), td.getPassengerId());
        td.setLengthWay(0);
        td.setPassengerId(null);
        return tuple;
    }

    @Override
    public String toString(){
        return taximap.entrySet().toString();
    }
}
