package com.licenta.app.UbberLicenta.beans;

import com.licenta.app.UbberLicenta.model.Position;
import com.licenta.app.UbberLicenta.model.SessionForAUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
public class DriverBean implements PositionInterface{

    List<Position> driverList = new ArrayList<>();

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Position driversList(){
        return new Position();
    }

    @Override
    public List<Position> listAllPosition(Position auxPosition) {
        return listAllPosition(auxPosition);
    }

    @Override
    public Position displayList() {
        return null;
    }

    @Override
    public List<SessionForAUser> listSendSessions(SessionForAUser sendSessionForAnUser) {
        return null;
    }

}
