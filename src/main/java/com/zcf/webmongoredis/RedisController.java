package com.zcf.webmongoredis;

import com.zcf.webmongoredis.redis.Person;
import com.zcf.webmongoredis.redis.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/redis/persons")
public class RedisController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<Person> redisCustomers() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(personList::add);
        return personList;
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping
    public void deletePerson(@RequestBody Person person) {
        personRepository.deleteById(person.getId());
    }
}
