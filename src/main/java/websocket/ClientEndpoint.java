
package websocket;

import Controllers.MainScreenController;
import models.User;
import javax.websocket.*;


@javax.websocket.ClientEndpoint
public class ClientEndpoint {
    private User user;
    private MainScreenController mainScreenController;

    public ClientEndpoint(User user, MainScreenController mainScreenController){
        this.user = user;
        this.mainScreenController = mainScreenController;
    }

    @OnOpen
    public void onWebsocketConnect() {
        mainScreenController.receiveMessage("[Connected]: " + this.user.getUsername());
    }
    @OnMessage
    public void onWebsocketText(String message) {
        mainScreenController.receiveMessage(message);
    }
    @OnClose
    public void onWebsocketClose(CloseReason reason) { mainScreenController.receiveMessage("[Disconnected]: " + this.user.getUsername()); }
    @OnError
    public void onWebsocketError(Throwable cause) {mainScreenController.receiveMessage("[Error]: " + cause.getMessage());}
}
