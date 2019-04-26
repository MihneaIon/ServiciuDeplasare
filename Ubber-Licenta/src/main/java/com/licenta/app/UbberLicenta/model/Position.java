package com.licenta.app.UbberLicenta.model;

import javax.persistence.*;

//@Entity
//@Table(name = "position")
public class Position {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private float lat;
    private float lng;

    public Position() {
    }

    public Position(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Position(int id,float lat, float lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "position{" +
                "lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
