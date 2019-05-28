package service;

import models.User;
import org.springframework.web.client.RestTemplate;

public class LoginCallService implements ILoginCallService {

    private RestTemplate restTemplate = new RestTemplate();

    public void loginAndAuthenticate(String userName, String password){

        final String uri = "http://localhost:8080/login/";

        restTemplate.postForObject(uri, new User(userName, password), User.class);
    }
}
