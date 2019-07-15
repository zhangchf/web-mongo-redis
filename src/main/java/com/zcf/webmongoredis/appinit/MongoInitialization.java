package com.zcf.webmongoredis.appinit;

import com.zcf.webmongoredis.WebMongoRedisApplication;
import com.zcf.webmongoredis.model.Customer;
import com.zcf.webmongoredis.mongodb.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class MongoInitialization implements CommandLineRunner {

    @Autowired
    private CustomerRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        repository.save(new Customer("Alice", "Smith"));
        repository.save(new Customer("Bob", "Smith"));
        repository.save(new Customer("Ted", "John"));

        System.out.println("Found All Customers: ");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println("Found Alice:");
        System.out.println(repository.findByFirstName("Alice"));
        System.out.println("Found All Smith: ");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }
}
