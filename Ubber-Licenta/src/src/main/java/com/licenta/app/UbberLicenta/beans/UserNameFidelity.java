package com.licenta.app.UbberLicenta.beans;

import com.licenta.app.UbberLicenta.model.Fidelity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserNameFidelity {

    private List<Fidelity> listOfFidelity= new ArrayList<>();
    private Fidelity actualFidelity;

    public Fidelity getActualFidelity() {
        return actualFidelity;
    }

    public void setActualFidelity(Fidelity actualFidelity) {
        this.actualFidelity = actualFidelity;
    }

    public List<Fidelity> getListOfFidelity() {
        return listOfFidelity;
    }

    public void setListOfFidelity(List<Fidelity> listOfFidelity) {
        this.listOfFidelity = listOfFidelity;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Fidelity applicationScopeBeanFidelity(Fidelity nameOfClient)
    {
        listOfFidelity.add(nameOfClient);
        System.out.println(" "+listOfFidelity.toString());
        return nameOfClient;
    }

}
