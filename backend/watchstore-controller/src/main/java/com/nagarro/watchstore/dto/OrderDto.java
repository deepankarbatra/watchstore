package com.nagarro.watchstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * The OrderDto class represents the DTO (Data Transfer Object) for an order.
 * It contains the order details such as ID, quantity, status, amount, timestamps,
 * user details, delivery address, and watch details.
 * 
 * @author tushar01
 */
@Data
public class OrderDto {

	private String id;
	private int quantity;
	private OrderStatusDto orderStatus;
	private BigDecimal amount;
	private LocalDateTime timestamp;
	private LocalDateTime statusTimestamp;
	private UserProfileDto user;
	private AddressDto deliveryAddress;
	private WatchDto watch;
}
