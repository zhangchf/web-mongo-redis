package com.zcf.webmongoredis;

import com.zcf.webmongoredis.model.Customer;
import com.zcf.webmongoredis.mongodb.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

//    @GetMapping("/customers")
//    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
//    }

    @GetMapping
    public List<Customer> customers(@RequestParam(required = false, name = "lastName") String lastName) {
        if (lastName != null) {
            return customerRepository.findByLastName(lastName);
        } else {
            return customerRepository.findAll();
        }
    }

    @PostMapping
    public Customer add(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @DeleteMapping
    public void delete(@RequestBody Customer customer) {
        customerRepository.delete(customer);
    }
}
