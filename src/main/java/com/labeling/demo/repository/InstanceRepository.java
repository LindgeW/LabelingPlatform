package com.labeling.demo.repository;

import com.labeling.demo.entity.InstanceMongoDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstanceRepository extends MongoRepository<InstanceMongoDB, String> {
    List<InstanceMongoDB> findByTaskName(String taskName);
    Page<InstanceMongoDB> findByTaskName(String taskName, Pageable pageable);
    InstanceMongoDB findByInstanceId(Long instId);
}
