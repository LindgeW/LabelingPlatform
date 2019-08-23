package com.labeling.demo.service;

import com.labeling.demo.entity.User;

import java.util.List;

public interface UserService {

    User findUser(String username);

    List<User> findAll();

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean updateBatchUser(List<User> users);

    List<User> findUserWithoutTeam();
}
