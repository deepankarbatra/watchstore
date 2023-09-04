package com.nagarro.watchstore.enums;

import java.util.Arrays;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.exception.InvalidOrderStatusException;


/**
 * The OrderStatus enum represents the possible statuses of an order.
 * 
 * @author tushar01
 */
public enum OrderStatus {
	BOOKED("BOOKED"), IN_PROGRESS("IN_PROGRESS"), IN_SHIPMENT("IN_SHIPMENT"), DELIVERED("DELIVERED");

	String value;

	OrderStatus(String value) {
		this.value = value;
	}
	
	 /**
     * Retrieves the OrderStatus based on the provided order status string.
     *
     * @param orderStatus the order status string
     * @return the corresponding OrderStatus
     * @throws InvalidOrderStatusException if the order status string is not valid
     */
	public static OrderStatus valueOfStatus(String orderStatus) {
		return Arrays.stream(values()).filter(status -> status.value.equalsIgnoreCase(orderStatus)).findFirst()
				.orElseThrow(() -> new InvalidOrderStatusException("orderStatus", Constant.STATUS_ENUM_NOT_VALID));
	}
}
