package com.nagarro.watchstore.validator;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dto.CartUpdateDto;
import com.nagarro.watchstore.exception.BadRequestException;


/**
 * The CartUpdateDtoValidator class is responsible for validating CartSaveDto
 * objects.
 * 
 * @author yogesh04
 */
@Component
public class CartUpdateDtoValidator implements Predicate<CartUpdateDto>{

	@Override
	public boolean test(CartUpdateDto cartUpdateDto) {
		
		if (cartUpdateDto.getWatchQty() < 0) {
		    throw new BadRequestException("CartQty", Constant.INVALID_QUANTITY);
		}
		
		if (cartUpdateDto.getWatchQty() == 0) {
		    throw new BadRequestException("CartQty", Constant.QUANTITY_REQUIRED);
		}
		return true;
	}

}
