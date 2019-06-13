package models;

public class Message {
    private String message;
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message(User user, String message){
        this.message = message;
        this.user = user;
    }

    @Override
    public String toString(){
        return user.getUsername() + " : " + message;
    }
}
