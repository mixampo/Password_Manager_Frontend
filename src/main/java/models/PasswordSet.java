package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordSet {

    @JsonProperty("id")
    private int id;

    @JsonProperty("password")
    private String password;

    @JsonProperty("title")
    private String title;

    @JsonProperty("websiteUrl")
    private String websiteUrl;

    @JsonProperty("description")
    private String description;

    @JsonProperty("user")
    private User user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public PasswordSet(){}

    public PasswordSet(int id, String password, String title, String websiteUrl, String description, User user){
        this.id = id;
        this.password = password;
        this.title = title;
        this.websiteUrl = websiteUrl;
        this.description = description;
        this.user = user;
    }

    public PasswordSet(String password, String title, String websiteUrl, String description, User user){
        this.password = password;
        this.title = title;
        this.websiteUrl = websiteUrl;
        this.description = description;
        this.user = user;
    }

}
