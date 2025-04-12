package pet.store.controller.model;

// DTO classes are used to transfer data btw the controller & client

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;


	@Data
	@NoArgsConstructor
	public class PetStoreData {
	    private Long petStoreId;
	    private String name;
	    private String address;
	    private String city;
	    private String state;
	    private String zip;
	    private String phone;
	    
// Will be changed to Set of DTO objects
	    private Set<PetStoreCustomer> customers = new HashSet<>();
	    private Set<PetStoreEmployee> employees = new HashSet<>();
	    
// Constructor that converts from Entity to DTO
	    public PetStoreData(PetStore petStore) {
	    	
// Copy matching fields
	        this.petStoreId = petStore.getPetStoreId();
	        this.name = petStore.getName();
	        this.address = petStore.getAddress();
	        this.city = petStore.getCity();
	        this.state = petStore.getState();
	        this.zip = petStore.getZip();
	        this.phone = petStore.getPhone();
	        
// Convert customers from entities to DTOs
	        if(petStore.getCustomers() != null) {
	            for(Customer customer : petStore.getCustomers()) {
	                this.customers.add(new PetStoreCustomer(customer));
	            }
	        }
	        
// Convert employees from entities to DTOs
	        if(petStore.getEmployees() != null) {
	            for(Employee employee : petStore.getEmployees()) {
	                this.employees.add(new PetStoreEmployee(employee));
				}
			}
		}

// Inner class for Customer DTO
	    @Data
	    @NoArgsConstructor
	    public static class PetStoreCustomer {
	        private Long customerId;
	        private String customerFirstName;
	        private String customerLastName;
	        private String customerEmail;
	        private String customerPhone;
	        
// Constructor that converts from Entity to DTO
	        public PetStoreCustomer(Customer customer) {
	            this.customerId = customer.getCustomerId();
	            this.customerFirstName = customer.getCustomerFirstName();
	            this.customerLastName = customer.getCustomerLastName();
	            this.customerEmail = customer.getCustomerEmail();
	            this.customerPhone = customer.getCustomerPhone();
	        }
	    }
	    
// Inner class for Employee DTO
	    @Data
	    @NoArgsConstructor
	    public static class PetStoreEmployee {
	        private Long employeeId;
	        private String employeeFirstName;
	        private String employeeLastName;
	        private String employeePhone;
	        private String employeeJobTitle;
	        
// Constructor that converts from Entity to DTO
	        public PetStoreEmployee(Employee employee) {
	            this.employeeId = employee.getEmployeeId();
	            this.employeeFirstName = employee.getEmployeeFirstName();
	            this.employeeLastName = employee.getEmployeeLastName();
	            this.employeePhone = employee.getEmployeePhone();
	            this.employeeJobTitle = employee.getEmployeeJobTitle();
	    }
	}
}

