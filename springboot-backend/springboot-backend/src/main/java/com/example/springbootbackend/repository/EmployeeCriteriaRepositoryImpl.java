package com.example.springbootbackend.repository;

import com.example.springbootbackend.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class EmployeeCriteriaRepositoryImpl implements EmployeeCriteriaRepository{



    @Autowired
    private EntityManager em;

    @Override
    public List findByFirstnameAndEmail(String Firstname, String Email) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);

        Root<Employee> employee=cq.from(Employee.class);

        Predicate firstNamePredicate=cb.equal(employee.get("firstName"),Firstname);
        Predicate emailPredicate=cb.equal(employee.get("email"),Email);

        cq.where(firstNamePredicate,emailPredicate);

        TypedQuery<Employee> query=em.createQuery(cq);

        return query.getResultList();

    }


}
