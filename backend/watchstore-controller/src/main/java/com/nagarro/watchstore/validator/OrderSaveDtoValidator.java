package com.nagarro.watchstore.validator;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dto.OrderSaveDto;
import com.nagarro.watchstore.exception.BadRequestException;


/**
 * The OrderSaveDtoValidator class is a validator that validates a OrderSaveDto object.
 * It checks if the addressId, quantity, and watchModelNumber fields meet the required criteria,
 * and throws a BadRequestException if any of the validations fail.
 * 
 * @author tushar01
 */
@Component
public class OrderSaveDtoValidator implements Predicate<OrderSaveDto> {
	

	@Override
	public boolean test(OrderSaveDto orderSaveDto) {
		if (orderSaveDto.getAddressId() == 0)
			throw new BadRequestException("addressId", Constant.ADRESS_ID_REQUIRED);
		if (orderSaveDto.getQuantity() == 0)
			throw new BadRequestException("quantity", Constant.QUANTITY_REQUIRED);
		if (orderSaveDto.getWatchModelNumber() == null || orderSaveDto.getWatchModelNumber().isBlank())
			throw new BadRequestException("watchModelNumber", Constant.WATCH_MODEL_REQUIRED);
		if (orderSaveDto.getQuantity() < 0)
			throw new BadRequestException("quantity", Constant.INVALID_QUANTITY);
		return true;
	}

}
