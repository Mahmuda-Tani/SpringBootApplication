package com.example.springbootbackend.service;

import com.example.springbootbackend.dto.RequestDto;
import com.example.springbootbackend.dto.SearchRequestDto;
import com.example.springbootbackend.dto.UserLocationDto;
import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployee();
    Employee getEmployeeById(long id);
    Employee updateEmployee(Employee employee, Long id);

    void deleteEmployee(long id);

    List<Employee>ByFirstName(String first_name);
    List<Employee>ByLastName(String last_name);

    List<Employee>ByFirstNameWithId(String first_name,long id);

    List<Employee>ByLastNameWithId(String last_name, long id);

    List<Employee> findByFirstname_Email(String Firstname, String Email);

    List<Employee> checkOnStram();

    List<UserLocationDto> getAllUsersLocation();

    UserLocationDto convertEntityToDto(User user);
    User convertDtoToEntity(UserLocationDto userLocationDto);

//    Specification<Employee> getSearchSpecification(SearchRequestDto searchRequestDto);

    Specification<Employee> getSearchSpecification(List<SearchRequestDto> searchRequestDtos , RequestDto.GlobalOperator globalOperator);

}
