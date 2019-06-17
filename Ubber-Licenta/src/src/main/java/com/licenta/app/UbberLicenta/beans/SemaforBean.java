package com.licenta.app.UbberLicenta.beans;

import com.licenta.app.UbberLicenta.model.Semafor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
public class SemaforBean {

    private int schimba=0;

    public int getSchimba() {
        return schimba;
    }

    public void setSchimba(int schimba) {
        this.schimba = schimba;
    }

    public SemaforBean(int schimba) {
        this.schimba = schimba;
    }

    public SemaforBean() {
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Semafor changeMySemafor(){
        schimba =1;
        return new Semafor(true);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Semafor backToState(){
        schimba = 0;
        return new Semafor(false);
    }
}
