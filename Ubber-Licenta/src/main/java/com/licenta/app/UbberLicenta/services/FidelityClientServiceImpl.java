package com.licenta.app.UbberLicenta.services;

import com.licenta.app.UbberLicenta.model.Fidelity;
import com.licenta.app.UbberLicenta.repository.FidelityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FidelityClientServiceImpl implements FidelityClientService {

    private FidelityRepository fidelityRepository;

    @Autowired
    public FidelityClientServiceImpl(FidelityRepository fidelityRepository){
        this.fidelityRepository = fidelityRepository;
    }

    @Override
    public Fidelity getFidelity(int id) {
        return fidelityRepository.findById(id).get();
    }

    @Override
    public List<Fidelity> getAllClients() {
        return fidelityRepository.listOfAllFidelity();
    }

    @Override
    public Fidelity upgradeClientFidelity(int id, Fidelity fidelity) {
        Fidelity fidelity1Update =  getFidelity(id);
        fidelity1Update.setClientName(fidelity.getClientName());
        fidelity1Update.setNumbersOfRoads(fidelity.getNumbersOfRoads());
        return fidelityRepository.saveAndFlush(fidelity1Update);
    }

    @Override
    public Fidelity addFidelityClient(Fidelity fidelity) {

        return fidelityRepository.saveAndFlush(fidelity);
    }

    @Override
    public int NumberOfRecords() {
        return fidelityRepository.numberOfRecords();
    }

    //    @Override
//    public Fidelity getClientNameFidelity(String name) {
//        return fidelityRepository.serchForFidelityClient(name);
//    }
}
