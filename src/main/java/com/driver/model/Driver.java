package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Driver{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;
    private String mobile;
    private String password;
    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    Cab cab;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList=new ArrayList<>();

    public Driver() {
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public Driver(int driverId, String mobile, String password, Cab cab, List<TripBooking> tripBookingList) {
        this.driverId = driverId;
        this.mobile = mobile;
        this.password = password;
        this.cab = cab;
        this.tripBookingList = tripBookingList;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}