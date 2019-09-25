package com.labeling.demo.service;

import com.labeling.demo.entity.InstanceUser;
import com.labeling.demo.entity.Pager;
import com.labeling.demo.entity.vo.InstanceUserVO;

import java.util.List;

public interface InstanceUserService {
    InstanceUser findInstanceUser(Long id);

    List<InstanceUser> findAll();

    List<InstanceUser> findByPage(String username, Integer taskId, Pager pageable);

    List<InstanceUser> findByUserName(String username);

    boolean addInstanceUser(InstanceUser instanceuser);

    boolean updateInstanceUser(InstanceUser instanceuser);

    boolean save(InstanceUser instanceUser);

//    boolean saveBtach (List<InstanceUser>list);

    //查找一条数据被所有用户的标注情况
    List<InstanceUser>findInstanceUserById(Long instanceid);

    Integer countByTask(String username, Integer taskId);

    List<InstanceUserVO> findFullRecords(Long instanceId);
}
