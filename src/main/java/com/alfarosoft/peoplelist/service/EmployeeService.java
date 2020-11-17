package com.alfarosoft.peoplelist.service;

import com.alfarosoft.peoplelist.database.HibernateSessionFactory;
import com.alfarosoft.peoplelist.exception.PeopleListException;
import com.alfarosoft.peoplelist.model.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

public class EmployeeService {
    private HibernateSessionFactory hibernateSessionFactory;
    private Session employeeSession;
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(HibernateSessionFactory hibernateSessionFactory) throws Exception {
        this.hibernateSessionFactory = hibernateSessionFactory;
        employeeSession = hibernateSessionFactory.buildSession();
    }

    public Employee getEmployee(String id){
        return retrieveEmployeeFromDatabase(id);
    }

    public List<Employee> getEmployees() {
        employeeSession.beginTransaction();
        List<Employee> employeeList = employeeSession.createQuery("from Employee", Employee.class).list();
        employeeSession.getTransaction().commit();
        LOG.info("Employees returned");
        return employeeList;
    }

    public Employee addEmployee (Employee employee){
        employeeSession.beginTransaction();
        employeeSession.save(employee);
        employeeSession.getTransaction().commit();
        LOG.info("Employee created");
        return employee;
    }

    public Employee updateEmployee (String id, Employee employee){
        Employee employeeRetrieved = retrieveEmployeeFromDatabase(id);
        employeeRetrieved.setName(employee.getName());
        employeeRetrieved.setSurname(employee.getSurname());
        employeeRetrieved.setAddress(employee.getAddress());
        employeeRetrieved.setEmail(employee.getEmail());
        employeeRetrieved.setPhone(employee.getPhone());
        employeeRetrieved.setDateHired(employee.getDateHired());
        employeeRetrieved.setActiveEmployee(employee.isActiveEmployee());
        employeeSession.update(employeeRetrieved);
        employeeSession.getTransaction().commit();
        LOG.info("Employee updated");
        return employeeRetrieved;
    }

    public void removeEmployee (String id){
        employeeSession.delete(retrieveEmployeeFromDatabase(id));
        employeeSession.getTransaction().commit();
        LOG.info("Employee removed");
    }

    private Employee retrieveEmployeeFromDatabase (String id){
        Employee employeeRetrieved;
        try{
            employeeSession.beginTransaction();
            Query selectQuery = employeeSession.createQuery("from Employee WHERE Id=:paramId");
            selectQuery.setParameter("paramId", id);
            employeeRetrieved = (Employee) selectQuery.uniqueResult();
            LOG.info("Employee retrieved", keyValue("employeeRetrieved" , employeeRetrieved));
            return employeeRetrieved;
        } catch (EntityNotFoundException e){
            LOG.error("Employee not found", keyValue("employeeIdInError", id));
            throw new PeopleListException("Employee with id " + id + " was not found", 404);
        }
    }
}
