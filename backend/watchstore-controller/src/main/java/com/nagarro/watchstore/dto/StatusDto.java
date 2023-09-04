package com.nagarro.watchstore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The StatusDto class represents the DTO (Data Transfer Object) for updating the order status.
 * It contains the new status value that needs to be assigned to an order.
 * 
 * @author tushar01
 *
 */
@NoArgsConstructor
@Getter
public class StatusDto {

	private OrderStatusDto status;

	public void setStatus(String  status) {
		this.status=OrderStatusDto.fromString(status.trim());
	}
}
