package pet.store;

//Create app main class that will start Spring Boot

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// Annotation enables component scanning
@SpringBootApplication

// Annotation explicitly enables JPA repos
@EnableJpaRepositories(basePackages = "pet.store.dao")

public class PetStoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);
	}
}
