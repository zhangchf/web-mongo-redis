package com.zcf.webmongoredis.appinit;

import com.zcf.webmongoredis.model.Customer;
import com.zcf.webmongoredis.mongodb.CustomerRepository;
import com.zcf.webmongoredis.redis.Address;
import com.zcf.webmongoredis.redis.Person;
import com.zcf.webmongoredis.redis.PersonRepository;
import com.zcf.webmongoredis.redis.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Order(1)
public class RedisInitialization implements CommandLineRunner {

    @Autowired
    RedisTemplate<String, Customer> redisTemplate;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PersonRepository personRepository;


    @Override
    public void run(String... args) throws Exception {
        HashOperations<String, String, Customer> hashOperations = redisTemplate.opsForHash();
        long count = hashOperations.size(RedisConfig.KEY_CUSTOMERS);
        System.out.println("Redis count: " + count);

        redisTemplate.delete(RedisConfig.KEY_CUSTOMERS);
//        hashOperations.delete(RedisConfig.KEY_CUSTOMERS, hashOperations.keys(RedisConfig.KEY_CUSTOMERS).toArray());

        count = hashOperations.size(RedisConfig.KEY_CUSTOMERS);
        System.out.println("Redis count after clear: " + count);

        List<Customer> customerList = customerRepository.findAll();
        for (Customer customer : customerList) {
            hashOperations.put(RedisConfig.KEY_CUSTOMERS, customer.id, customer);
        }

        Map<String, Customer> customerMap = hashOperations.entries(RedisConfig.KEY_CUSTOMERS);
        System.out.println("Redis customers: ");
        for (Map.Entry<String, Customer> entry : customerMap.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        // perform operations using RedisRepositories
        basicCrudRepositoryOperations();
    }

    public void basicCrudRepositoryOperations() {
        personRepository.deleteAll();
        Person rand = new Person("rand", "al'thor");
        rand.setAddress(new Address("emond's field", "andor"));

        personRepository.save(rand);
        personRepository.findById(rand.getId());
        personRepository.count();

        rand = new Person("Ted", "Smith");
        rand.setAddress(new Address("Shanghai", "China"));
        personRepository.save(rand);

    }
}
