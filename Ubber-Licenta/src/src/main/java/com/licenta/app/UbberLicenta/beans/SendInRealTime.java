package com.licenta.app.UbberLicenta.beans;

import com.licenta.app.UbberLicenta.model.Position;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
public class SendInRealTime implements SendInRealTimeInterface{
    private static boolean amIntrat = false;


    @Override
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public List<Position> addInList(Position myPosition) {
        if(sendToTheInterface.size()>0){
            System.out.println("Am avut ceva in lista");
            sendToTheInterface.clear();
        }
        sendToTheInterface.add(myPosition);
        System.out.println("Lista de pozitit pe care am adaugat-o este"+ sendToTheInterface.get(0).toString());
        return sendToTheInterface;
    }

}
