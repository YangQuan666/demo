package org.example.controller;

import annotation.RpcConsumer;
import entity.User;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @RpcConsumer(interfaceName = UserService.class, version = "1.0.0")
    private UserService userService;

    @GetMapping("/{name}/{age}")
    public User getUser(@PathVariable String name, @PathVariable Integer age) {
        return userService.getUser(name, age);
    }

    @PostMapping
    public String hello(@RequestBody User user) {
        return userService.info(user);
    }
}
