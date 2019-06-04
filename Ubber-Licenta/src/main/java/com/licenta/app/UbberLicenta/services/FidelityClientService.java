package com.licenta.app.UbberLicenta.services;

import com.licenta.app.UbberLicenta.model.Fidelity;

import java.util.List;

public interface FidelityClientService {

    Fidelity upgradeClientFidelity(int id, Fidelity fidelity);

    List<Fidelity> getAllClients();

    Fidelity getFidelity(int id);

    Fidelity addFidelityClient(Fidelity fidelity);

    int NumberOfRecords();

//    Fidelity getClientNameFidelity(String name);
}
