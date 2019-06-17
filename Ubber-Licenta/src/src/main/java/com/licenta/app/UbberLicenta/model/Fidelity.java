package com.licenta.app.UbberLicenta.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Fidelity")
public class Fidelity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numbersOfRoads;

    private String clientName;

    public Fidelity(){

    }

    public Fidelity(int numbersOfRoads) {
        this.numbersOfRoads = numbersOfRoads;
    }

    public Fidelity(int numbersOfRoads, String clientName) {
        this.numbersOfRoads = numbersOfRoads;
        this.clientName = clientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumbersOfRoads() {
        return numbersOfRoads;
    }
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setNumbersOfRoads(int numbersOfRoads) {
        this.numbersOfRoads = numbersOfRoads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fidelity fidelity = (Fidelity) o;
        return id == fidelity.id &&
                numbersOfRoads == fidelity.numbersOfRoads &&
                Objects.equals(clientName, fidelity.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numbersOfRoads, clientName);
    }

    @Override
    public String toString() {
        return "Fidelity{" +
                "id=" + id +
                ", numbersOfRoads=" + numbersOfRoads +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
