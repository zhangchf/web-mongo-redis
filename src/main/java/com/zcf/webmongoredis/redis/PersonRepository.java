package com.zcf.webmongoredis.redis;

import com.zcf.webmongoredis.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {
}
