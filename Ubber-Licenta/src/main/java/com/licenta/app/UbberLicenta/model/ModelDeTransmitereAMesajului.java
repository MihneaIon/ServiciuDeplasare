package com.licenta.app.UbberLicenta.model;

public class ModelDeTransmitereAMesajului {

    private Position position;
    private String type;

    public ModelDeTransmitereAMesajului(Position position, String type) {
        this.position = position;
        this.type = type;
    }

    public ModelDeTransmitereAMesajului() {

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ModelDeTransmitereAMesajului{" +
                "position=" + position +
                ", type='" + type + '\'' +
                '}';
    }
}
