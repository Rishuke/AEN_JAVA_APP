package com.wicookin.connecttomysql;

import org.springframework.data.repository.CrudRepository;
import com.wicookin.connecttomysql.InvoicesEntity;
public interface InvoiceRepository extends CrudRepository<InvoicesEntity, Long> {
}
