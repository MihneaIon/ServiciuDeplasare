package com.licenta.app.UbberLicenta.model;

import org.springframework.stereotype.Component;

import java.util.Objects;

public class SessionForAUser {
    ModelDeTransmitereAMesajului modelDeTransmitereAMesajului;
    private String sesiune;
    private boolean semafor;

    public boolean isSemafor() {
        return semafor;
    }

    public void setSemafor(boolean semafor) {
        this.semafor = semafor;
    }

    public SessionForAUser(ModelDeTransmitereAMesajului modelDeTransmitereAMesajului, String sesiune) {
        this.modelDeTransmitereAMesajului = modelDeTransmitereAMesajului;
        this.sesiune = sesiune;
    }

    public SessionForAUser() {

    }

    public ModelDeTransmitereAMesajului getPosition() {
        return modelDeTransmitereAMesajului;
    }

    public void setPosition(ModelDeTransmitereAMesajului position) {
        this.modelDeTransmitereAMesajului = position;
    }

    public String getSesiune() {
        return sesiune;
    }

    public void setSesiune(String sesiune) {
        this.sesiune = sesiune;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionForAUser that = (SessionForAUser) o;
        return Objects.equals(modelDeTransmitereAMesajului, that.modelDeTransmitereAMesajului) &&
                Objects.equals(sesiune, that.sesiune);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelDeTransmitereAMesajului, sesiune);
    }

    @Override
    public String toString() {
        return "SessionForAUser{" +
                "modelDeTransmitereAMesajului=" + modelDeTransmitereAMesajului +
                ", sesiune='" + sesiune + '\'' +
                ", semafor=" + semafor +
                '}';
    }
}
