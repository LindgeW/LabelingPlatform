package com.labeling.demo.repository;

import com.labeling.demo.entity.Instance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstanceRepository extends MongoRepository<Instance, Integer> {
}
