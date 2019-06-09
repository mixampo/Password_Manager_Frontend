package service;

import models.Session;
import models.User;

public interface ILoginCallService {
    Session login(String username, String password);
}
