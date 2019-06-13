package service;

import models.Session;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.sql.SQLOutput;

public class LoginCallService implements ILoginCallService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Boolean register(String username, String password){
        final String uri = "http://localhost:8080/register";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<Object>(new User(username, password), headers);

        try {
            restTemplate.postForObject(uri, entity, User.class);
            return true;
        }
        catch (HttpClientErrorException ex){
            return false;
        }
    }

    @Override
    public Session login(String username, String password) {

        final String uri = "http://localhost:8080/auth";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<Object>(new User(username, password), headers);
        try {
            ResponseEntity<String> out = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
            String token = out.getBody();
            String[] parts = token.split("-Uid-");
            return new Session(new User(Integer.parseInt(parts[1]), username, password), parts[0]);
        }
        catch (HttpClientErrorException ex){
            return null;
        }
        //System.out.println(out.getBody());

       // if (out.getStatusCode().equals(HttpStatus.OK)){
          //  String token = out.getBody();
           // String[] parts = token.split("-Uid-");
           // return new Session(new User(Integer.parseInt(parts[1]), username, password), parts[0]);
       // }
        //return null;
    }
}
