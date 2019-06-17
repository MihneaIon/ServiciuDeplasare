package com.licenta.app.UbberLicenta.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private float lat;
    private float lng;

    public Client(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Client()
    {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Float.compare(client.lat, lat) == 0 &&
                Float.compare(client.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lat, lng);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
