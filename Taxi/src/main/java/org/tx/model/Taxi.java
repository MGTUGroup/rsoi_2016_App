package org.tx.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 11.04.16.
 */
@Entity
@Table(name = "Taxi")
public class Taxi implements Serializable {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "taxi_id")
    private Long taxi_id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    //@JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "mark", nullable = false)
    private String mark;

    @Column(name = "model", nullable = false)
    private String model;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "status_id")
    //@JsonSerialize(using = CustomListSerializer.class)
    private Status status;


    @Column(name = "statenumber", nullable = false)
    private String statenumber;

    @Column(name = "region", nullable = false)
    private Integer region;


    public Long getId() {
        return taxi_id;
    }

    public void setId(Long id) {
        this.taxi_id = id;
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

    public String getStatenumber(){ return  this.statenumber; };
    public void setStatenumber(String statenumber){ this.statenumber = statenumber; }

    public String getMark() { return mark; }
    public void setMark(String mark) { this.mark = mark; }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public Integer getRegion() {
        return region;
    }
    public void setRegion(Integer region) {
        this.region = region;
    }

    public Status getStatus(){ return  this.status; };
    public void setStatus(Status status){ this.status = status; }

    @Override
    public String toString() {
        return "{" +
                "taxi_id=" + taxi_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", state_number='" + statenumber + '\'' +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", region='" + region + '\'' +
                '}';
    }


}
