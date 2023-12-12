package com.example.snowTest.repository;

import com.example.snowTest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    @Query( value = "SELECT * FROM PERSON", nativeQuery = true)
    List<Person> findAllPersons();

    @Query( value = "SELECT * FROM PERSON WHERE id = :id", nativeQuery = true)
    Optional<Person> findPersonById(@Param("id") String id);

    // UPDATE returns a String but I don't need it I just return the updated object in the controller
    @Query( value = "UPDATE PERSON SET name = :name WHERE id = :id", nativeQuery = true )
    String updatePerson(@Param("id") String id, @Param("name") String name);

    @Query( value = "DELETE FROM PERSON WHERE id = :id", nativeQuery = true)
    String deletePerson(@Param("id") String id);
    // lets try to post
    @Query( value = "INSERT INTO PERSON (id, name)" + "VALUES (:id, :name)", nativeQuery = true)
    void createPerson(@Param("id") String id, @Param("name") String name);
}
