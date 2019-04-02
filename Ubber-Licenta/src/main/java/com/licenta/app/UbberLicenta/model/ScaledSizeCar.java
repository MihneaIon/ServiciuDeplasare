package com.licenta.app.UbberLicenta.model;

public class ScaledSizeCar {

    private String width;
    private String height;

    public ScaledSizeCar() {

    }

    public ScaledSizeCar(String width, String height) {
        this.width = width;
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "SizeCar{" +
                "width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
