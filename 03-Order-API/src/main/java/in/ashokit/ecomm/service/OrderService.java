package in.ashokit.ecomm.service;

import java.util.List;

import in.ashokit.ecomm.dto.OrderDto;
import in.ashokit.ecomm.dto.PaymentCallBackDto;
import in.ashokit.ecomm.request.PurchaseOrderRequest;
import in.ashokit.ecomm.response.PurchaseOrderResponse;

public interface OrderService {
	
	public PurchaseOrderResponse createOrder(PurchaseOrderRequest orderRequest);
	
	public List<OrderDto> getOrdersByEmail(String email);
	
	public PurchaseOrderResponse updateOrder(PaymentCallBackDto paymentCallBackDto);
	
	

}
