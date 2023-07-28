package com.driver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private int tripBookingId;

    private String fromLocation;
    private String toLocation;

    private int distanceInKm;

    private TripStatus tripStatus;

    private int bill;

    public TripBooking(int tripBookingId, String fromLocation, String toLocation, int distanceInKm, TripStatus status, int bill) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.distanceInKm = distanceInKm;
        this.tripStatus = status;
        this.bill = bill;
    }

    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(int distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }
}