package com.licenta.app.UbberLicenta.model;

import java.util.Objects;

public class SessionForAUser {
    private Position position;
    private String sesiune;

    public SessionForAUser(Position position, String sesiune) {
        this.position = position;
        this.sesiune = sesiune;
    }

    public SessionForAUser() {

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
        return Objects.equals(position, that.position) &&
                Objects.equals(sesiune, that.sesiune);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, sesiune);
    }

    @Override
    public String toString() {
        return "SessionForAUser{" +
                "position=" + position +
                ", sesiune='" + sesiune + '\'' +
                '}';
    }
}
