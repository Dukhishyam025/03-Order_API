package in.ashokit.ecomm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.ecomm.entities.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
