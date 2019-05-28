package service;

import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class LoginCallService implements ILoginCallService {

    private RestTemplate restTemplate = new RestTemplate();

    public Boolean loginAndAuthenticate(String userName, String password) {

        final String uri = "http://localhost:8080/login/";

        JSONArray json = new JSONArray();
        JSONObject obj = new JSONObject();

        obj.put("userName", userName);
        obj.put("password", password);

        json.put(obj);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<>(obj.toString(), headers);
        ResponseEntity<String> out = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        if (out.getStatusCode().equals(HttpStatus.OK)){
            return true;
        }
        return false;
    }
}
