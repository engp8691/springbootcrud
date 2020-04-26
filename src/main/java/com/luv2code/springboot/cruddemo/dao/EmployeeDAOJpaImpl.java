package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

	private EntityManager entityManager;

	public EmployeeDAOJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> findAll() {
		System.out.println("JJJJJJJJJJJJ All");
		// Create a query
		Query theQuery = entityManager.createQuery("from Employee");

		// execute query and get the result list
		List<Employee> employees = theQuery.getResultList();

		// return the results

		return employees;
	}

	@Override
	public Employee findById(int theId) {
		System.out.println("JJJJJJJJJJJJ theId = " + theId);
		
		// get employee
		Employee theEmployee = entityManager.find(Employee.class, theId);

		// return employee
		return theEmployee;
	}

	@Override
	public void save(Employee theEmployee) {
		// Save or update the employee
		Employee dbEmployee = entityManager.merge(theEmployee);
		
		// Update with id from db... so we can get generated id for save/insert
		theEmployee.setId(dbEmployee.getId());
	}

	@Override
	public void deleteById(int theId) {
		// Method 1 is to use entityManager.remove(...);
//		Employee theEmployee = null;
//		try {
//			theEmployee = entityManager.find(Employee.class, theId);
//			System.out.println(theEmployee);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		if(theEmployee != null) {
//			entityManager.remove(theEmployee);	
//		}
//		
		// Another method is with creating query
		Query theQuery = entityManager.createQuery("delete from Employee where id=:employeeId");
		theQuery.setParameter("employeeId", theId); theQuery.executeUpdate();		
	}
}
