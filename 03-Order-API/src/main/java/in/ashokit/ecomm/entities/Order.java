package in.ashokit.ecomm.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orders")
@Setter
@Getter
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	private String orderTrackingNum;
	
	private String razorPayOrderId;// payment initiated
	
	private String invoiceUrl;
	
	private String email;
	
	private String orderStatus;
	
	private Double totalPrice;
	
	private Integer totalQuantity;
	
	private String razorPayPayment; //payment completed
	
	private LocalDate deliveryDate;
	
	private LocalDate dateCreated;
	
	private LocalDate lastUpdated;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="addr_id")
	private Address address;
	

}
