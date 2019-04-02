package com.licenta.app.UbberLicenta.controlers;

import com.licenta.app.UbberLicenta.model.Bill;
import com.licenta.app.UbberLicenta.model.Client;
import com.licenta.app.UbberLicenta.model.DestinationClient;
import com.licenta.app.UbberLicenta.repository.ClientRepository;
import com.licenta.app.UbberLicenta.repository.DestinationClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/price")
public class CalculateTaxes {

    @Autowired
    ClientRepository myClientRepository;

    @Autowired
    DestinationClientRepository myDestinationClientRepository;

    @RequestMapping(path = "/settleThePrice",method = RequestMethod.GET)
    public float showTheCost(){

        long dateAboutPositionOfTheClient = myClientRepository.count();
        long dateAboutPositionOfTheDestination = myDestinationClientRepository.count();
        Client clientPosition = myClientRepository.findById(dateAboutPositionOfTheClient).get();
        DestinationClient destinationClient = myDestinationClientRepository.findById(dateAboutPositionOfTheDestination).get();
        String origin = clientPosition.getLat() + "," + clientPosition.getLng();
        String destination = destinationClient.getLat() + "," + destinationClient.getLng();
        Bill roadBill = new Bill(origin,destination);
        float pricForTheSelectedRoad=0;
        pricForTheSelectedRoad = roadBill.returenzPretulCalatoriei();
        return pricForTheSelectedRoad;
    }

}
