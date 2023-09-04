package com.nagarro.watchstore.dto;

import lombok.Data;

/**
 * The OrderSaveDto class represents the DTO (Data Transfer Object) for placing an order.
 * It contains the necessary information for placing an order, including the quantity,
 * watch model number and delivery address ID.
 * 
 * @author tushar01
 */
@Data
public class OrderSaveDto {
	private  int quantity;
	private  String watchModelNumber;
	private  long addressId;
}
