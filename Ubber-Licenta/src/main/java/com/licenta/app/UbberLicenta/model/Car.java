package com.licenta.app.UbberLicenta.model;

import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class Car {
    private String model;
    private String color;
    private Position position;
    private SizeCar sizeCar;
    private ScaledSizeCar scaledSizeCar;
    private String driverName;
    private String carRegistrationNumber;

    public Car() {
    }

    public Car(String model, String color, Position position, SizeCar sizeCar, ScaledSizeCar scaledSizeCar, String driverName,String carRegistrationNumber) {
        this.model = model;
        this.color = color;
        this.position = position;
        this.sizeCar = sizeCar;
        this.scaledSizeCar = scaledSizeCar;
        this.driverName=driverName;
        this.carRegistrationNumber=carRegistrationNumber;

    }

    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public Car(String model, String color, Position position, String driverName,String carRegistrationNumber) {
        this.model = model;
        this.color = color;
        this.position = position;
        this.driverName=driverName;
        this.carRegistrationNumber=carRegistrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public SizeCar getSizeCar() {
        return sizeCar;
    }

    public void setSizeCar(SizeCar sizeCar) {
        this.sizeCar = sizeCar;
    }

    public ScaledSizeCar getScaledSizeCar() {
        return scaledSizeCar;
    }

    public void setScaledSizeCar(ScaledSizeCar scaledSizeCar) {
        this.scaledSizeCar = scaledSizeCar;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public void setCarRegistrationNumber(String carRegistrationNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(model, car.model) &&
                Objects.equals(color, car.color) &&
                Objects.equals(position, car.position) &&
                Objects.equals(sizeCar, car.sizeCar) &&
                Objects.equals(scaledSizeCar, car.scaledSizeCar) &&
                Objects.equals(driverName, car.driverName) &&
                Objects.equals(carRegistrationNumber, car.carRegistrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, color, position, sizeCar, scaledSizeCar, driverName, carRegistrationNumber);
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", position=" + position +
//                ", sizeCar=" + sizeCar +
//                ", scaledSizeCar=" + scaledSizeCar +
                ", driverName='" + driverName + '\'' +
                ", carRegistrationNumber='" + carRegistrationNumber + '\'' +
                '}';
    }
}
