package com.labeling.demo.repository;

import com.labeling.demo.entity.Instance;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstanceRepository extends MongoRepository<Instance, String> {
    List<Instance> findByTaskName(String taskName);
    Instance findByInstanceId(Long instId);
}
