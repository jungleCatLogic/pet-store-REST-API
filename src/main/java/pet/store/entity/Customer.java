package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//add class level annotations
@Entity	
@Data 

public class Customer {
	
//add annotations to each ID field
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

//add instance variables
private Long customerId;

private String customerFirstName;
private String customerLastName;
private String customerEmail;
private String customerPhone;
	
//add relationship variables
@ManyToMany(mappedBy = "customers", cascade = CascadeType.PERSIST)
@EqualsAndHashCode.Exclude
@ToString.Exclude
private Set<PetStore> petStores = new HashSet<>();
	
}
