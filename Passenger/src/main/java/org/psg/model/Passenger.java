package org.psg.model;

/**
 * Created by user on 08.04.16.
 */
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.psg.serializable.CustomListSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Passenger")
//@JsonIgnoreProperties({"status"})
public class Passenger implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "passenger_id")
    private Long passenger_id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    //@JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "status_id")
    //@JsonSerialize(using = CustomListSerializer.class)
    private Status status;


    public Passenger() {

    }
//    public Passenger(Passenger passenger) {
//
//        this.username = passenger.username;
//        this.email = passenger.email;
//        this.password = passenger.password;
//        this.sta
//    }

    public Passenger(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return passenger_id;
    }

    public void setId(Long id) {
        this.passenger_id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatuses(){ return  this.status; };

    public void setStatuses(Status status){ this.status = status; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + passenger_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status.getStatus() + '\'' +
                '}';
    }
}