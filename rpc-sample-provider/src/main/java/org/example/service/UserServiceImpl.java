package org.example.service;

import annotation.RpcProvider;
import entity.User;
import org.springframework.stereotype.Service;
import service.UserService;

@Service
@RpcProvider(interfaceName = UserService.class, version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(String name, int age) {
        return new User(name, age);
    }

    @Override
    public String info(User user) {
        return "this is: " + user;
    }
}
