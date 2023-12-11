package com.example.snowTest.controller;

import com.example.snowTest.dto.PersonRequest;
import com.example.snowTest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.snowTest.model.Person;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepo;

    @GetMapping("/person")
    public ResponseEntity<List<Person>> getAllPersons() {
        try {
            List<Person> personList = personRepo.findAllPersons();

            if (personList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> personData = personRepo.findPersonById(id);

        if(personData.isPresent()) {
            return new ResponseEntity<>(personData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//     custom working post
    @PostMapping("/person")
    public ResponseEntity<Person> addPerson(@RequestBody PersonRequest personRequest) {
        personRepo.createPerson(personRequest.getName());
        Person newPerson = new Person(personRequest.getName());
        return new ResponseEntity<>(newPerson, HttpStatus.OK);
    }

    //post with hibernate - troubleshooting -NOT WORKING
    @PutMapping("/person/{id}")
    public ResponseEntity<Person> putPerson(@PathVariable Long id, @RequestBody PersonRequest personRequest) {
        Optional<Person> personData = personRepo.findPersonById(id);

        if(personData.isPresent()) {
            Person updatedPerson = personData.get();
            updatedPerson.setName(personRequest.getName());
            personRepo.updatePerson(updatedPerson.getId(), updatedPerson.getName());
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Person> removePerson(@PathVariable Long id) {
        Optional<Person> personData = personRepo.findPersonById(id);

        if(personData.isPresent()) {
            Person deletedPerson = personData.get();
            personRepo.deletePerson(id);
            return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/hibernate/person")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person personObj = personRepo.save(person);

        return new ResponseEntity<>(personObj, HttpStatus.OK);
    }

}
