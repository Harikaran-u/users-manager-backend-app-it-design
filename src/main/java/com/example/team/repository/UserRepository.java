package com.example.team.repository;

import com.example.team.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User addUser(User user);
    List<User> getUsers(User user);
    void deleteUser(User user);
    List<User> updateUsers(List<UUID> userIds, User user);
}
