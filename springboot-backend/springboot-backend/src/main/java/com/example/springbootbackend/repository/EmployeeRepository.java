package com.example.springbootbackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springbootbackend.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee,Long>,JpaSpecificationExecutor<Employee> {

    @Query(value = "SELECT * FROM employees WHERE first_name = :firstname", nativeQuery = true)
    List<Employee> findByFirstName(@Param("firstname") String firstname);

    @Query(value = "SELECT * from employees e where e.last_name=:lastname", nativeQuery = true)
    List<Employee> findByLastName(@Param("lastname") String lastname);

    @Query(value = "SELECT * FROM employees e WHERE e.first_name = ?1 AND e.id = ?2", nativeQuery = true)
    List<Employee> findByFirstNameWithId(String firstName, long id);

    @Query(value = "SELECT * from employees e where e.last_name= ?1 and e.id= ?2", nativeQuery = true)
    List<Employee> findByLastNameWithId(String lastname, long id);








}
