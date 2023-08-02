package com.example.springbootbackend.service.impl;
import com.example.springbootbackend.dto.RequestDto;
import com.example.springbootbackend.dto.SearchRequestDto;
import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.model.User;
import com.example.springbootbackend.repository.EmployeeCriteriaRepository;
import com.example.springbootbackend.repository.EmployeeRepository;
import com.example.springbootbackend.repository.UserRepository;
import com.example.springbootbackend.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.springbootbackend.dto.UserLocationDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;



@Service
public class EmployeeServiceImpl  implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeCriteriaRepository employeeCriteriaRepository;

    private UserRepository userRepository;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCriteriaRepository employeeCriteriaRepository ,
                               UserRepository userRepository) {
        super();
        this.employeeRepository = employeeRepository;
        this.employeeCriteriaRepository = employeeCriteriaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee","id",id));
    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Employee","id",id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());

        employeeRepository.save(existingEmployee);

        return existingEmployee;
    }

    @Override
    public void deleteEmployee(long id) {

        employeeRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Employee","id",id));

        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> ByFirstName(String first_name) {
        return employeeRepository.findByFirstName(first_name);
    }

    @Override
    public List<Employee> ByLastName(String last_name) {
        return employeeRepository.findByLastName(last_name);
    }

    @Override
    public List<Employee> ByFirstNameWithId(String first_name, long id) {
        return employeeRepository.findByFirstNameWithId(first_name,id);
    }

    @Override
    public List<Employee> ByLastNameWithId(String last_name, long id) {
        return employeeRepository.findByLastNameWithId(last_name,id);
    }

    @Override
    public List<Employee> findByFirstname_Email(String Firstname, String Email) {
        return employeeCriteriaRepository.findByFirstnameAndEmail(Firstname, Email);
    }

    @Override
    public List<Employee> checkOnStram() {


        List<Employee> modifiedData = employeeRepository.findAll().stream()
                .filter(element -> element.getFirstName().equalsIgnoreCase("tani"))
                .map(element -> {
                    element.setFirstName(element.getFirstName() + "Akter");
                    return element;
                })
                .collect(Collectors.toList());


        return  modifiedData;
    }

    @Override
    public List<UserLocationDto> getAllUsersLocation() {

        System.out.println(userRepository.findAll());

        System.out.println(userRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList()));



        return userRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public UserLocationDto convertEntityToDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        UserLocationDto userLocationDto = modelMapper.map(user, UserLocationDto.class);
        return userLocationDto;
    }


    @Override
    public User convertDtoToEntity(UserLocationDto userLocationDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        User user = new User();
        user = modelMapper.map(userLocationDto,User.class);
        return user;
    }

//    @Override
//    public Specification<Employee> getSearchSpecification(SearchRequestDto searchRequestDto) {
//        return  new Specification<Employee>() {
//            @Override
//            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get(searchRequestDto.getColumn()),searchRequestDto.getValue());
//            }
//        };
//    }

    @Override
    public Specification<Employee> getSearchSpecification(List<SearchRequestDto> searchRequestDtos, RequestDto.GlobalOperator globalOperator) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            for (SearchRequestDto searchRequestDto : searchRequestDtos){

                switch (searchRequestDto.getOperation()){
                    case EQUAL :
                        Predicate equal = criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                        predicates.add(equal);
                        break;
                    case LIKE:
                        Predicate like = criteriaBuilder.like(root.get(searchRequestDto.getColumn()), "%"+ searchRequestDto.getValue() +"%");
                        predicates.add(like);
                        break;

                    case IN:
                        String[] split = searchRequestDto.getValue().split(",");
                        Predicate in = root.get(searchRequestDto.getColumn()).in((Object[]) split);
                        predicates.add(in);
                        break;
                    case GREATER_THAN:
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                        predicates.add(greaterThan);
                        break;

                    case LESS_THAN:
                        Predicate lessThan = criteriaBuilder.lessThan(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                        predicates.add(lessThan);
                        break;
                    case BETWEEN:
                        String[] split1 = searchRequestDto.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(searchRequestDto.getColumn()),split1[0],split1[1]);
                        predicates.add(between);
                        break;
//                    case JOIN:
//
//                        Predicate join = criteriaBuilder.equal(root.join(searchRequestDto.getJoinTable()).get(searchRequestDto.getColumn()), searchRequestDto.getValue());
//                        predicates.add(join);
//                        break;

                    default:
                        throw new IllegalStateException("error!!");

                }
            }

            if (globalOperator.equals(RequestDto.GlobalOperator.AND)){
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            else{
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }


        };
    }


}
