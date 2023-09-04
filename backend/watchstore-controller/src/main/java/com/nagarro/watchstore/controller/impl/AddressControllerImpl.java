package com.nagarro.watchstore.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.controller.AddressController;
import com.nagarro.watchstore.dto.AddressDto;
import com.nagarro.watchstore.entity.Address;
import com.nagarro.watchstore.extractor.UserDetailExtractor;
import com.nagarro.watchstore.extractor.UserInfo;
import com.nagarro.watchstore.response.ApiResponse;
import com.nagarro.watchstore.service.AddressService;

/**
 * @author deepankarbatra
 * 
 *         This class implements the AddressController interface and provides
 *         the endpoints for managing addresses.
 */
@RestController
public class AddressControllerImpl implements AddressController {

	private Function<AddressDto, Address> addressDtoTransformer;

	private Function<Address, AddressDto> addressTransformer;

	private Predicate<AddressDto> addressDtoValidator;

	private AddressService addressService;
	
	private UserDetailExtractor userDetailExtractor;

	private static final Logger logger = LoggerFactory.getLogger(CartControllerImpl.class);

	/**
	 * Constructs an instance of AddressControllerImpl with the necessary
	 * dependencies.
	 *
	 * @param addressDtoTransformer          The transformer function to convert
	 *                                       AddressDto to Address.
	 * @param addressService                 The AddressService for address-related
	 *                                       operations.
	 * @param addressTransformer       The transformer function to convert
	 *                                       Address to AddressDto.
	 * @param addressDtoValidator            The validator predicate for
	 *                                       AddressDto.
	 * @param userDetailExtractor            Extracts user details 
	 */
	@Autowired
	AddressControllerImpl(Function<AddressDto, Address> addressDtoTransformer, AddressService addressService,
			Function<Address, AddressDto> addressTransformer,
			Predicate<AddressDto> addressDtoValidator,
			UserDetailExtractor userDetailExtractor) {
		this.addressDtoTransformer = addressDtoTransformer;
		this.addressService = addressService;
		this.addressTransformer = addressTransformer;
		this.addressDtoValidator = addressDtoValidator;
		this.userDetailExtractor = userDetailExtractor;
	}
 
	@Override
	public ResponseEntity<ApiResponse> save(AddressDto addressDto) {
		addressDtoValidator.test(addressDto);
		logger.info("saving addresses : {}", addressDto);
		Address addressTransform = addressDtoTransformer.apply(addressDto);
		final UserInfo user = this.userDetailExtractor.getUserInfo();
		logger.info("User Id : {}", user.getEmailId());
		this.addressService.save(addressTransform, user);
		ApiResponse saveResponse = new ApiResponse(Constant.ADD_ADDRESS_SUCCESSFULL);
		return new ResponseEntity<>(saveResponse, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<AddressDto>> get() {
		final UserInfo user = this.userDetailExtractor.getUserInfo();
		logger.info("Getting addresses for user ID: {}", user.getEmailId());
		List<AddressDto> address = new ArrayList<>();
		this.addressService.get(user)
				.forEach(addressRes -> address.add(this.addressTransformer.apply(addressRes)));

		return new ResponseEntity<>(address, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> update(AddressDto addressDto, long addressId) {
		logger.info("Updating address with ID: {}, Data: {}", addressId, addressDto);
		addressDtoValidator.test(addressDto);
		Address addressTransform = this.addressDtoTransformer.apply(addressDto);
		this.addressService.update(addressId, addressTransform);
		ApiResponse updateResponse = new ApiResponse(Constant.UPDATE_ADDRESS_SUCCESSFULL);
		return new ResponseEntity<>(updateResponse, HttpStatus.OK);
	}
 
	@Override
	public ResponseEntity<ApiResponse> delete(long addressId) {
		logger.info("Deleting address with ID: {}", addressId);
		this.addressService.delete(addressId);
		ApiResponse deleteResponse = new ApiResponse(Constant.DELETE_ADDRESS_SUCCESSFULL);
		return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
	}
}
