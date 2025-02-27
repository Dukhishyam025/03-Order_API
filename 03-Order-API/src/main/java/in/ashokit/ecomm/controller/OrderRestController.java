package in.ashokit.ecomm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.ecomm.dto.OrderDto;
import in.ashokit.ecomm.dto.PaymentCallBackDto;
import in.ashokit.ecomm.request.PurchaseOrderRequest;
import in.ashokit.ecomm.response.ApiResponse;
import in.ashokit.ecomm.response.PurchaseOrderResponse;
import in.ashokit.ecomm.service.OrderService;

@RestController
public class OrderRestController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order")
	public ResponseEntity<ApiResponse<PurchaseOrderResponse>> createOrder(PurchaseOrderRequest request)
	{
		ApiResponse<PurchaseOrderResponse> response = new ApiResponse<>();
		
		PurchaseOrderResponse orderRespo = orderService.createOrder(request);
		
		if(orderRespo != null)
		{
			response.setStatus(200);
			response.setMessage("Order Created");
			response.setData(orderRespo);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			response.setStatus(500);
			response.setMessage("Order Creation Failed");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@PutMapping("/order")
	public ResponseEntity<ApiResponse<PurchaseOrderResponse>> updateOrder(@RequestBody PaymentCallBackDto request)
	{
		ApiResponse<PurchaseOrderResponse> response = new ApiResponse<>();
		
		PurchaseOrderResponse orderRespo = orderService.updateOrder(request);
		
		if(orderRespo != null)
		{
			response.setStatus(200);
			response.setMessage("Order Update");
			response.setData(orderRespo);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			response.setStatus(500);
			response.setMessage("Order Updated Failed");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@GetMapping("/orders/{email}")
	public ResponseEntity<ApiResponse<List<OrderDto>>> getOrders(@PathVariable String email)
	{
		ApiResponse<List<OrderDto>> response = new ApiResponse<>();
		
		List<OrderDto> ordersByEmail= orderService.getOrdersByEmail(email);
		
		if(ordersByEmail.isEmpty())
		{
			response.setStatus(200);
			response.setMessage("Fetched Orders");
			response.setData(ordersByEmail);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			response.setStatus(500);
			response.setMessage("Failed to fetch orders ");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

}
