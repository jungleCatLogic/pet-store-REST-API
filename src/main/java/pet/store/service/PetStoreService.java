package pet.store.service;

// Service layer contains business logic
// also acts as intermediary btw controller and DAO

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;

	@Transactional
	public PetStoreData savePetStore(PetStoreData petStoreData) {

// Find existing pet store or create a new one
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);

// Copy fields from DTO to entity
		copyPetStoreFields(petStore, petStoreData);

// Save the entity to the db
		PetStore dbPetStore = petStoreDao.save(petStore);

// Return a new DTO with the updated data
		return new PetStoreData(dbPetStore);
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (petStoreId == null) {
// If no ID is provided, create a new pet store
			return new PetStore();
		} else {
// Else, if an ID is provided, find the existing pet store
			return findPetStoreById(petStoreId);
		}
	}

	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store not found with ID=" + petStoreId));
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setName(petStoreData.getName());
		petStore.setAddress(petStoreData.getAddress());
		petStore.setCity(petStoreData.getCity());
		petStore.setState(petStoreData.getState());
		petStore.setZip(petStoreData.getZip());
		petStore.setPhone(petStoreData.getPhone());

// Customers & employees fields are handled by separate methods for adding/updating
	}
}
