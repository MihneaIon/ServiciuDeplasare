package com.licenta.app.UbberLicenta.webSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.app.UbberLicenta.beans.*;
import com.licenta.app.UbberLicenta.model.*;
import org.keycloak.common.util.ConcurrentMultivaluedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class NotificationHandler extends TextWebSocketHandler {

    public ConcurrentMultivaluedHashMap<String, WebSocketSession> sessions;
    public ConcurrentMultivaluedHashMap<String, UserNotificationConsumer> consumers;
    public ConcurrentMultivaluedHashMap<String, ExecutorService> executors;
    public String sessionId = "";

    UserNotificationConsumer consumer;

    @Autowired
    private SessionBean sessionBean;

    @Autowired
    private DriverBean driverBean;

    @Autowired
    private UserNameFidelity userNameFidelity;

    @Autowired
    private SendInRealTime mySendInRealTime;

    @Autowired
    private SemaforBean semaforBean;


    public NotificationHandler() {
        sessions = new ConcurrentMultivaluedHashMap<>();
        consumers = new ConcurrentMultivaluedHashMap<>();
        executors = new ConcurrentMultivaluedHashMap<>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String userId = session.getId();
        sessionId = userId;
        System.out.println("Web socket conected for user: " + session);
        sessions.add(userId, session);
        consumer = new UserNotificationConsumer(session, userId);
        consumers.add(userId, consumer);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        System.out.println("size-ul consummer-ului" + consumers.size());
        executorService.submit(consumer);
        executors.add(userId, executorService);
        System.out.println(executors.toString());
        System.out.println(" sesiunea curenta" + this.sessionId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = session.getId();
        System.out.println(String.format("Web socket disconnected. Session for userId: %s", userId));
        int sessionIndex = sessions.get(userId).indexOf(session);
        ExecutorService executorService = executors.get(userId).get(sessionIndex);
        if (!executorService.isTerminated()) {
            consumer = consumers.get(userId).get(sessionIndex);
            consumer.terminate();
        }
        sessions.get(userId).remove(session);
        consumers.get(userId).remove(sessionIndex);
        executors.get(userId).remove(sessionIndex);
        sessionId = "";
    }


    @Override // intreab ce se intamla cu concurenta
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(String.format("Message received from the client" + message));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(message.getPayload());
            JsonNode jsonResponse = jsonNode.get("data");
            JsonNode jsonPosition = jsonResponse.get("position");
            JsonNode jsonType = jsonResponse.get("type");
            if ((jsonType + "").equals("\"authenticated\"")) {
                JsonNode nameOfClient = jsonPosition.get("nameOfClient");
                String afisarenume = nameOfClient + "";
                System.out.println(afisarenume + "aici e numle clientului\n");
                Fidelity newFidelity = new Fidelity(0, afisarenume);
                userNameFidelity.setActualFidelity(newFidelity);
            } else {
                JsonNode lat = jsonPosition.get("lat");
                JsonNode lng = jsonPosition.get("lng");
                System.out.println("lat "+lat+ "lng "+ lng);
                Position sayWhereTheDriverIs = new Position(transformJosnNodeToFloat(lat), transformJosnNodeToFloat(lng));
                ModelDeTransmitereAMesajului modelDeTransmitereAMesajului = new ModelDeTransmitereAMesajului(sayWhereTheDriverIs, jsonType + "");
                if ((jsonType + "").equals("\"driver\"")) {
                    System.out.println(sayWhereTheDriverIs + " sa vedem daca se schimba pozitia ");
                    driverBean.myList.add(sayWhereTheDriverIs);   /// Modific urgent
                    mySendInRealTime.addInList(sayWhereTheDriverIs);
                } else if ((jsonType + "").equals("\"client\"")) {
                    System.out.println("aici este partea de date care vin pe sesiuni pentru client");
                    SessionForAUser myTransmitere = new SessionForAUser(modelDeTransmitereAMesajului, session + "");
                    sessionBean.listSendSessions(myTransmitere);
//                    semaforBean.changeMySemafor();
//                    semaforBean.setSchimba(1);
                    System.out.println("transmiterea de sesiune" + myTransmitere);
                    System.out.println("se schimba oare ?" + semaforBean.getSchimba());

                }
                System.out.println("response" + sayWhereTheDriverIs);
            }
        } catch (IOException exception) {
            System.out.println(String.format("Exception occurred while handling web socket message. Message: %s", exception.getMessage()));
        }
    }

    public float transformJosnNodeToFloat(JsonNode node) {
        String stringNode = node + "";
        return Float.parseFloat(stringNode);
    }

}