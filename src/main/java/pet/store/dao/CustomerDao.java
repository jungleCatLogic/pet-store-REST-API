package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.store.entity.Customer;

// This interface extends JpaRepository to provide CRUD operations for Customer entities
// The generic parameters specify the entity type (Customer) and the ID type (Long)
public interface CustomerDao extends JpaRepository<Customer, Long> {
    // Spring Data JPA automatically implements basic CRUD methods
}