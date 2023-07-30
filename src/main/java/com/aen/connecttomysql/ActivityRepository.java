package com.aen.connecttomysql;

import com.aen.connecttomysql.ActivitiesEntity;
import org.springframework.data.repository.CrudRepository;
public interface ActivityRepository  extends CrudRepository<ActivitiesEntity, Long> {
}
