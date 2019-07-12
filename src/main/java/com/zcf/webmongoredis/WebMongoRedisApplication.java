package com.zcf.webmongoredis;

import com.zcf.webmongoredis.mongodb.Customer;
import com.zcf.webmongoredis.mongodb.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebMongoRedisApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;


	public static void main(String[] args) {
		SpringApplication.run(WebMongoRedisApplication.class, args);
	}

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
