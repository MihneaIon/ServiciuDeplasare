package com.licenta.app.UbberLicenta.closerCars;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DistanceBetweenThoPlaces {

    public final static double AVERAGE_RADIUS_OF_EARTH_KM=6371;

    private static final Logger logger = LogManager.getLogger(DistanceBetweenThoPlaces.class);

    // Haversine formula
    public static float calculateDistanceInKilometer(double userLat, double userLng, double carLat, double carLng)
    {
        float latDistance = (float) Math.toRadians(userLat - carLat);
        float lngDistance = (float) Math.toRadians(userLng - carLng);

        double distance=Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(carLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double returnDistance=2 * Math.atan2(Math.sqrt(distance), Math.sqrt(1 - distance));
        logger.info("Procedure distance ");

        return (float) ((AVERAGE_RADIUS_OF_EARTH_KM * (float) returnDistance));
    }


    // O interpretarea a mea dupa ce am citit cate ceva despre distanta
    public static float caluclareDistanta(float userLat, float userLng, float carLat, float carLng )
    {
        float latDistance = Math.abs(userLat-carLat);
        float lngDistance = Math.abs(userLng-carLng);
        float suma=latDistance*111+lngDistance*111;

        return suma;
    }
}
