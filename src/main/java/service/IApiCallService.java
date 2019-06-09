package service;

import javafx.collections.ObservableList;
import models.PasswordSet;
import models.Session;
import org.springframework.http.ResponseEntity;
import sun.security.util.Password;

import java.io.IOException;
import java.util.List;

public interface IApiCallService {
    void addPasswordSet(String password, String title, String websiteUrl, String description, Session session);
    List<PasswordSet> getPasswordSets(Session session) throws IOException;
    void deletePasswordSet(PasswordSet passwordSet, Session session);
    void updatePasswordSet(PasswordSet currentPasswordSet, Session session);
    ResponseEntity<String> getGeneratedHexKey(int bitSize, Session session);
    ResponseEntity<String> getGeneratedPasswordByUserSpecification(boolean upperCase, boolean lowerCase, boolean special, boolean digits, int length, Session session);
}
