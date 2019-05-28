package service;

import models.User;

public interface ILoginCallService {
    void loginAndAuthenticate(String userName, String password);
}
