package com.licenta.app.UbberLicenta.controlers;

import com.licenta.app.UbberLicenta.beans.SessionBean;
import com.licenta.app.UbberLicenta.model.SessionForAUser;
import com.licenta.app.UbberLicenta.webSocketHandler.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping(path = "/scope")
public class HandleSessionBeanMessageController {

    @Autowired
    private NotificationHandler seesionNotificationHandler;

    @Autowired
    private SessionBean sessionBean;

    @Resource(name = "applicationScopedBeanSession")
    SessionForAUser applicationScopeBeanSession;

    @RequestMapping(value = "/scopes/application/session", method = RequestMethod.GET)
    public SessionForAUser getApplicationScopeSessionMessage() {

        sessionBean.listSessionsForUsers.add(applicationScopeBeanSession);
        System.out.println(sessionBean.listSessionsForUsers);
        System.out.println("size-ul listei cu sesiuni" + sessionBean.listSessionsForUsers.size());
        List<String> mySessionList = new ArrayList<>();
        for (Map.Entry<String, List<WebSocketSession>> entry : seesionNotificationHandler.sessions.entrySet()) {
            System.out.println("Key =" + entry.getKey() + "Value= " + entry.getValue());
            mySessionList.add(entry.getKey() + "");
        }
        System.out.println(sessionBean.listSessionsForUsers+" si sizeu-ul "+ sessionBean.listSessionsForUsers.size());
        System.out.println(sessionBean.listSessionsForUsers.get(0).getSesiune()+"aici primesc un warn");

        for (int i = 0; i < seesionNotificationHandler.sessions.size(); i++) {
            System.out.println("i-ul este "+i);
            for (int j = 0; j < mySessionList.size(); j++) {
                System.out.println("acesta este raspunsul damn" + sessionBean.listSessionsForUsers.get(i).getSesiune().substring(28, 64));
                System.out.println(seesionNotificationHandler.getSessionId());
                if (sessionBean.listSessionsForUsers.get(i).getSesiune().substring(28, 64).equals(mySessionList.get(j)) && sessionBean.listSessionsForUsers.get(i).getSesiune().substring(28, 64).equals(seesionNotificationHandler.getSessionId())) {
                    System.out.println("am intrat ");
                    return sessionBean.listSessionsForUsers.get(i);

                }
            }
            i++;
        }

        return null;
    }

}
