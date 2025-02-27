package in.ashokit.ecomm.response;

import lombok.Data;

@Data
public class ApiResponse<T>{
	
	private Integer status;
	private String Message;
	private  T data;

}
