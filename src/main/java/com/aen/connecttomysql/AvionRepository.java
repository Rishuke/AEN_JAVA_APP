package com.aen.connecttomysql;

import com.aen.connecttomysql.AvionEntity;
import org.springframework.data.repository.CrudRepository;

public interface AvionRepository extends CrudRepository<AvionEntity, Long> {
}
