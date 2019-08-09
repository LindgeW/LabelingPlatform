package com.labeling.demo.service;

import com.labeling.demo.entity.InstanceUser;

import java.util.List;

public interface InstanceUserService {
    InstanceUser findInstanceUser(int id);

    List<InstanceUser> findAll();

    boolean addInstanceUser(InstanceUser instanceuser);

    boolean updateInstanceUser(InstanceUser instanceuser);



    long countByUsername(String username);


}
