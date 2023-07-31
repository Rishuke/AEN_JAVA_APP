package com.aen.connecttomysql;

import com.aen.connecttomysql.PlanificationEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlanificationRepository extends CrudRepository<PlanificationEntity, Long> {
}
