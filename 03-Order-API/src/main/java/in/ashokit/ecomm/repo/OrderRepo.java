package in.ashokit.ecomm.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.ecomm.dto.OrderDto;
import in.ashokit.ecomm.entities.Order;
import java.util.List;


public interface OrderRepo extends JpaRepository<Order, Long>{

	public Order findByRazorPayOrderId(String razorPayOrderId);
	
	public List<Order> findByEmail(String email);
}
