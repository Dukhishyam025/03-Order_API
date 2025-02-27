package in.ashokit.ecomm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.ecomm.entities.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
