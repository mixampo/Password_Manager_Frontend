package websocket;

import Controllers.MainScreenController;
import models.Message;
import models.User;
import org.eclipse.jetty.util.component.LifeCycle;

import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;


public class ClientHandler implements IClientHandler {

    private Session session;
    private WebSocketContainer container;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void connect(User user, MainScreenController mainScreenController){
        URI uri = URI.create("ws://localhost:8095/chat/" + user.getUsername());
        try {
            container = ContainerProvider.getWebSocketContainer();
            try {
                session = container.connectToServer(new ClientEndpoint(user, mainScreenController), uri);
            } finally {
                if (container instanceof LifeCycle){
                    ((LifeCycle) container).stop();
                }
            }
        } catch (Throwable t){
            t.printStackTrace(System.err);
        }
    }
    public void disconnect(User user){
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, new Message(user, " disconnected from service").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        session.getAsyncRemote().sendText(message);
    }
}
