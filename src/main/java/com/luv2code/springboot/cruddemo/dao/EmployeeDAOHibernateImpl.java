package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {
	// define field for entitymanager

	private EntityManager entityManager;

	// setup constructor injection
	// The entityManager is automatically created by Spring Boot
	@Autowired
	public EmployeeDAOHibernateImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public List<Employee> findAll() {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// create a query
		Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);

		// excecute the query and get result list
		List<Employee> employees = theQuery.getResultList();

		// return the result list
		return employees;
	}

	@Override
	public Employee findById(int theId) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// create a query
		Employee theEmployee = currentSession.get(Employee.class, theId);

		// return the result list
		return theEmployee;
	}

	@Override
	public void save(Employee theEmployee) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// save employee
		// Remember: id=0? save : !exists? insert : update		
		currentSession.saveOrUpdate(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// save employee
		Query theQuery = currentSession.createQuery("DELETE FROM Employee WHERE id=:employeeId");
		theQuery.setParameter("employeeId",  theId);
		
		theQuery.executeUpdate();
	}
}
