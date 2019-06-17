package com.licenta.app.UbberLicenta.controlers;

import com.licenta.app.UbberLicenta.model.Client;
import com.licenta.app.UbberLicenta.model.Position;
import com.licenta.app.UbberLicenta.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/driverPositions")
public class DriverPositions {

    @Autowired
    ClientRepository myClientRepository;

    @RequestMapping(path = "/postPositions",method = RequestMethod.POST)
    public Client getPositions(Client myPositions){
        return myClientRepository.saveAndFlush(myPositions);
    }
}
