package com.wicookin.connecttomysql;

import org.springframework.data.repository.CrudRepository;
import com.wicookin.connecttomysql.EventsEntity;
public interface EventRepository extends CrudRepository<EventsEntity, Long> {
}
