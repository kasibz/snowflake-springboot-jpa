package com.example.snowTest.controller;

import com.example.snowTest.dto.PersonRequest;
import com.example.snowTest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.snowTest.model.Person;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepo;

    @GetMapping("/person")
    public ResponseEntity<List<Person>> getAllPersons() {
        try {
            List<Person> personList = new ArrayList<>();
            personRepo.findAll().forEach(personList::add);

            if (personList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable String id) {
        Optional<Person> personData = personRepo.findById(id);

        if(personData.isPresent()) {
            return new ResponseEntity<>(personData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/person")
    public ResponseEntity<Person> addHibernatePerson(@RequestBody PersonRequest personRequest) {
        Person personObj = new Person();
        String newUUID = UUID.randomUUID().toString();
        personObj.setId(newUUID);
        personObj.setName(personRequest.getName());
        return new ResponseEntity<>(personRepo.save(personObj), HttpStatus.OK);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Person> putPerson(@PathVariable String id, @RequestBody PersonRequest personRequest) {
        Optional<Person> personData = personRepo.findById(id);

        if(personData.isPresent()) {
            Person existingPerson = personData.get();
            existingPerson.setName(personRequest.getName());
            existingPerson.setId(id);

            Person updatedPerson = personRepo.save(existingPerson);

            return new ResponseEntity<>(existingPerson, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Person> removePerson(@PathVariable String id) {
        Optional<Person> personData = personRepo.findById(id);

        if(personData.isPresent()) {
            Person deletedPerson = personData.get();
            personRepo.delete(deletedPerson);
            return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
