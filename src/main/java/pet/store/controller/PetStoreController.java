package pet.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controller layer handles http requests and responses


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
    
    @Autowired
    private PetStoreService petStoreService;
    
// POST endpoint to create a new pet store
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
        log.info("Creating pet store {}", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }
    
// PUT endpoint to update an existing pet store
    @PutMapping("/{petStoreId}")
    public PetStoreData updatePetStore(
            @PathVariable Long petStoreId, 
            @RequestBody PetStoreData petStoreData) {
        
// Set the pet store ID in the data from the URL path
        petStoreData.setPetStoreId(petStoreId);
        
        log.info("Updating pet store {}", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }
    
// This method handles HTTP POST requests to add an employee to a pet store
    @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreData.PetStoreEmployee addEmployee(
            @PathVariable Long petStoreId,
            @RequestBody PetStoreData.PetStoreEmployee petStoreEmployee) {
        
        log.info("Adding employee to pet store with ID={}: {}", petStoreId, petStoreEmployee);
        return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
    }    
    
 // This method handles HTTP POST requests to add a customer to a pet store
    @PostMapping("/{petStoreId}/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreData.PetStoreCustomer addCustomer(
            @PathVariable Long petStoreId,
            @RequestBody PetStoreData.PetStoreCustomer petStoreCustomer) {
        
        log.info("Adding customer to pet store with ID={}: {}", petStoreId, petStoreCustomer);
        return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
    }
    
 // This method handles HTTP GET requests to retrieve all pet stores
    @GetMapping
    public List<PetStoreData> retrieveAllPetStores() {
        log.info("Retrieving all pet stores");
        return petStoreService.retrieveAllPetStores();
    }    

 // This method handles HTTP GET requests to retrieve a specific pet store
    @GetMapping("/{petStoreId}")
    public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
        log.info("Retrieving pet store with ID={}", petStoreId);
        return petStoreService.retrievePetStoreById(petStoreId);
    }
 
 // This method handles HTTP DELETE requests to remove a pet store
    @DeleteMapping("/{petStoreId}")
    public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
        log.info("Deleting pet store with ID={}", petStoreId);
        
        petStoreService.deletePetStoreById(petStoreId);
        
// Return a success message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet Store with ID=" + petStoreId + " was deleted successfully");
        
        return response;
    }    
    
    
}