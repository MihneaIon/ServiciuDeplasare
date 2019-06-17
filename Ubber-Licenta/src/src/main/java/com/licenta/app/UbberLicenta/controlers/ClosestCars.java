package com.licenta.app.UbberLicenta.controlers;

import com.google.maps.*;
import com.licenta.app.UbberLicenta.TransformDoubleToFloat.TransformDoubleToFloat;
import com.licenta.app.UbberLicenta.closerCars.DistanceBetweenThoPlaces;
import com.licenta.app.UbberLicenta.compare.CompareDistance;
import com.licenta.app.UbberLicenta.model.*;
//import com.licenta.app.UbberLicenta.repository.CarRepository;

import com.licenta.app.UbberLicenta.repository.ClientRepository;
import com.licenta.app.UbberLicenta.repository.DestinationClientRepository;
import com.licenta.app.UbberLicenta.webSocketHandler.NotificationHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Stream;

@Component
@RestController
public class ClosestCars {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DestinationClientRepository destinationClientRepository;

    @Autowired
    private NotificationHandler myNotificationHandler;

    @Autowired
    private HandleDrivers myHandleDrivers;

    private static final Logger logger = LogManager.getLogger(ClosestCars.class);

    @ResponseBody
    @RequestMapping(path = "/cars", method = RequestMethod.GET)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Object[] displayDistance() {

        long dateAboutPositionOfTheClient = clientRepository.count();
        long dateAboutPositionOfTheDestination = destinationClientRepository.count();
        Client clientPosition = clientRepository.findById(dateAboutPositionOfTheClient).get();
        System.out.println(clientPosition);
        DestinationClient destinationClient = destinationClientRepository.findById(dateAboutPositionOfTheDestination).get();
        Map<Car, Float> theClosestCars = new HashMap<>();
        CompareDistance myCompareDistance = new CompareDistance();
        Position myPosition = new Position(TransformDoubleToFloat.reversa(45.638296), TransformDoubleToFloat.reversa(25.6179385)); //
        Position myPosition2 = new Position(TransformDoubleToFloat.reversa(45.659797), TransformDoubleToFloat.reversa(25.6125651)); //
        Position myPosition3 = new Position(TransformDoubleToFloat.reversa(45.6509493), TransformDoubleToFloat.reversa(25.5860017)); //
        Position myPosition4 = new Position(TransformDoubleToFloat.reversa(45.6696955), TransformDoubleToFloat.reversa(25.5955718)); //
        Position myPosition5 = new Position(TransformDoubleToFloat.reversa(45.6157052), TransformDoubleToFloat.reversa(25.6367357)); //Noua
        Position myPosition6 = new Position(TransformDoubleToFloat.reversa(45.8608738), TransformDoubleToFloat.reversa(25.8085177)); // Sf Gheorghe
        Position myPosition7 = new Position(TransformDoubleToFloat.reversa(45.1313222), TransformDoubleToFloat.reversa(25.3715439)); // Fieni
        Position myPosition8 = new Position(TransformDoubleToFloat.reversa(46.7822424), TransformDoubleToFloat.reversa(23.6047355)); // Cluj
        Position myPosition9 = new Position(TransformDoubleToFloat.reversa(45.3482449), TransformDoubleToFloat.reversa(25.5492405)); // Sinaia
        List<Position> keepTheDriver = myHandleDrivers.displayDriver();
//        System.out.println("What I expected for "+ keepTheDriver.getLng()+" "+ keepTheDriver.getLng());
        ScaledSizeCar myScaledSizeCar = new ScaledSizeCar("20", "20");
        SizeCar mySizeCar = new SizeCar("20", "20");
        System.out.println("am intrat");
        List<Car> myCarList = new ArrayList<>();
        Car car1 = new Car("Model", "red", myPosition, mySizeCar, myScaledSizeCar, "Gica", "BV 01 AND");
        Car car2 = new Car("Model", "blue", myPosition2, mySizeCar, myScaledSizeCar, "Iona", "BV 02 AND");
        Car car3 = new Car("Model", "red", myPosition3, mySizeCar, myScaledSizeCar, "Mihai", "BV 03 AND");
        Car car4 = new Car("Model", "orange", myPosition4, mySizeCar, myScaledSizeCar, "Bobo", "BV 04 AND");
        Car car5 = new Car("Model", "purple", myPosition5, mySizeCar, myScaledSizeCar, "Dan", "BV 05 AND");
        Car car6 = new Car("Model", "lime", myPosition6, mySizeCar, myScaledSizeCar, "Daniel", "BV 06 AND");
        Car car7 = new Car("Model", "azure", myPosition7, mySizeCar, myScaledSizeCar, "Sorin", "BV 07 AND");
        Car car8 = new Car("Model", "grey", myPosition8, mySizeCar, myScaledSizeCar, "Luca", "BV 08 AND");
        Car car9 = new Car("Model", "azure", myPosition9, mySizeCar, myScaledSizeCar, "Dany", "BV 09 AND");
        for (int i=0;i<keepTheDriver.size();i++){
            Car carDriver = new Car("Model", "negru", keepTheDriver.get(i), mySizeCar, myScaledSizeCar, "Marius", "BV 010 AND");
            System.out.println(carDriver);
            myCarList.add(carDriver);
        }
        keepTheDriver.clear();
        myCarList.add(car1);
        myCarList.add(car2);
        myCarList.add(car3);
        myCarList.add(car4);
        myCarList.add(car5);
        myCarList.add(car6);
        myCarList.add(car7);
        myCarList.add(car8);
        myCarList.add(car9);

        System.out.println("Size car list "+myCarList.size());
        for (Car auxCar : myCarList) {
            theClosestCars.put(auxCar, DistanceBetweenThoPlaces.calculateDistanceInKilometer(clientPosition.getLat(), clientPosition.getLng(),
                    auxCar.getPosition().getLat(), auxCar.getPosition().getLng()));
            System.out.println(DistanceBetweenThoPlaces.calculateDistanceInKilometer(clientPosition.getLat(), clientPosition.getLng(),
                    auxCar.getPosition().getLat(), auxCar.getPosition().getLng()));
        }
        Stream<Map.Entry<Car, Float>> sorted = theClosestCars.entrySet().stream().sorted(Map.Entry.comparingByValue());
        Map<Car, Float> sortareMap = theClosestCars.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(3).collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);//.limit(3).forEach(System.out::println);
        List<Car> auxList = new ArrayList<>();
        Car[] objectArray = sortareMap.keySet().toArray(new Car[0]);
        logger.info("successing search", objectArray);
        String origin = clientPosition.getLat() + "," + clientPosition.getLng();
        String destination = destinationClient.getLat() + "," + destinationClient.getLng();
        return objectArray;
    }

    public static DirectionsApiRequest newRequest(GeoApiContext context) {
        return new DirectionsApiRequest(context);
    }

    public static DirectionsApiRequest getDirection(GeoApiContext context, String origin, String destination) {
        return new DirectionsApiRequest(context).origin(origin).destination(destination);
    }

}