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
    public ResponseEntity<String> login(@RequestParam(value="email") String email,@RequestParam(value="password") String password) {

        try{

            Passenger passenger = passengerService.findByEmailAndPassword(email, password);
            if(passenger == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            JsonObject obj = new JsonObject();
            obj.addProperty("pass_id",passenger.getId().toString());
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

    @RequestMapping(value = "/cheak_status/{passenger_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cheakStatus(@PathVariable long passenger_id) {
        try{

            Passenger passenger = passengerService.findById(new Long(passenger_id));
            if(passenger == null)
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

            JsonObject obj = new JsonObject();
            obj.addProperty("status", passenger.getStatuses().getStatus());
            return ResponseEntity.status(HttpStatus.OK).body(obj.toString());

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/change_status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeStatus(@RequestParam(value="user_id") long user_id,@RequestParam(value="status") String status) {
        try{
            Status current_status = statusService.findByStatus(status);
            if(current_status == null)
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            Passenger passenger = passengerService.findById(new Long(user_id));
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