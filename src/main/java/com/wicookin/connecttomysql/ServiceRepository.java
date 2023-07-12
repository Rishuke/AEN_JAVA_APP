package com.wicookin.connecttomysql;

import org.springframework.data.repository.CrudRepository;
import com.wicookin.connecttomysql.SpacesEntity;

public interface ServiceRepository extends CrudRepository<ServicesEntity, Long> {

}