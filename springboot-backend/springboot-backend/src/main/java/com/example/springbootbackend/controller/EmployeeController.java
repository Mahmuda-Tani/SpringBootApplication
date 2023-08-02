package com.example.springbootbackend.controller;

import com.example.springbootbackend.dto.RequestDto;
import com.example.springbootbackend.dto.UserLocationDto;
import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeCriteriaRepository;
import com.example.springbootbackend.repository.EmployeeRepository;
import com.example.springbootbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private  EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;



//    create api
    @PostMapping()
    public ResponseEntity<Employee> saveEmployee( @RequestBody Employee employee){
        return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

//    get all employee api


    @GetMapping
    public List<Employee>getAllEmployees(){
        return employeeService.getAllEmployee();
    }

//    get employee by id api
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id")long employeeId){

        return new ResponseEntity<Employee>(employeeService.getEmployeeById(employeeId),HttpStatus.OK);
    }



//    update api



    @PutMapping("/id/{id}")
    public ResponseEntity<Employee>updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee){

        return new ResponseEntity<Employee>(employeeService.updateEmployee(employee,id),HttpStatus.OK);
    }




//   delete api


    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<String>("Employee deleted successfully!!",HttpStatus.OK);
    }


    @GetMapping("/firstname/{firstname}")
    public List<Employee> getByFirstName(@PathVariable("firstname") String firstname){
        return employeeService.ByFirstName(firstname);

    }

    @GetMapping("/lastname/{lastname}")
    public List<Employee> getByLastName(@PathVariable("lastname") String lastname){
        return employeeService.ByLastName(lastname);

    }


    @GetMapping("/firstnameD/{firstname}/{id}")
    public List<Employee> getFirstnameWithId(@PathVariable("firstname") String firstname,
                                                   @PathVariable("id") long id){
        return employeeService.ByFirstNameWithId(firstname,id);

    }

    @GetMapping("/lastnameD/{lastname}/{id}")
    public List<Employee> getLastnameWithDId(@PathVariable("lastname") String lastname,
                                                     @PathVariable("id") long id){
        return employeeService.ByLastNameWithId(lastname,id);

    }
    @GetMapping("/criteria/{firstname}/{email}")

    public List<Employee> getByCriteria(@PathVariable("firstname") String firstname,
                                        @PathVariable("email")String email){

      return employeeService.findByFirstname_Email(firstname, email);
    }


    @GetMapping("/stream")
    public List<Employee> checkStream(){
        return employeeService.checkOnStram();
    }

    @GetMapping("/dto")
    public List<UserLocationDto> getAllUsersLocation(){
        return employeeService.getAllUsersLocation();
    }


//    @PostMapping("/specification")
//    public List<Employee> getEmployees (@RequestBody RequestDto requestDto){
//
//       Specification<Employee> specification =  employeeService.getSearchSpecification(requestDto.getSearchRequestDto());
//
//        return  employeeRepository.findAll(specification);
//    }

    @PostMapping("/specificationList")
    public List<Employee> getEmployees2 (@RequestBody RequestDto requestDto){

        Specification<Employee> specification =  employeeService.getSearchSpecification(requestDto.getSearchRequestDto(),requestDto.getGlobalOperator());

        return  employeeRepository.findAll(specification);
    }




}
