package com.licenta.app.UbberLicenta.model;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.io.IOException;
public class DataAboutRoutes {

    public static DirectionsResult findTheBestRoute(String origin, String destination) {
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyAuH_VblA7ViIJUFqSfVsLnSs7LS1fdEA0").build();
//            DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(context);
            DirectionsResult calculateRoutes = DirectionsApi.newRequest(context)
                    .alternatives(true)
                    .mode(TravelMode.DRIVING)
                    .origin(origin)
                    .destination(destination)
                    .await();
            System.out.println(calculateRoutes.routes[0].legs[0].steps.length);
            return calculateRoutes;
        } catch (InterruptedException | IOException | ApiException interrupedException) {
            System.out.println("am intrat pe eroare");
            interrupedException.printStackTrace();
        }
        return null;
    }
}
