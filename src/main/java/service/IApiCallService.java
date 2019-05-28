package service;

import javafx.collections.ObservableList;
import models.PasswordSet;
import sun.security.util.Password;

import java.io.IOException;
import java.util.List;

public interface IApiCallService {
    void addPasswordSet(String password, String title, String websiteeUrl, String description);
    List<PasswordSet> getPasswordSets(int id) throws IOException;
    void deletePasswordSet(PasswordSet passwordSet);
    void updatePasswordSet(PasswordSet currentPasswordSet);
}
