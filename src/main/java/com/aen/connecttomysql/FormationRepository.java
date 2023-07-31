package com.aen.connecttomysql;

import com.aen.connecttomysql.FormationEntity;
import org.springframework.data.repository.CrudRepository;

public interface FormationRepository extends CrudRepository<FormationEntity, Long> {
}
