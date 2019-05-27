package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("id")
    private int id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("loginKey")
    private int loginKey;

    public User(){}

    public User(int id, String userName, int loginKey){
        this.id = id;
        this.userName = userName;
        this.loginKey = loginKey;
    }

    public User(String userName, int loginKey){
        this.userName = userName;
        this.loginKey = loginKey;
    }
}
