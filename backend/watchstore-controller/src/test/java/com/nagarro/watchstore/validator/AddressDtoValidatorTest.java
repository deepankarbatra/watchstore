package com.nagarro.watchstore.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nagarro.watchstore.dto.AddressDto;
import com.nagarro.watchstore.exception.BadRequestException;

/**
 * @author deepankarbatra 
 * Junit test cases for AddressDtoValidator class.
 *
 */
class AddressDtoValidatorTest {

	private Predicate<AddressDto> addressDtoValidator;
	private AddressDto addressDto;
	private boolean result;

	@BeforeEach
	public void setup() {
		addressDtoValidator = new AddressDtoValidator();
	}

	@Test
	public void testValidAddressDto() {
		givenAddressDetails("street", "city", "state", "country", "landmark", "1234567890", "123456");
		whenValidatorIsCalled();
		verifyValidAddressDto();
	}

	@Test
	public void testInvalidStreetName() {
		givenAddressDetails("", "city", "state", "country", "landmark", "1234567890", "123456");
		whenAddressDtoValidatorCalledAndThenVerify_BadRequest();
	}

	@Test
	public void testInvalidCity() {
		givenAddressDetails("street", "", "state", "country", "landmark", "1234567890", "123456");
		whenAddressDtoValidatorCalledAndThenVerify_BadRequest();
	}

	@Test
	public void testInvalidState() {
		givenAddressDetails("street", "city", "", "country", "landmark", "1234567890", "123456");
		whenAddressDtoValidatorCalledAndThenVerify_BadRequest();
	}

	@Test
	public void testInvalidCountry() {
		givenAddressDetails("street", "city", "state", "", "landmark", "1234567890", "123456");
		whenAddressDtoValidatorCalledAndThenVerify_BadRequest();
	}

	@Test
	public void testInvalidLandmark() {
		givenAddressDetails("street", "city", "state", "country", "", "1234567890", "123456");
		whenAddressDtoValidatorCalledAndThenVerify_BadRequest();
	}

	@Test
	public void testInvalidPhoneNumberFormat() {
		givenAddressDetails("street", "city", "state", "country", "landmark", "4567890", "123456");
		whenAddressDtoValidatorCalledAndThenVerify_BadRequest();
	}

	@Test
	public void testEmptyPhoneNumberFormat() {
		givenAddressDetails("street", "city", "state", "country", "landmark", "", "123456");
		whenAddressDtoValidatorCalledAndThenVerify_BadRequest();
	}

	@Test
	public void testInvalidPincodeFormat() {
		givenAddressDetails("street", "city", "state", "country", "landmark", "1234567890", "1456");
		whenAddressDtoValidatorCalledAndThenVerify_BadRequest();
	}

	private void givenAddressDetails(String streetName, String cityName, String stateName, String countryName,
			String landmark, String phoneNumber, String pincode) {
		this.addressDto = new AddressDto();
		addressDto.setStreetName(streetName);
		addressDto.setCity(cityName);
		addressDto.setState(stateName);
		addressDto.setCountry(countryName);
		addressDto.setLandmark(landmark);
		addressDto.setPhoneNumber(phoneNumber);
		addressDto.setPincode(pincode);

	}

	private void whenAddressDtoValidatorCalledAndThenVerify_BadRequest() {
		assertThrows(BadRequestException.class, () -> addressDtoValidator.test(addressDto));
	}

	private void whenValidatorIsCalled() {
		this.result = addressDtoValidator.test(addressDto);
	}

	private void verifyValidAddressDto() {
		assertTrue(this.result);
	}
}
