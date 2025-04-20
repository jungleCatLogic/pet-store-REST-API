package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.Employee;

// This interface extends JpaRepository to provide CRUD operations for Employee entities
// The generic parameters specify the entity type (Employee) and the ID type (Long)
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    // Spring Data JPA automatically implements basic CRUD methods
  
}