package com.example.masterbranch.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.masterbranch.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PersonRepository {

    @Autowired
    private DynamoDBMapper mapper;

    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

    public Person addPerson(Person person) {
        mapper.save(person);
        return person;
    }

    public Person findPersonByPersonId(String personId) {
        return mapper.load(Person.class, personId);
    }

    public List<Person> findAllPerson() {
        return mapper.scan(Person.class, scanExpression);
    }

    public String deletePerson(Person person) {
        mapper.delete(person);
        return "Person Deleted!";
    }

    public String editPerson(Person person) {
        mapper.save(person, buildExpression(person));
        return "record updated successfully.";
    }

    public String patchUpdate(String personId, Person person) {
        Person oldPerson = mapper.load(Person.class, personId);

        if (person.getName() != null) {
            oldPerson.setName(person.getName());
        }
        if (person.getAge() != null) {
            oldPerson.setAge(person.getAge());
        }
        if (person.getEmail() != null) {
            oldPerson.setEmail(person.getEmail());
        }
        if (person.getAddress() != null) {
            oldPerson.setAddress(person.getAddress());
        }
        mapper.save(oldPerson);
        return "Successfully updated.";
    }

    private DynamoDBSaveExpression buildExpression(Person person) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap = new HashMap<>();
        expectedMap.put("personId", new ExpectedAttributeValue(new AttributeValue().withS(person.getPersonId())));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }
}
