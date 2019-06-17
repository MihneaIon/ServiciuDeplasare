package com.licenta.app.UbberLicenta.beans;

import com.licenta.app.UbberLicenta.model.Position;
import com.licenta.app.UbberLicenta.model.SessionForAUser;

import java.util.ArrayList;
import java.util.List;

public interface PositionInterface {

    List<Position> myList = new ArrayList<>();

    List<SessionForAUser> listSessionsForUsers = new ArrayList<>();

    List<Position> listAllPosition(Position auxPosition);

    Position displayList();

    List<SessionForAUser> listSendSessions(SessionForAUser transmitereDeSesiune);


    //Map<Integer, Position> listaCuDeToate = new TreeMap<>();


}
