package service;

import entity.User;

public interface UserService {

    User getUser(String name, int age);

    String info(User user);
}
