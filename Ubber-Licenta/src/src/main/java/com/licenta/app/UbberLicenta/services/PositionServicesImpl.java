package com.licenta.app.UbberLicenta.services;

import com.licenta.app.UbberLicenta.model.Position;
import com.licenta.app.UbberLicenta.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PositionServicesImpl implements PositionServices{

    private ClientRepository clientRepository;

    @Autowired
    public PositionServicesImpl(ClientRepository clientRepository)
    {
        this.clientRepository=clientRepository;
    }

    @Override
    public Position getPositions() {
        return null;
    }
}
