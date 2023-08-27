package com.aen.connecttomysql;

import com.aen.connecttomysql.PlanificationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface PlanificationRepository extends CrudRepository<PlanificationEntity, Long> {
    PlanificationEntity findByDate(Date date);
}
