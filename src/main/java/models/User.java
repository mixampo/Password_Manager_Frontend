package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("id")
    private int id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    public User(){}

    public User(int id, String userName, String password){
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
