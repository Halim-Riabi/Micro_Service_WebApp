package org.sid.customerservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString @Builder
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
}
//@CrossOrigin
@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer,Long> {
    public Customer findCustomerByName(String name);
}

@Projection(name = "fullCustomer",types = Customer.class)
interface CustomerProjection extends Projection{
    public Long getId();
    public String getName();
    public String getEmail();
}



/*
@SpringBootApplication
public class CustomerServiceApplication {
    @Autowired
    CustomerRepository customerRepository;
    public static void main(String[] args) { SpringApplication.run(CustomerServiceApplication.class, args);

        @Bean
        CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration){
            return args ->{
                customerRepository.save(new Customer(null,"eni","contact@eni.tn"));
                customerRepository.save(new Customer(null,"FST","contact@fst.tn"));
                customerRepository.save(new Customer(null,"ENSI","contact@ensi.tn"));
                customerRepository.findAll().forEach(System.out::println);
            };
        }

    }

}*/

@SpringBootApplication
public class CustomerServiceApplication {

    @Autowired
    CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration restconfig) {

        return args -> {
            restconfig.exposeIdsFor(Customer.class);
            customerRepository.save(new Customer(null, "eni", "contact@eni.tn"));
            customerRepository.save(new Customer(null, "FST", "contact@fst.tn"));
            customerRepository.save(new Customer(null, "ENSI", "contact@ensi.tn"));
            customerRepository.findAll().forEach(System.out::println);
        };
    }
}
