package com.example.javaSpringBoot_JDK11.api;

import com.example.javaSpringBoot_JDK11.model.Person;
import com.example.javaSpringBoot_JDK11.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {
    private  final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople(){
        return  personService.getAllPeople();
    }

    @GetMapping(path ="{id}") // /id
    public Person getPersonById(@PathVariable("id") UUID id){
        return  personService.getPersonbyId(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePersonbyId(@PathVariable("id")UUID id){
        personService.deletePerson(id);
    }

    @PutMapping(path = "{id}")
    public void updatePersonbyId(@PathVariable("id")UUID id ,@RequestBody Person person){
        personService.updatePerson(id,person);
    }

}
