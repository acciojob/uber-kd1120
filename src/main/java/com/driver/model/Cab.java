package com.driver.model;


import javax.persistence.*;

@Entity
public class Cab {

    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private boolean available;

    private int perKmRate;

    @OneToOne
    private Driver driver;

    public Cab(int id, boolean available, int perKmRate, Driver driver) {
        this.id = id;
        this.available = available;
        this.perKmRate = perKmRate;
        this.driver = driver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}