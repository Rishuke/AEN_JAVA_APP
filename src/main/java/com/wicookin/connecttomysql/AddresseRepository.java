package com.wicookin.connecttomysql;

import org.springframework.data.repository.CrudRepository;
import com.wicookin.connecttomysql.AddressesEntity;
public interface AddresseRepository extends CrudRepository<AddressesEntity, Long> {
}
