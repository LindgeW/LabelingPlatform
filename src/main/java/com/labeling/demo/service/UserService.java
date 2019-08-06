package com.labeling.demo.service;

import com.labeling.demo.entity.User;

public interface UserService {

    User findUser(String username);

    boolean addUser(User user);

    boolean updateUser(User user);
}
