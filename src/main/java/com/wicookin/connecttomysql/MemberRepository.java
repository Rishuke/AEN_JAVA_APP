package com.wicookin.connecttomysql;

import org.springframework.data.repository.CrudRepository;
import com.wicookin.connecttomysql.MembersEntity;

public interface MemberRepository extends CrudRepository<MembersEntity, Long> {
    long countByGender(String male);
}
