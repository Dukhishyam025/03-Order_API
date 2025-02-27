package in.ashokit.ecomm.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.ecomm.dto.AddressDto;
import in.ashokit.ecomm.dto.CustomerDto;
import in.ashokit.ecomm.dto.OrderDto;
import in.ashokit.ecomm.dto.OrderItemDto;
import in.ashokit.ecomm.dto.PaymentCallBackDto;
import in.ashokit.ecomm.entities.Address;
import in.ashokit.ecomm.entities.Customer;
import in.ashokit.ecomm.entities.Order;
import in.ashokit.ecomm.entities.OrderItem;
import in.ashokit.ecomm.repo.AddressRepo;
import in.ashokit.ecomm.repo.CustomerRepo;
import in.ashokit.ecomm.repo.OrderItemRepo;
import in.ashokit.ecomm.repo.OrderRepo;
import in.ashokit.ecomm.request.PurchaseOrderRequest;
import in.ashokit.ecomm.response.PurchaseOrderResponse;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private OrderItemRepo orderItemRepo;
	
	@Autowired
    private AddressRepo addressRepo;
	
	@Autowired
	private RazorpayService razorpayService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public PurchaseOrderResponse createOrder(PurchaseOrderRequest orderRequest) {
		
		
		CustomerDto customerDto = orderRequest.getCustomer();
		AddressDto addressDto = orderRequest.getAddress();
		OrderDto orderDto = orderRequest.getOrder();
		List<OrderItemDto> orderItemsList = orderRequest.getOrderItems();
		
		//check customer presence in DB and save if required
		Customer c = customerRepo.findByEmail(customerDto.getEmail());
		if(c==null)
		{
			c= new Customer();
			c.setName(customerDto.getName());
			c.setEmail(customerDto.getEmail());
			c.setPhoneNo(customerDto.getPhoneNo());
			customerRepo.save(c);
		}
		
		//save address
		Address address = new Address();
		address.setHouseNum(addressDto.getHouseNum());
		address.setStreet(addressDto.getStreet());
		address.setCity(addressDto.getCity());
		address.setState(addressDto.getState());
		address.setPincode(addressDto.getPincode());
		address.setCustomer(c);
		addressRepo.save(address);
		
		//save order
		Order newOrder = new Order();
		String orderTrackingNum = generateOrderTrackingNum();
		newOrder.setOrderTrackingNum(orderTrackingNum);
		newOrder.setOrderTrackingNum(generateOrderTrackingNum());
		com.razorpay.Order paymentOrder = razorpayService.createPaymentOrder(orderDto.getTotalPrice());
		newOrder.setRazorPayOrderId(paymentOrder.get("id"));
		newOrder.setOrderStatus(paymentOrder.get("status"));
		newOrder.setTotalPrice(orderDto.getTotalPrice());
		newOrder.setTotalQuantity(orderDto.getTotalQuantity());
		newOrder.setEmail(c.getEmail());
		
		newOrder.setCustomer(c); //association mapping
		newOrder.setAddress(address); //association mapping
		
		orderRepo.save(newOrder);
		
		//save order items
		for(OrderItemDto itemDto : orderItemsList)
		{
			OrderItem item = new OrderItem();
			BeanUtils.copyProperties(itemDto, item);
			item.setOrder(newOrder); //association mapping
			orderItemRepo.save(item);
		}
		
		//prepare
//		PurchaseOrderResponse response = new PurchaseOrderResponse();
//		response.setRazorPayOrderId(orderTrackingNum);
//		response.setOrderStatus(orderTrackingNum);
//		response.setOrderTrackingNumber(orderTrackingNum);
//		
		
		return PurchaseOrderResponse.builder()
                                    .razorPayOrderId(orderTrackingNum)
                                    .orderStatus(orderTrackingNum)
                                    .orderTrackingNumber(orderTrackingNum)
                                    .build();
	}

	@Override
	public List<OrderDto> getOrdersByEmail(String email) {
		
		List<OrderDto> dtosList = new ArrayList<>();

		List<Order> orderList = orderRepo.findByEmail(email);
		
		for(Order order : orderList)
		{
			OrderDto dto = new OrderDto();
			BeanUtils.copyProperties(order, dto);
			dtosList.add(dto);
		}
		
		return dtosList;
	}

	@Override
	public PurchaseOrderResponse updateOrder(PaymentCallBackDto paymentCallBackDto) {

		   Order order = orderRepo.findByRazorPayOrderId(paymentCallBackDto.getRazorpayOrderId());
		   
		   if(order != null)
		   {
			   order.setOrderStatus("CONFIRMED");
			   order.setDeliveryDate(LocalDate.now().plusDays(3));
			   orderRepo.save(order);
			   
			   String subject = "Your Order Confirmed";
			   String body = "ThankYou, You will recieve your order on" + order.getDeliveryDate();
			   
			   emailService.sendEmail(order.getEmail(), subject, body);
		   }
		   
		   //Prepare and Return Response
		   return PurchaseOrderResponse.builder()
                                       .razorPayOrderId(paymentCallBackDto.getRazorpayOrderId())
                                       .orderStatus(order.getOrderStatus())
                                       .orderTrackingNumber(order.getOrderTrackingNum())
                                       .build();
		
}
	
	private String generateOrderTrackingNum()
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHss");
		String timestamp = sdf.format(new Date());
		
		String randomUuid = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
		
		//combine timestamp and uuid to form order tracking num
		return "OD" + timestamp + randomUuid;
		
	}

}
