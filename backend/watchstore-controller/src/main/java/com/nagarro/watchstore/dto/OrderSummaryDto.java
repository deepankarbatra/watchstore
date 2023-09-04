package com.nagarro.watchstore.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * The OrderSummaryDto class represents the DTO (Data Transfer Object) for an order.
 * It contains the summarized order details such as ID, quantity, status, amount,
 * user details, and watch details.
 * 
 * @author tushar01
 */
@Data
public class OrderSummaryDto {

	private String id;
	private int quantity;
	private OrderStatusDto orderStatus;
	private BigDecimal amount;
	private UserProfileDto user;
	private WatchDto watch;
}
