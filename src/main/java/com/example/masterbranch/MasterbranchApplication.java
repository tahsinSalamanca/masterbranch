package com.example.masterbranch;

import com.example.masterbranch.config.DynamoDBConfig;
import com.example.masterbranch.entity.Person;
import com.example.masterbranch.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@Import({DynamoDBConfig.class})
@RequestMapping("/api/v1")
public class MasterbranchApplication {

	@Autowired
	private PersonRepository personRepository;

	@PostMapping("/addPerson")
	public Person addPerson(@RequestBody Person person){
		return personRepository.addPerson(person);
	}

	@GetMapping("/getPerson/{personId}")
	public Person getPerson(@PathVariable String  personId){
		return personRepository.findPersonByPersonId(personId);
	}

	@GetMapping("/getAllPeople")
	public List<Person> getAllPeople(){
		return personRepository.findAllPerson();
	}

	@DeleteMapping("/deletePerson/{personId}")
	public String deletePerson(@PathVariable String  personId){
		return personRepository.deletePerson(personId);
	}

	@PutMapping("/updatePerson")
	public String editPerson(@RequestBody Person  person){
		return personRepository.editPerson(person);
	}

	@PatchMapping("/partialUpdate/{personId}")
	public String editPerson(@PathVariable String personId, @Validated  @RequestBody Person  person){
		return personRepository.patchUpdate(personId,person);
	}

	public static void main(String[] args) {
		SpringApplication.run(MasterbranchApplication.class, args);
	}

}
