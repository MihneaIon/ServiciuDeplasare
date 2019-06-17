package com.licenta.app.UbberLicenta.controlers;

import com.licenta.app.UbberLicenta.model.Client;
import com.licenta.app.UbberLicenta.model.DestinationClient;
import com.licenta.app.UbberLicenta.repository.ClientRepository;
import com.licenta.app.UbberLicenta.repository.DestinationClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/position")
public class ClientPosition {

    @Autowired
    ClientRepository myClientRepository;

    @Autowired
    DestinationClientRepository myDestinationClientRepository;

    @RequestMapping(path = "/takeClientPosition",method = RequestMethod.POST)
    public Client takePositionOfAClient(@RequestBody Client myClient)
    {

        System.out.println(myClient);
        return myClientRepository.saveAndFlush(myClient);
    }

    @RequestMapping(path = "/takeDestinationPosition",method = RequestMethod.POST)
    public DestinationClient takeDestinationPositionOfAClient(@RequestBody DestinationClient myDestinationClient)
    {
        System.out.println(myDestinationClient);
        return myDestinationClientRepository.saveAndFlush(myDestinationClient);
    }

    @RequestMapping(path = "/salutare",method = RequestMethod.GET)
    public Integer numarceva(){

        return (int)myClientRepository.count();
    }

}