package org.tx.container;

import org.tx.model.Taxi;

import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 11.04.16.
 */
public class CoordinatesTime {
    private int x;
    private int y;
    private Calendar datetime;
    private Taxi taxi;

    public CoordinatesTime(int x, int y, Calendar datetime, Taxi taxi){
        this.x = x;
        this.y = y;
        this.datetime = datetime;
        this.taxi = taxi;
    }

    public int getX(){ return x;}
    public void setX(int x){ this.x = x; }

    public int getY(){ return y;}
    public void setY(int y){ this.y = y; }

    public Calendar getDatetime(){ return this.datetime; }
    public void setDatetime(Date datetime){ this.datetime.setTime(datetime); }

    public Taxi getTaxi(){ return taxi; }
    public void setTaxi(Taxi _taxi){ this.taxi = _taxi; }

}
