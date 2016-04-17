package org.order.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by user on 11.04.16.
 */
@Entity
@Table(name = "Order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "status_id")
    private Long order_id;


    @Column(name = "pass_id", nullable = false)
    private int pass_id;

    @Column(name = "taxi_id", nullable = false)
    private int taxi_id;

    @Column(name = "x", nullable = false)
    private int x;

    @Column(name = "y", nullable = false)
    private int y;

    public Order() {
    }

    public Order(int pass_id, int taxi_id, int x, int y) {
        this.pass_id = pass_id;
        this.taxi_id = taxi_id;
        this.x = x;
        this.y = y;
    }

    public Long getId() {
        return order_id;
    }

    public void setId(Long id) {
        this.order_id = id;
    }

    public int getPassengerId() {
        return pass_id;
    }

    public void setPassengerId(int id) {
        this.pass_id = id;
    }

    public int getTaxiId() {
        return taxi_id;
    }

    public void setTaxiId(int id) {
        this.taxi_id = id;
    }

    public int getCoordinateX() {
        return this.x;
    }

    public void setCoordinateX(int x) {
        this.x = x;
    }

    public int getCoordinateY() {
        return this.y;
    }

    public void setCoordinateY(int y) {
        this.y = y;
    }


    @Override
    public String toString() {
        return "{" +
                "id=" + order_id +
                ", status='" + pass_id + '\'' +
                '}';
    }
}
