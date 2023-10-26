package com.example.masterbranch;

import com.example.masterbranch.config.DynamoDBConfig;
import com.example.masterbranch.entity.Person;
import com.example.masterbranch.repository.PersonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@SpringBootApplication
@RestController
@Import({DynamoDBConfig.class})
@Api(tags = "Person Api")
@RequestMapping("/api/v1")
public class MasterbranchApplication {

	@Autowired
	private PersonRepository personRepository;

	@PostMapping("/addPerson")
	@ApiOperation(value = "Add a new person")
	public Person addPerson(@RequestBody Person person){
		return personRepository.addPerson(person);
	}

	@GetMapping("/getPerson/{personId}")
	@ApiOperation(value = "Call one person by id")
	public Person getPerson(@PathVariable String  personId){
		return personRepository.findPersonByPersonId(personId);
	}

	@GetMapping("/getAllPeople")
	@ApiOperation(value = "Call All People")
	public List<Person> getAllPeople(){
		return personRepository.findAllPerson();
	}

	@DeleteMapping("/deletePerson/{personId}")
	@ApiOperation(value = "Delete one person by id")
	public String deletePerson(@PathVariable String  personId){
		return personRepository.deletePerson(personId);
	}

	@PutMapping("/updatePerson")
	@ApiOperation(value = "Update person")
	public String editPerson(@RequestBody Person  person){
		return personRepository.editPerson(person);
	}

	@PatchMapping("/partialUpdate/{personId}")
	@ApiOperation(value = "Update person partially")
	public String editPerson(@PathVariable String personId, @Validated  @RequestBody Person  person){
		return personRepository.patchUpdate(personId,person);
	}

	public static void main(String[] args) {
		SpringApplication.run(MasterbranchApplication.class, args);
	}

}
