package in.ashokit.ecomm.dto;

import lombok.Data;

@Data
public class OrderItemDto {
	
	private long id;

	private int quantity;

	private double unitPrice;

	private String imageUrl;
	
	private String productName;

}
