package in.ashokit.ecomm.request;

import java.util.List;

import in.ashokit.ecomm.dto.AddressDto;
import in.ashokit.ecomm.dto.CustomerDto;
import in.ashokit.ecomm.dto.OrderDto;
import in.ashokit.ecomm.dto.OrderItemDto;
import lombok.Builder;
import lombok.Data;

@Data
public class PurchaseOrderRequest {
	
	private CustomerDto customer;
	
	private AddressDto address;
	
	private OrderDto order;
	
	private  List<OrderItemDto> orderItems;


}
