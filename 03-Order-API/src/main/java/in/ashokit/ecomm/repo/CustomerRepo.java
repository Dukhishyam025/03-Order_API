package in.ashokit.ecomm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.ecomm.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{
	
	public Customer findByEmail(String email);

}
