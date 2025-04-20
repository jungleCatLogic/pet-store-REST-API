package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//add class level annotations
@Entity
@Data


public class PetStore {
//add annotations to each ID field	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

//add instance variables	
	private Long petStoreId;

	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	
//add relationship variables
// Many-to-many relationship with Customer
// Use CascadeType.PERSIST to save customers,
// but do not delete them when the pet store is deleted

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "pet_store_customer", 
			joinColumns = @JoinColumn(name = "pet_store_id"),
			inverseJoinColumns = @JoinColumn(name = "customer_id")
			)
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Customer> customers = new HashSet<>();

	
// One-to-many relationship with Employee
// Use CascadeType.ALL to cascade all operations to employees	
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<Employee> employees = new HashSet<>();

	
	
}
