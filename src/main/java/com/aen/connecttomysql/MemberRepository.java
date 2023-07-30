package com.aen.connecttomysql;

import com.aen.connecttomysql.MembersEntity;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<MembersEntity, Long> {
}
