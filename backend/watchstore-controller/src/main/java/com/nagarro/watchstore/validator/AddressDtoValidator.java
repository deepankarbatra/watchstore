package com.nagarro.watchstore.validator;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dto.AddressDto;
import com.nagarro.watchstore.exception.BadRequestException;


/**
 * @author deepankarbatra
 * 
 *         This class implements the Predicate interface to validate an
 *         AddressDto object.
 */
@Component
public class AddressDtoValidator implements Predicate<AddressDto> {
	/**
	 * Validates an AddressDto object.
	 *
	 * @param addressDto The AddressDto object to be validated.
	 * @return true if the AddressDto is valid, false otherwise.
	 * @throws BadRequestException if the AddressDto is invalid, with corresponding
	 *                             error messages.
	 */
	@Override
	public boolean test(AddressDto addressDto) {
		if (addressDto.getStreetName() == null || addressDto.getStreetName().isBlank()) {
			throw new BadRequestException("Invalid street name details", "street name cannot be blank");
		}

		if (addressDto.getCity() == null || addressDto.getCity().isBlank()) {
			throw new BadRequestException("Invalid City details", "city details cannot be blank");
		}
		if (addressDto.getCountry() == null || addressDto.getCountry().isBlank()) {
			throw new BadRequestException("Invalid country details", "country details cannot be blank");
		}
		if (addressDto.getLandmark() == null || addressDto.getLandmark().isBlank()) {
			throw new BadRequestException("Invalid Landmark details", "Land mark cannot be blank");
		}
		if (addressDto.getPhoneNumber() == null || addressDto.getPhoneNumber().isBlank()) {
			throw new BadRequestException("Invalid Phone number details", "Phone number cannot be blank");
		}
		if (!isValidPhoneNumber(addressDto.getPhoneNumber())) {
			throw new BadRequestException("Invalid Phone number details", "phone number should be 10 digit number");
		}
		if (!isValidPinCode(addressDto.getPincode()) || Integer.parseInt(addressDto.getPincode()) < 100000
				|| Integer.parseInt(addressDto.getPincode()) > 999999) {
			throw new BadRequestException("Invalid pincode details", "pin code should be six digits integer");
		}

		if (addressDto.getState() == null || addressDto.getState().isBlank()) {
			throw new BadRequestException("Invalid state details", "state details must not be empty");
		}
		return true;
	}

	private static boolean isValidPhoneNumber(String phoneNumber) {
		Pattern pattern = Pattern.compile(Constant.PHONE_NUMBER_REGEX);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	private static boolean isValidPinCode(String pinCode) {
		Pattern pattern = Pattern.compile(Constant.PIN_CODE_REGEX);
		try {
			Matcher matcher = pattern.matcher(String.valueOf(pinCode));
			return matcher.matches();
		} catch (Exception e) {
			return false;
		}
	}

}
