package org.psg.controller;

/**
 * Created by user on 08.04.16.
 */
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.psg.model.Passenger;
import org.psg.model.Status;
import org.psg.repository.passengerRepository.PassengerService;
import org.psg.repository.statusRepository.StatusService;
import org.psg.serializable.ChangeStatus;
import org.psg.serializable.Login;
import org.psg.serializable.Parametr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerController {

    //@Inject
    private PassengerService passengerService;
    private StatusService statusService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello(){
        return "Hello there !";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        try {
            Thread.sleep(10000);
            return "Hello there Test !";
        }catch (InterruptedException ex)
        {
            return ex.getMessage();
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Passenger passenger) {
        //return passengerService.save(passenger);
        try {
            if (passenger == null)
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            passenger.setStatuses(statusService.findById((long)1)); // Исправить !!!!
            passengerService.save(passenger);
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

            Passenger passenger = passengerService.findByEmailAndPassword(login.getEmail(), login.getPassword());
            if(passenger == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            JsonObject obj = new JsonObject();
            obj.addProperty("pass_id",passenger.getId().toString());
            obj.addProperty("code", 200);
            return ResponseEntity.status(HttpStatus.OK).body(obj.toString());

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/passenger", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> findAll() {
        final List<String> resultList = new ArrayList<>();
        final Iterable<Passenger> all = passengerService.findAll();
        all.forEach(new Consumer<Passenger>() {
            @Override
            public void accept(Passenger passenger) {
                resultList.add(passenger.toString());
            }
        });
        return resultList;
    }

    @RequestMapping(value = "/check_status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cheakStatus(@RequestBody Parametr parametr) {
        try{
            if(!parametr.checkFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            Passenger passenger = passengerService.findById(new Long(parametr.getUser_id()));
            if(passenger == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            JsonObject obj = new JsonObject();
            obj.addProperty("status", passenger.getStatuses().getStatus());
            obj.addProperty("code", 200);
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

            Passenger passenger = passengerService.findById(new Long(ch_status.getUser_id()));
            if(passenger == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            passenger.setStatuses(current_status);
            passengerService.save(passenger);
            return new ResponseEntity<String>(HttpStatus.OK);

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


        @Autowired
    public void setPassengerService(PassengerService passengerService) {

        this.passengerService = passengerService;
    }

    @Autowired
    public void setStatusService(StatusService statusService) {

        this.statusService = statusService;
    }


}