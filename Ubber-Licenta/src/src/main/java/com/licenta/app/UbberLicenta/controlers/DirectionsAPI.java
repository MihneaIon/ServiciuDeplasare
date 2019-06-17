package com.licenta.app.UbberLicenta.controlers;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.internal.ApiConfig;
import com.google.maps.internal.ApiResponse;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodedWaypoint;


public class DirectionsAPI {

    static final ApiConfig API_Config = new ApiConfig("");

    private DirectionsAPI() {

    }

    public static DirectionsApiRequest newRequest(GeoApiContext context) {
        return new DirectionsApiRequest(context);
    }

    public static DirectionsApiRequest getDirections(
            GeoApiContext context, String origin, String destination
    ) {
        return new DirectionsApiRequest(context).origin(origin).destination(destination);
    }

    static class Response implements ApiResponse<DirectionsResult>{
        public String status;
        public String errorMessage;
        public GeocodedWaypoint[] geocodedWaypoints;
        public DirectionsRoute[] routes;


        @Override
        public boolean successful() {
            return "OK".equals(status) || "ZERO_RESULTS".equals(status);
        }

        @Override
        public DirectionsResult getResult() {
            DirectionsResult result = new DirectionsResult();
            result.geocodedWaypoints = geocodedWaypoints;
            result.routes = routes;
            return result;
        }

        @Override
        public ApiException getError() {
            if (successful()) {
                return null;
            }
            return ApiException.from(status, errorMessage);
        }
    }
}
