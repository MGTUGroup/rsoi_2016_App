package org.tx.container;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
import org.tx.model.*;
/**
 * Created by user on 11.04.16.
 */
public class TaxiMap {

    private HashMap<Long, CoordinatesTime> taximap = new HashMap<>();

    public Long findTaxi(int x, int y, int r)
    {
        for(Long id : taximap.keySet())
        {
            if()
        }
    }

    public void putTaxiCoordinates(long taxi_id, int x, int y)
    {
        CoordinatesTime ct = taximap.get(taxi_id);
        if(ct == null){
            taximap.put(taxi_id, new CoordinatesTime(x, y, Calendar.getInstance()));
        }
        else
        {
            ct.setDatetime(Calendar.getInstance().getTime());
        }
    }
}
