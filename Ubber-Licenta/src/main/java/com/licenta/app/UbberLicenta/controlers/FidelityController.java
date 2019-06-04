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
            System.out.println(listOfCliets + "ddsnskdndsf");
            System.out.println(myUserNameFidelity.getActualFidelity() + "si acestia sunt:" + myUserNameFidelity.getActualFidelity());
            Fidelity onlyUser = myUserNameFidelity.getActualFidelity();
            System.out.println(myUserNameFidelity.getActualFidelity());
            System.out.println(listOfCliets.toString());
            int numarapartii = 0;
            for (Fidelity myFidelityClient : listOfCliets) {
                if (myFidelityClient.getClientName().equals(onlyUser.getClientName()) && myFidelityClient.getNumbersOfRoads() < 100 ){
                    myFidelityClient.setNumbersOfRoads(myFidelityClient.getNumbersOfRoads() + 1);
                    fidelityClientService.upgradeClientFidelity(myFidelityClient.getId(), myFidelityClient);
                    globalFidelity = myFidelityClient;
                    System.out.println(globalFidelity);
                    return myFidelityClient;
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
        return new Fidelity(3, "Unknown");
    }

    @ResponseBody
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(path = "/getFidelity", method = RequestMethod.GET)
    public Fidelity showFidelity() {
        return globalFidelity;
    }


}
