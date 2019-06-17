package com.licenta.app.UbberLicenta.controlers;

import com.licenta.app.UbberLicenta.beans.DriverBean;
import com.licenta.app.UbberLicenta.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/drivers")
public class HandleDrivers {

    private List<Position> listOfPositions = new ArrayList<>();

    @Autowired
    DriverBean myDriverBean;

    @Resource(name = "driversList")
    Position myPosition;

    @RequestMapping(value = "/driver/position", method = RequestMethod.POST)
    public List<Position> addDriversPositions(){
        System.out.println(myPosition+ "aici nu mai stiu ce trebuie sa se intample");
//        for (Position position:)
        return myDriverBean.myList;
    }

    // de modificat metoda
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public List<Position> displayDriver(){
        for(Position statusDriver:myDriverBean.myList){
            System.out.println(statusDriver);
            listOfPositions.add(statusDriver);
        }
        return listOfPositions;
    }


}
