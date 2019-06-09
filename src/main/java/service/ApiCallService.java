package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import models.PasswordSet;
import models.Session;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiCallService implements IApiCallService {
    private RestTemplate restTemplate = new RestTemplate();

    public void addPasswordSet(String password, String title, String websiteUrl, String description, Session session){

        final String uri = "http://localhost:8080/passwordsets/";

        User user = new User(session.getUser().getId(), session.getUser().getUsername(), session.getUser().getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + session.getToken());
        HttpEntity<?> entity = new HttpEntity<Object>(new PasswordSet(password, title, websiteUrl, description, user), headers);

        restTemplate.postForObject(uri, entity, PasswordSet.class);
    }

    public List<PasswordSet> getPasswordSets(Session session) throws IOException {

        final String uri = "http://localhost:8080/passwordsets/" + session.getUser().getId();

        ResponseEntity<String> jsonString = restTemplate.exchange(uri, HttpMethod.GET, getAuthHeader(session), String.class);

        ObjectMapper mapper = new ObjectMapper();

        List<PasswordSet> passwordSets = mapper.readValue(jsonString.getBody(), new TypeReference<ArrayList<PasswordSet>>() {
        });

        return passwordSets;
    }

    public void deletePasswordSet(PasswordSet passwordSet, Session session){

        final String uri =  "http://localhost:8080/passwordsets/" + passwordSet.getId();

        restTemplate.exchange(uri, HttpMethod.DELETE, getAuthHeader(session), String.class);
    }

    public void updatePasswordSet(PasswordSet currentPasswordSet, Session session){
        final String uri =  "http://localhost:8080/passwordsets/" + currentPasswordSet.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + session.getToken());
        HttpEntity<?> entity = new HttpEntity<Object>(currentPasswordSet, headers);

        restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
    }

    public ResponseEntity<String> getGeneratedHexKey(int bitSize, Session session){
        final String uri = "http://localhost:8080/generatepassword/" + bitSize;

        return restTemplate.exchange(uri, HttpMethod.GET, getAuthHeader(session), String.class);
    }

    public ResponseEntity<String> getGeneratedPasswordByUserSpecification(boolean upperCase, boolean lowerCase, boolean special, boolean digits, int length, Session session){
        final String uri = "http://localhost:8080/generatepassword/userspecifiedchar/" + length;

        //adding the query params to the URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("uppercase", upperCase)
                .queryParam("lowercase", lowerCase)
                .queryParam("special", special)
                .queryParam("digits", digits);

        return restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, getAuthHeader(session), String.class);
    }

    private HttpEntity<?> getAuthHeader(Session session){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + session.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return entity;
    }
}
