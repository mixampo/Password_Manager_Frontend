package service;

import models.Session;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;

public class LoginCallService implements ILoginCallService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Session login(String username, String password) {

        final String uri = "http://localhost:8080/auth";

        JSONArray json = new JSONArray();
        JSONObject obj = new JSONObject();

        obj.put("username", username);
        obj.put("password", password);

        json.put(obj);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<>(obj.toString(), headers);
        ResponseEntity<String> out = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        System.out.println(out.getBody());

        if (out.getStatusCode().equals(HttpStatus.OK)){
            String token = out.getBody();
            String[] parts = token.split("-Uid-");
            return new Session(new User(Integer.parseInt(parts[1]), username, password), parts[0]);
        }
        return null;
    }
}
