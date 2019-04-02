package com.licenta.app.UbberLicenta.model;

import com.google.maps.model.DirectionsResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;


public class Bill {

    private String origin;

    private String destination;

    public Bill(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Bill() {

    }

    public float returenzPretulCalatoriei(){
        DirectionsResult myDirectionREsult;
        double tarif;
        myDirectionREsult = DataAboutRoutes.findTheBestRoute( this.origin, this.destination);
        System.out.println(myDirectionREsult.routes[0].legs[0].distance.humanReadable+ "aici afisez numarul drumurilor");
        float distanceInKm = (float) (myDirectionREsult.routes[0].legs[0].distance.inMeters);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        distanceInKm= Float.valueOf(decimalFormat.format(distanceInKm/1000));
        String indiceDeDrum = myDirectionREsult.routes[0].summary.toUpperCase();
        if(myDirectionREsult.routes[0].summary.contains("DN71") || myDirectionREsult.routes[0].summary.contains("E-")){
            tarif = 2 * distanceInKm;
        }
        else {
            tarif = 1.5 * distanceInKm;
        }
        BigDecimal price = new BigDecimal(2);
        price = price.setScale(2, RoundingMode.HALF_EVEN); // here the value is correct (625.30)
        System.out.println(price);
        return price.floatValue(); // but here the values is 625.3
    }

}
