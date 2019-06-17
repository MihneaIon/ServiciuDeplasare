package com.licenta.app.UbberLicenta.controlers;

import com.google.maps.model.DirectionsResult;
import com.licenta.app.UbberLicenta.model.Client;
import com.licenta.app.UbberLicenta.model.DataAboutRoutes;
import com.licenta.app.UbberLicenta.model.Position;
import com.licenta.app.UbberLicenta.model.SessionForAUser;
import com.licenta.app.UbberLicenta.repository.ClientRepository;
import com.licenta.app.UbberLicenta.repository.DestinationClientRepository;
import com.licenta.app.UbberLicenta.webSocketHandler.NotificationHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/distanta")
public class DirectionController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DestinationClientRepository destinationClientRepository;

    @Autowired
    private NotificationHandler myNotificationHandler;

    @Autowired
    private HandleSessionBeanMessageController handleSessionBeanMessageController;

    private static final Logger logger = LogManager.getLogger(DirectionController.class);

    @RequestMapping(path = "/getIntermediateRoad", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Position> intermediateRoad() {
        long dateAboutPositionOfTheClient = clientRepository.count();
        long dateAboutPositionOfTheDestination = destinationClientRepository.count();
        logger.info("Fetching id {}", dateAboutPositionOfTheClient);
        logger.info("Fetching id for destination {}", dateAboutPositionOfTheDestination);
        Client clientPosition = clientRepository.findById(dateAboutPositionOfTheClient).get();
//        Position destinationClient = handleMessageController.getApplicationScopeMessage();
        SessionForAUser destinationClient = handleSessionBeanMessageController.getApplicationScopeSessionMessage();

        String origin = clientPosition.getLat() + "," + clientPosition.getLng();
        String destination = destinationClient.getPosition().getPosition().getLat() + "," + destinationClient.getPosition().getPosition().getLng();
        System.out.println("Lat destinatie"+destinationClient.getPosition().getPosition().getLat()+ " lng destinatie"+ destinationClient.getPosition().getPosition().getLng() );
        List<Position> myPositionList = new ArrayList<>();
        System.out.println(myPositionList.size() + "aceasta este dimensiunea ");
        DirectionsResult result;
        try {
            result = DataAboutRoutes.findTheBestRoute(origin, destination);

            for (int i = 0; i < result.routes[0].legs[0].steps.length; i++) {
                String firstStepLat = result.routes[0].legs[0].steps[i].startLocation.lat + "";
                String firstStepLng = result.routes[0].legs[0].steps[i].startLocation.lng + "";
                String lastStepLat = result.routes[0].legs[0].steps[i].endLocation.lat + "";
                String lastStepLng = result.routes[0].legs[0].steps[i].endLocation.lng + "";
                Position updateStartPosition = new Position(Float.parseFloat(firstStepLat), Float.parseFloat(firstStepLng));
                Position updateEndPosition = new Position(Float.parseFloat(lastStepLat), Float.parseFloat(lastStepLng));
                myPositionList.add(updateStartPosition);
                myPositionList.add(updateEndPosition);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("Sizu-ul vectorului" + myPositionList.size());
        System.out.println(myPositionList.toString());
        return myPositionList;
    }

}
