package pet.store.dao;

// DAO interface handles db operations using Spring Data JPA

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet.store.entity.PetStore;

@Repository
public interface PetStoreDao extends JpaRepository<PetStore, Long> {
// Spring Data JPA provides basic CRUD operations by default
	   
	}

