package com.licenta.app.UbberLicenta.beans;

import com.licenta.app.UbberLicenta.model.Position;
import com.licenta.app.UbberLicenta.model.SessionForAUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Component
public class SessionBean implements PositionInterface{

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SessionForAUser applicationScopedBeanSession(){
        return new SessionForAUser();
    }

    @Override
    public Position displayList() {
        return null;
    }

    @Override
    public List<Position> listAllPosition(Position auxPosition) {
        return null;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @Override
    public List<SessionForAUser> listaDeTransmitereDeSesiune(SessionForAUser transmitereDeSesiune) {
        listSessionsForUsers.add(transmitereDeSesiune);
        System.out.println("lista cu de toate "+ transmitereDeSesiune.toString());
        return listSessionsForUsers;
    }



}
