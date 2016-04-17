package org.tx.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tx.container.TaxiMap;
import org.tx.model.Status;
import org.tx.model.Taxi;
import org.tx.repository.statusRepository.StatusService;
import org.tx.repository.taxiRepository.TaxiService;
import org.tx.serializable.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by user on 11.04.16.
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaxiController {

    private TaxiService taxiService;
    private StatusService statusService;
    private TaxiMap taxiMapxi;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello(){
        return "Hello there Taxi Service !";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Taxi taxi) {
        try {
            if (taxi == null)
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            taxi.setStatus(statusService.findById((long)1)); // Исправить !!!!
            taxiService.save(taxi);
            return new ResponseEntity<String>(HttpStatus.CREATED);

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody Login login) {
        try{
            if(!login.checkFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            Taxi taxi = taxiService.findByEmailAndPassword(login.getEmail(), login.getPassword());
            if(taxi == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            JsonObject obj = new JsonObject();
            obj.addProperty("taxi_id", taxi.getId().toString());
            return ResponseEntity.status(HttpStatus.OK).body(obj.toString());

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/check_status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cheakStatus(@RequestBody Parametr parametr) {
        try{

            if(!parametr.checkFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            Taxi taxi = taxiService.findById(new Long(parametr.getUser_id()));
            if(taxi == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            JsonObject obj = new JsonObject();
            obj.addProperty("status", taxi.getStatus().getStatus());
            return ResponseEntity.status(HttpStatus.OK).body(obj.toString());

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/change_status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeStatus(@RequestBody ChangeStatus ch_status) {
        try{
            if(!ch_status.checkFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            Status current_status = statusService.findByStatus(ch_status.getStatus());
            if(current_status == null)
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            Taxi taxi = taxiService.findById(new Long(ch_status.getUser_id()));
            if(taxi == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            taxi.setStatus(current_status);
            taxiService.save(taxi);
            return new ResponseEntity<String>(HttpStatus.OK);

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/post_coordinates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCoordinate(@RequestBody RequestCoordinate coordinate){
        try{
            if(!coordinate.chaeckFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            if(taxiMapxi.updateTaxiCoordinates(coordinate.getUser_id(),
                    coordinate.getCoordinate().getX(),
                    coordinate.getCoordinate().getY() ) != -1)
                return new ResponseEntity<String>(HttpStatus.OK);

            Taxi taxi = taxiService.findById(new Long(coordinate.getUser_id().intValue()));
            if(taxi == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            taxiMapxi.putTaxiCoordinates(taxi, coordinate.getCoordinate().getX(),coordinate.getCoordinate().getY() );
            return new ResponseEntity<String>(HttpStatus.OK);

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/check_taxi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> chaekTaxi(@RequestBody RadiusCoordinate coordinate){
        try{
            if(!coordinate.chaeckFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            Taxi taxi = taxiMapxi.findTaxi(coordinate.getCoordinate().getX(), coordinate.getCoordinate().getY(), coordinate.getRadius());
            if(taxi == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            return ResponseEntity.status(HttpStatus.OK).body(taxi.toString());

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/taxi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> findAll() {
        final List<String> resultList = new ArrayList<>();
        final Iterable<Taxi> all = taxiService.findAll();
        all.forEach(new Consumer<Taxi>() {
            @Override
            public void accept(Taxi taxi) {
                resultList.add(taxi.toString());
            }
        });
        return resultList;
    }






    @Autowired
    public void setTaxiService(TaxiService taxiService) {

        this.taxiService = taxiService;
    }

    @Autowired
    public void setStatusService(StatusService statusService) {

        this.statusService = statusService;
    }

    @Autowired
    public void setTaxiMapService(TaxiMap taximap) {

        this.taxiMapxi = taximap;
    }
}
