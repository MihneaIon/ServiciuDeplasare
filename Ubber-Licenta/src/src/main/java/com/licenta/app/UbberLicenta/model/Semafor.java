package com.licenta.app.UbberLicenta.model;

import org.springframework.stereotype.Component;

@Component
public class Semafor {

    private boolean semafor;

    public boolean isSemafor() {
        return semafor;
    }

    public void setSemafor(boolean semafor) {
        this.semafor = semafor;
    }

    public Semafor() {
    }

    public Semafor(boolean semafor) {
        this.semafor = semafor;
    }

    @Override
    public String toString() {
        return "Semafor{" +
                "semafor=" + semafor +
                '}';
    }
}
