package service;

import models.Session;
import models.User;

public interface ILoginCallService {
    Boolean register(String username, String Password);
    Session login(String username, String password);
}
