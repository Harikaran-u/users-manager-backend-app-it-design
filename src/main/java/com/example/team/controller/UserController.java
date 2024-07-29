package com.example.team.controller;

import com.example.team.model.UpdateUserRequest;
import com.example.team.model.User;
import com.example.team.service.UserJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserJpaService userJpaService;

    @PostMapping("/create_user")
    public User addUser(@RequestBody User user){
        return userJpaService.addUser(user);
    }

    @PostMapping("/get_users")
    public List<User> getUsers(@RequestBody User user){
        return userJpaService.getUsers(user);
    }

    @PostMapping("/delete_user")
    public void deleteUser(@RequestBody User user){
        userJpaService.deleteUser(user);
    }

    @PostMapping("/update_user")
    public List<User> updateUsers(@RequestBody UpdateUserRequest userRequest){
        List<UUID> userIds = userRequest.getUser_ids();
        User user = userRequest.getUpdate_data();
        return userJpaService.updateUsers(userIds, user);
    }
}
