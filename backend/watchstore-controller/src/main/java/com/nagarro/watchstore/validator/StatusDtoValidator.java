package com.nagarro.watchstore.validator;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dto.StatusDto;
import com.nagarro.watchstore.exception.BadRequestException;


/**
 * The StatusDtoValidator class is a validator that validates a StatusDto object.
 * It checks if the order status is null, and throws a BadRequestException if it is.
 * 
 * @author tushar01
 */
@Component
public class StatusDtoValidator implements Predicate<StatusDto> {

	@Override
	public boolean test(StatusDto statusDto) {
		if (statusDto == null||statusDto.getStatus()==null)
			throw new BadRequestException("status", Constant.ORDER_STATUS_REQUIRED);
		return true;
	}

}
