package com.nagarro.watchstore.validator;

import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dto.CartSaveDto;
import com.nagarro.watchstore.exception.BadRequestException;


/**
 * The CartSaveDtoValidator class is responsible for validating CartSaveDto
 * objects.
 * 
 * @author yogesh04
 */

@Component
public class CartSaveDtoValidator implements Predicate<CartSaveDto> {

	@Override
	public boolean test(CartSaveDto cartSaveDto) {
		if (cartSaveDto.getWatchModel() == null || cartSaveDto.getWatchModel().isBlank()) {
			throw new BadRequestException("WatchModel", Constant.ERROR_MESSAGE_WATCH);
		}
		return true;
	}

}
