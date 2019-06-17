package com.licenta.app.UbberLicenta.controlers;

import com.licenta.app.UbberLicenta.beans.UserNameFidelity;
import com.licenta.app.UbberLicenta.model.Fidelity;
import com.licenta.app.UbberLicenta.services.FidelityClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/fidelity")
public class FidelityController {

    private FidelityClientService fidelityClientService;

    private Fidelity globalFidelity;

    @Autowired
    public FidelityController(FidelityClientService fidelityClientService) {
        this.fidelityClientService = fidelityClientService;
    }

    @Autowired
    UserNameFidelity myUserNameFidelity;

    @ResponseBody
    @RequestMapping(path = "/fidelity", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Fidelity dummyCountFidelity(@RequestBody String name) {
        List<Fidelity> listOfCliets;
        try {
            listOfCliets = fidelityClientService.getAllClients();
            Fidelity onlyUser = myUserNameFidelity.getActualFidelity();
            int numarapartii = 0;
            for (Fidelity myFidelityClient : listOfCliets) {
                if (myFidelityClient.getClientName().equals(onlyUser.getClientName()) && myFidelityClient.getNumbersOfRoads() < 100) {
                    myFidelityClient.setNumbersOfRoads(myFidelityClient.getNumbersOfRoads() + 1);
                    fidelityClientService.upgradeClientFidelity(myFidelityClient.getId(), myFidelityClient);
                    globalFidelity = myFidelityClient;
                    System.out.println(globalFidelity);
                    return myFidelityClient;
                } else if (myFidelityClient.getNumbersOfRoads() == 100) {
                    myFidelityClient.setNumbersOfRoads(0);
                    fidelityClientService.upgradeClientFidelity(myFidelityClient.getId(), myFidelityClient);
                } else {
                    numarapartii++;
                }
            }
            if (numarapartii == fidelityClientService.getAllClients().size()) {
                onlyUser.setNumbersOfRoads(1);
                fidelityClientService.addFidelityClient(onlyUser);
            } else if (fidelityClientService.getAllClients().size() == 0 && numarapartii == 1) {
                fidelityClientService.addFidelityClient(onlyUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Fidelity(3, "dummyName");
    }

    @ResponseBody
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(path = "/getFidelity", method = RequestMethod.GET)
    public Fidelity showFidelity() {
        int number=0;
        Fidelity onlyUser = myUserNameFidelity.getActualFidelity();
        System.out.println("acesta este singurul user"+ onlyUser);
        List<Fidelity> listOfCliets;
        listOfCliets = fidelityClientService.getAllClients();
        for (Fidelity myFidelityClient : listOfCliets) {
            if (myFidelityClient.getClientName().equals(onlyUser.getClientName()) && myFidelityClient.getNumbersOfRoads() < 100) {
                myFidelityClient.setNumbersOfRoads(myFidelityClient.getNumbersOfRoads() + 1);
                globalFidelity = myFidelityClient;
                System.out.println(globalFidelity);
                return myFidelityClient;
            } else if (myFidelityClient.getNumbersOfRoads() == 100) {
                number++;
            }
        }
        if(number == listOfCliets.size() || listOfCliets.size()==0){
            return new Fidelity(0,"dummyName");
        }
        return new Fidelity(0,"dummyName");
    }

    @ResponseBody
    @RequestMapping(path = "/post", method = RequestMethod.POST)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Fidelity puneCeva(@RequestBody String name){
        Fidelity auxFidelity = new Fidelity(4,"ana");
        fidelityClientService.addFidelityClient(auxFidelity);
        return auxFidelity;
    }

}


