package com.licenta.app.UbberLicenta.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "client_destination")
public class DestinationClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private float lat;
    private float lng;

    public DestinationClient(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public DestinationClient() {
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
        DestinationClient that = (DestinationClient) o;
        return id == that.id &&
                Float.compare(that.lat, lat) == 0 &&
                Float.compare(that.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lat, lng);
    }

    @Override
    public String toString() {
        return "DestinationClient{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
