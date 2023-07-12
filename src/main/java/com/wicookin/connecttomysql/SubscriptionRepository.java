package com.wicookin.connecttomysql;

import org.springframework.data.repository.CrudRepository;
import com.wicookin.connecttomysql.SubscriptionEntity;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
}
