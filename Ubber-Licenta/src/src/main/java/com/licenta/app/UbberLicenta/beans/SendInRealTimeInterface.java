package com.licenta.app.UbberLicenta.beans;

import com.licenta.app.UbberLicenta.model.Position;

import java.util.ArrayList;
import java.util.List;

public interface SendInRealTimeInterface {

    List<Position> sendToTheInterface = new ArrayList<>();

    List<Position> addInList(Position myPosition);

}
