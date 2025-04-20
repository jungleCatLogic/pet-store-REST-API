package pet.store.service;

import java.util.ArrayList;
import java.util.List;

// Service layer contains business logic
// also acts as intermediary btw controller and DAO

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
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

// Inject the EmployeeDao using Spring's dependency injection
	@Autowired
	private EmployeeDao employeeDao;	
	
// This method finds an employee by ID and verifies it belongs to the specified pet store
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {

// Retrieve the employee by ID or throw an exception if not found
	    Employee employee = employeeDao.findById(employeeId)
	            .orElseThrow(() -> new NoSuchElementException(
	                    "Employee with ID=" + employeeId + " was not found"));
	    
// Check if the employee belongs to the specified pet store
	    if (!employee.getPetStore().getPetStoreId().equals(petStoreId)) {
	        throw new IllegalArgumentException(
	                "Employee with ID=" + employeeId + " does not belong to pet store with ID=" + petStoreId);
	    }	    
	    return employee;
	}	
	
// This method either creates a new employee or finds an existing one
	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
	    if (employeeId == null) {
// If no ID is provided, create a new employee
	        return new Employee();
	    } else {
// If an ID is provided, find the existing employee
	        return findEmployeeById(petStoreId, employeeId);
	    }
	}	
	
// This method copies fields from the DTO to the entity
	private void copyEmployeeFields(Employee employee, PetStoreData.PetStoreEmployee petStoreEmployee) {
// Copy all relevant fields
	    employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
	    employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
	    employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
	    employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	}
	
// This method handles saving an employee to a pet store
	    @Transactional(readOnly = false)
	    public PetStoreData.PetStoreEmployee saveEmployee(Long petStoreId, PetStoreData.PetStoreEmployee petStoreEmployee) {
// Find the pet store by ID
	        PetStore petStore = findPetStoreById(petStoreId);
	        
// Find or create the employee
	        Long employeeId = petStoreEmployee.getEmployeeId();
	        Employee employee = findOrCreateEmployee(petStoreId, employeeId);
	        
// Copy fields from DTO to entity
	        copyEmployeeFields(employee, petStoreEmployee);
	        
// Set the pet store for the employee
	        employee.setPetStore(petStore);
	        
// Add the employee to the pet store's collection
	        petStore.getEmployees().add(employee);
	        
// Save the employee to the database
	        Employee savedEmployee = employeeDao.save(employee);
	        
// Return the employee data as a DTO
	        return new PetStoreData.PetStoreEmployee(savedEmployee);
	    }	
	    
// Inject the CustomerDao using Spring's dependency injection
	    @Autowired
	    private CustomerDao customerDao;	

// This method finds a customer by ID and verifies it is associated with the specified pet store
	    private Customer findCustomerById(Long petStoreId, Long customerId) {
// Retrieve the customer by ID or throw an exception if not found
	        Customer customer = customerDao.findById(customerId)
	                .orElseThrow(() -> new NoSuchElementException(
	                        "Customer with ID=" + customerId + " was not found"));
	        
// Check if the customer is associated with the specified pet store
// Since this is a many-to-many relationship, we need to iterate through the customer's pet stores
	        boolean found = false;
	        
	        for (PetStore store : customer.getPetStores()) {
	            if (store.getPetStoreId().equals(petStoreId)) {
	                found = true;
	                break;
	            }
	        }	        
	        if (!found) {
	            throw new IllegalArgumentException(
	                    "Customer with ID=" + customerId + " does not shop at pet store with ID=" + petStoreId);
	        }	        
	        return customer;
	    }	    
	
// This method either creates a new customer or finds an existing one
	    private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
	        if (customerId == null) {
// If no ID is provided, create a new customer
	            return new Customer();
	        } else {
// If an ID is provided, find the existing customer
	            return findCustomerById(petStoreId, customerId);
	        }
	    }
	    
// This method copies fields from the DTO to the entity
	    private void copyCustomerFields(Customer customer, PetStoreData.PetStoreCustomer petStoreCustomer) {
// Copy all relevant fields
	        customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
	        customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
	        customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	        customer.setCustomerPhone(petStoreCustomer.getCustomerPhone());
	    }
	    
// This method handles saving a customer to a pet store
	    @Transactional(readOnly = false)
	    public PetStoreData.PetStoreCustomer saveCustomer(Long petStoreId, PetStoreData.PetStoreCustomer petStoreCustomer) {
// Find the pet store by ID
	        PetStore petStore = findPetStoreById(petStoreId);
	        
// Find or create the customer
	        Long customerId = petStoreCustomer.getCustomerId();
	        Customer customer = findOrCreateCustomer(petStoreId, customerId);
	        
// Copy fields from DTO to entity
	        copyCustomerFields(customer, petStoreCustomer);
	        
// Add associations between customer and pet store
	        customer.getPetStores().add(petStore);
	        petStore.getCustomers().add(customer);
	        
// Save the customer to the database
	        Customer savedCustomer = customerDao.save(customer);
	        
// Return the customer data as a DTO
	        return new PetStoreData.PetStoreCustomer(savedCustomer);
	    }	    
	    
// This method retrieves all pet stores with summary data (no customers or employees)
	    @Transactional(readOnly = true)
	    public List<PetStoreData> retrieveAllPetStores() {
// Retrieve all pet stores from the database
	        List<PetStore> petStores = petStoreDao.findAll();
	        
// Convert to DTOs and remove customer/employee data
	        List<PetStoreData> result = new ArrayList<>();
	        
	        for (PetStore petStore : petStores) {
// Create a DTO from the entity
	            PetStoreData petStoreData = new PetStoreData(petStore);
	            
// Clear customers and employees to provide only summary data
	            petStoreData.getCustomers().clear();
	            petStoreData.getEmployees().clear();
	            
	            result.add(petStoreData);
	        }  
	        return result;
	    }	    
	    
// This method retrieves a single pet store by ID with full details
	    @Transactional(readOnly = true)
	    public PetStoreData retrievePetStoreById(Long petStoreId) {
// Find the pet store by ID
	        PetStore petStore = findPetStoreById(petStoreId);
	        
// Convert to DTO (includes all customers and employees)
	        return new PetStoreData(petStore);
	    }
	    
// This method deletes a pet store by ID
	    @Transactional(readOnly = false)
	    public void deletePetStoreById(Long petStoreId) {
// Find the pet store by ID
	        PetStore petStore = findPetStoreById(petStoreId);
	        
// Delete the pet store (this will cascade to employees and customer associations)
	        petStoreDao.delete(petStore);
	    }	    

	    
	    
	    
	    
}
