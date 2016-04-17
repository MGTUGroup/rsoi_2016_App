package org.order.controller;

import com.google.gson.JsonObject;
import org.order.container.ContainerTaxi;
import org.order.container.Tuple;
import org.order.repository.orderRepository.OrderService;
import org.order.serializableObject.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by user on 11.04.16.
 */
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private OrderService orderService;
    private ContainerTaxi containerTaxi;
    private ConcurrentHashMap<Integer, Coordinate> tmpOrder = new ConcurrentHashMap<>();

    @RequestMapping(value = "/make_order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makeOrder(@RequestBody MakeOrder make_order) {
        try{
            if(!make_order.checkFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            tmpOrder.put(make_order.getPass_id(), make_order.getCoordinate());

            return new ResponseEntity<String>(HttpStatus.CREATED);

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allOrder() {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(tmpOrder.entrySet().toString());

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/taxi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allTaxi() {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(containerTaxi.toString());

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/add_taxi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTaxi(@RequestBody AddTaxi addtaxi) {
        try{
            if(!addtaxi.chaekFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            containerTaxi.addOrder(new Long(addtaxi.getTaxi_id()), addtaxi.getPass_id().intValue());

            return new ResponseEntity<String>(HttpStatus.OK);
        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/start_calculation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> startCalculation(@RequestBody ParametrTaxi parametr) {
        try {
            if(!parametr.checkFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);


            containerTaxi.startCalculation(new Long(parametr.getTaxi_id()));
            return new ResponseEntity<String>(HttpStatus.OK);

        } catch (NullPointerException ex1) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/stop_calculation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> stopCalculation(@RequestBody ParametrTaxi parametr) {
        try {
            if(!parametr.checkFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            Tuple tuple = containerTaxi.stopCalculation(new Long(parametr.getTaxi_id()));

            JsonObject json = new JsonObject();
            json.addProperty("pass_id", tuple.getB());
            json.addProperty("cost", tuple.getA() * 10);
            json.addProperty("code", "200");
            return ResponseEntity.status(HttpStatus.OK).body(json.toString());

        } catch (NullPointerException ex1) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/post_coordinates", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCoordinate(@RequestBody PostCoordinates post) {
        try{
            if(!post.chaeckFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);


            containerTaxi.addTaxiCoordinate(new Long(post.getTaxi_id().intValue()), post.getCoordinate().getX(), post.getCoordinate().getY());
            return new ResponseEntity<String>(HttpStatus.OK);

        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/cancel_order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCoordinate(@RequestBody StopCalculation parametr) {
        try{
            if(!parametr.checkFields())
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

            tmpOrder.remove(parametr.getPass_id());
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }catch (Exception ex)
        {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Autowired
    public void setOrderService(OrderService orderService) {

        this.orderService = orderService;
    }

    @Autowired
    public void setContainerTaxiService(ContainerTaxi containertaxi) {

        this.containerTaxi = containertaxi;
    }
}
