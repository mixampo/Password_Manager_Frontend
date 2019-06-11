package websocket;

import Controllers.MainScreenController;
import models.User;

import javax.websocket.Session;

public interface IClientHandler {
    void connect(User user, MainScreenController mainScreenController);
    void disconnect(User user);
    Session getSession();
    void setSession(Session session);
    void sendMessage(String message);
}
