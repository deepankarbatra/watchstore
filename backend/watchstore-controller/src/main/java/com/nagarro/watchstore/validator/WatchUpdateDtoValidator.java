package com.nagarro.watchstore.validator;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.WatchDto;
import com.nagarro.watchstore.exception.BadRequestException;

/**
 * Validator class for validating a WatchUpdateDto object.
 * 
 * @author karan
 */
@Component
public class WatchUpdateDtoValidator implements Predicate<WatchDto> {

	@Override
	public boolean test(WatchDto watchDto) {

		if (watchDto.getWatchBrand() == null || watchDto.getWatchBrand().isBlank()) {
			throw new BadRequestException("Watch Brand", "Watch Brand is required");
		}

		if (watchDto.getWatchName() == null || watchDto.getWatchName().isBlank()) {
			throw new BadRequestException("Watch name", "Watch name is required");
		}

		if (watchDto.getWatchType() == null) {
			throw new BadRequestException("Watch type", "Watch type is required");
		}

		if (watchDto.getImagePathList() == null || watchDto.getImagePathList().isEmpty()) {
			throw new BadRequestException("Image Path", "At least one image path is required");
		}

		if (watchDto.getStockQuantity() <= 0) {
			if (watchDto.getStockQuantity() < 0) {
				throw new BadRequestException("Stock quantity", "stock cannot be less then zero");
			}
		}

		if (watchDto.getPrice() == null) {
			throw new BadRequestException("Watch price", "Watch price is required");

		} else {
			if (watchDto.getPrice().signum() <= 0) {
				throw new BadRequestException("Watch price", "Watch price cannot be less then or equal to zero");
			}
		}

		if (watchDto.getAvailableStatus() == null) {
			throw new BadRequestException("Available status", "Available status is required");
		}

		return true;

	}

}
