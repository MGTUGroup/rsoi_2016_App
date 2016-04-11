package org.psg.model;

import com.fasterxml.jackson.annotation.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by user on 10.04.16.
 */
@Entity
@Table(name = "Status_Passenger")
public class Status implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "status_id")
    private Long status_id;


    @Column(name = "status", nullable = false)
    private String status;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "passenger_id", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@JoinColumn(name = "passenger_id")
    private Set<Passenger> passengers;

    public Status() {
    }

    public Status(String status) {
        this.status = status;
    }

    public Long getId() {
        return status_id;
    }

    public void setId(Long id) {
        this.status_id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set getPassenger(){return this.passengers; }

    public void setPassenger(Set passengers){
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + status_id +
                ", status='" + status + '\'' +
                '}';
    }
}
