package service;

import models.User;

public interface ILoginCallService {
    Boolean loginAndAuthenticate(String userName, String password);
}
