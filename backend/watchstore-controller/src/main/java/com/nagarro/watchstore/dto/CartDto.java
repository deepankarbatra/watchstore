package com.nagarro.watchstore.dto;

import lombok.Data;

/**
 * The CartDto class represents a shopping cart.
 * It contains information about the cart ID, user ID, watch quantity, and the associated watch.
 * 
 * @author yogesh04
 * @version 1.0
 */
@Data
public class CartDto {
	private int cartId;
	private int watchQty;
	private WatchDto watch;
}
