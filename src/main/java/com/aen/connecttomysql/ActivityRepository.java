package com.aen.connecttomysql;

import com.aen.connecttomysql.ActivitiesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository  extends CrudRepository<ActivitiesEntity, Long> {
    @Query("SELECT count(a) FROM ActivitiesEntity a WHERE a.nom_activite = :name")
    long countByNom_activite(@Param("name") String name);
}
