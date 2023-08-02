package com.example.springbootbackend.repository;

import com.example.springbootbackend.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;



public interface EmployeeCriteriaRepository {

    List<Employee> findByFirstnameAndEmail(String Firstname, String Email);
}
