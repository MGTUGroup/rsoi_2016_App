package org.tx.container;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.tx.model.*;
/**
 * Created by user on 11.04.16.
 */
@Service("taxiMapService")
public class TaxiMap {

    private ConcurrentHashMap<Long, CoordinatesTime> taximap = new ConcurrentHashMap<>();

    public Taxi findTaxi(int a, int b, int r)
    {
        for(ConcurrentHashMap.Entry<Long, CoordinatesTime> item : taximap.entrySet())
        {
            if(((item.getValue().getX() - a)*(item.getValue().getX() - a)
                    + (item.getValue().getY() - b)*(item.getValue().getY() - b)) <= r*r
                    && item.getValue().getTaxi().getStatus().getStatus().equals("free"))
            {
                return item.getValue().getTaxi();
            }
        }
        return null;
    }

    public void putTaxiCoordinates(Taxi taxi, int x, int y)
    {
        CoordinatesTime ct = taximap.get(taxi.getId());
        if(ct == null){

            taximap.put(taxi.getId(), new CoordinatesTime(x, y, Calendar.getInstance(), taxi));
        }
        else
        {
            updateTaxiCoordinates(taxi.getId(), x, y);
        }
    }

    public long updateTaxiCoordinates(long taxi_id, int x, int y)
    {
        CoordinatesTime ct = taximap.get(taxi_id);
        if(ct == null){
            return -1;
            //taximap.put(taxi_id, new CoordinatesTime(x, y, Calendar.getInstance()));
        }
        else
        {
            ct.setDatetime(Calendar.getInstance().getTime());
            ct.setX(x);
            ct.setY(y);
            return taxi_id;
        }
    }

}
