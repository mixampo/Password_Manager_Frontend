package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;
import models.PasswordSet;
import models.User;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

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

    public void addPasswordSet(String password, String title, String websiteeUrl, String description){

        final String uri = "http://localhost:8080/passwordsets/";
        //ObjectMapper mapper = new ObjectMapper();

        //TODO inlogen implementeren
        User user = new User(1, "Tommen", 39535);

        //PasswordSet passwordSet = new PasswordSet(password, title, websiteeUrl, description, user);
        //String jsonString = mapper.writeValueAsString(passwordSet);
        restTemplate.postForObject(uri, new PasswordSet(password, title, websiteeUrl, description, user), PasswordSet.class);
    }

    public List<PasswordSet> getPasswordSets() throws IOException {

        final String uri = "http://localhost:8080/passwordsets/";
        String jsonString = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();

        List<PasswordSet> passwordSets = mapper.readValue(jsonString, new TypeReference<ArrayList<PasswordSet>>() {
        });

        return passwordSets;
    }

    public void deletePasswordSet(PasswordSet passwordSet){

        final String uri =  "http://localhost:8080/passwordsets/{id}";
        Map<String, Integer> params = new HashMap<>();

        params.put("id", passwordSet.getId());

        restTemplate.delete(uri, params);
    }

    public void updatePasswordSet(PasswordSet currentPasswordSet){
        final String uri =  "http://localhost:8080/passwordsets/{id}";
        Map<String, Integer> params = new HashMap<>();

        params.put("id", currentPasswordSet.getId());

        restTemplate.put(uri, currentPasswordSet, params);
    }
}
