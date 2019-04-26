package com.licenta.app.UbberLicenta.webSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.app.UbberLicenta.model.Position;
import org.json.JSONArray;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public class UserNotificationConsumer  implements Runnable{

    private WebSocketSession webSocketSession;
    private ObjectMapper objectMapper;
    private volatile boolean running =true;
    private String userId;
    List<Position> positionList;

    private boolean isFirstCall;

    public UserNotificationConsumer(WebSocketSession webSocketSession, String userId, List<Position> positionList){
        this.webSocketSession = webSocketSession;
        this.userId = userId;
        this.objectMapper = new ObjectMapper();
        this.positionList = positionList;

        this.isFirstCall = true;
    }
    public UserNotificationConsumer(WebSocketSession webSocketSession, String userId){
        this.webSocketSession = webSocketSession;
        this.userId = userId;
        this.objectMapper = new ObjectMapper();
        this.isFirstCall = true;
    }

    @Override
    public void run() {
        while (running){
            //getUserFirstCall();
            try{
                //BlockingDeque<List<Position>> coordonateleDrumul = new LinkedBlockingDeque<>();
                JSONArray list = new JSONArray(positionList);
//                coordonateleDrumul.put(positionList);
                //  send message

                //sendMessage(list.toString());
                Thread.sleep(5000);

            }catch (InterruptedException e)
            {
                e.printStackTrace();
                System.out.println("s-a oprit webSocketul");
                running=false;
            }
        }
    }

    private void sendMessage(String response){
        try{
            System.out.println("acesta este respons-ul"+response);
            webSocketSession.sendMessage(new TextMessage(response));
        }
        catch (IOException e){
            System.out.println(String.format("Exception occurred while handling web socket message. Message %s", e.getMessage()));
            e.printStackTrace();
        }
    }

    private void getUserFirstCall(){
        if(isFirstCall){
            JSONArray list = new JSONArray(positionList);
            System.out.println("lista de la inceput"+list.toString());
            System.out.println("salutare de inceput");
            sendMessage(list.toString());
            isFirstCall = false;
        }
    }

    public void terminate(){
        running = false;
    }

}