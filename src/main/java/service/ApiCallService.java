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
        User user = new User(1, "Tommen", "wr45y7");

        //PasswordSet passwordSet = new PasswordSet(password, title, websiteeUrl, description, user);
        //String jsonString = mapper.writeValueAsString(passwordSet);
        restTemplate.postForObject(uri, new PasswordSet(password, title, websiteeUrl, description, user), PasswordSet.class);
    }

    public List<PasswordSet> getPasswordSets(int id) throws IOException {

        final String uri = "http://localhost:8080/passwordsets/{id}";

        String jsonString = restTemplate.getForObject(uri, String.class, id);
        ObjectMapper mapper = new ObjectMapper();

        List<PasswordSet> passwordSets = mapper.readValue(jsonString, new TypeReference<ArrayList<PasswordSet>>() {
        });

        return passwordSets;
    }

    public void deletePasswordSet(PasswordSet passwordSet){

        final String uri =  "http://localhost:8080/passwordsets/{id}";

        restTemplate.delete(uri, passwordSet.getId());
    }

    public void updatePasswordSet(PasswordSet currentPasswordSet){
        final String uri =  "http://localhost:8080/passwordsets/{id}";

        restTemplate.put(uri, currentPasswordSet, currentPasswordSet.getId());
    }

    public String getGeneratedHexKey(int bitSize){
        final String uri = "http://localhost:8080/generatepassword/{bitSize}";

        return restTemplate.getForObject(uri, String.class, bitSize);
    }

    public String getGeneratedPasswordByUserSpecification(boolean upperCase, boolean lowerCase, boolean special, boolean digits, int length){
        final String uri = "http://localhost:8080/generatepassword/userspecifiedchar/{length}?uppercase={uppercase}&lowercase={lowercase}&special={special}&digits={digits}";

        return restTemplate.getForObject(uri, String.class, length, upperCase, lowerCase, special, digits);
    }
}
