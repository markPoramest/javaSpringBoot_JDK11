package com.example.javaSpringBoot_JDK11.dao;

import com.example.javaSpringBoot_JDK11.model.Person;
import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(Person person) {
        final String sql = "INSERT INTO person (id ,name) VALUES (?,?)";
        int row = jdbcTemplate.update(sql,UUID.randomUUID(),person.getName());
        return row;
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT id,name FROM person";
        return jdbcTemplate.query(sql,(resultSet, i)->{
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id,name);
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT id,name FROM person WHERE id = ?";
        Person person = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i)->{
            UUID personID = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(personID,name);
        });
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        final String sql = "Delete FROM person WHERE id = ?";
         int row = jdbcTemplate.update(sql,id);
        return row;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final String sql = "UPDATE person SET name = ? WHERE id = ?";
        int row = jdbcTemplate.update(sql,person.getName(),id);
        return row;
    }
}
