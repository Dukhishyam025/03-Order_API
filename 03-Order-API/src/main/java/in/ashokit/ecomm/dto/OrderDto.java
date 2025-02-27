package in.ashokit.ecomm.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDto {
	
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
	

}
