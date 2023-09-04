package com.nagarro.watchstore.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.controller.AddressController;
import com.nagarro.watchstore.dto.AddressDto;
import com.nagarro.watchstore.entity.Address;
import com.nagarro.watchstore.extractor.UserDetailExtractor;
import com.nagarro.watchstore.extractor.UserInfo;
import com.nagarro.watchstore.response.ApiResponse;
import com.nagarro.watchstore.service.AddressService;

/**
 * @author deepankarbatra Junit test cases for AddressControllerImpl class.
 *
 */
public class AddressControllerImplTest {

	private AddressController addressController;

	@Mock
	private Function<AddressDto, Address> addressDtoTransformer;
	@Mock
	private Function<Address, AddressDto> addressTransformer;
	@Mock
	private Predicate<AddressDto> addressDtoValidator;
	@Mock
	private AddressService addressService;
	@Mock
	private UserDetailExtractor userDetailExtractor;

	private AddressDto addressDto;
	private Address address;
	private UserInfo userInfo;
	private ResponseEntity<ApiResponse> response;
	private List<Address> addresses;
	private List<AddressDto> addressDtos;
	private ResponseEntity<List<AddressDto>> responseList;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		addressController = new AddressControllerImpl(addressDtoTransformer, addressService, addressTransformer,
				addressDtoValidator, userDetailExtractor);
	}

	@Test
	public void save_ValidAddressDto_ReturnsCreatedResponse() {
		givenAddressDto();
		mockSaveAddress();
		whenSaveAddressIsCalled();
		thenVerifySave();
	}

	@Test
	public void update_ValidAddressDto_ReturnsOkResponse() {
		givenAddressDto();
		mockUpdateAddress();
		whenAddressUpdateCalled();
		thenVerifyUpdate();
	}

	@Test
	public void get_ReturnsListOfAddressDto() {
		givenListOfAddresses();
		mockGetAddress();
		whenGetAddressesCalled();
		thenVerifyList();
	}

	@Test
	public void delete_ReturnsOkResponse() {
		whenAddressIsDeleted();
		mockDeleteAddress();
		thenVerifyDelete();
	}

	private void givenListOfAddresses() {
		this.address = new Address();
		this.addresses = new ArrayList<>();
		this.addresses.add(new Address());
		this.addressDtos = new ArrayList<>();
		this.addressDtos.add(new AddressDto());
		this.userInfo = new UserInfo();
	}

	private void givenAddressDto() {
		this.address = new Address();
		this.userInfo = new UserInfo();
		this.addressDto = new AddressDto();
	}

	private void whenAddressIsDeleted() {
		this.response = this.addressController.delete(1L);
	}

	private void whenSaveAddressIsCalled() {
		this.response = this.addressController.save(addressDto);

	}

	private void whenAddressUpdateCalled() {
		this.response = this.addressController.update(addressDto, 1L);
	}

	private void whenGetAddressesCalled() {
		this.responseList = this.addressController.get();
	}

	private void thenVerifyDelete() {
		assertEquals(HttpStatus.OK, this.response.getStatusCode());
		assertEquals(Constant.DELETE_ADDRESS_SUCCESSFULL, this.response.getBody().getMessage());
	}

	private void thenVerifySave() {
		assertEquals(HttpStatus.CREATED, this.response.getStatusCode());
		assertEquals(Constant.ADD_ADDRESS_SUCCESSFULL, this.response.getBody().getMessage());
	}

	private void thenVerifyUpdate() {
		assertEquals(HttpStatus.OK, this.response.getStatusCode());
		assertEquals(Constant.UPDATE_ADDRESS_SUCCESSFULL, this.response.getBody().getMessage());
	}

	private void thenVerifyList() {
		assertEquals(HttpStatus.OK, this.responseList.getStatusCode());
		assertEquals(addressDtos.size(), this.responseList.getBody().size());
	}

	private void mockSaveAddress() {
		when(this.addressDtoValidator.test(addressDto)).thenReturn(true);
		when(this.addressDtoTransformer.apply(addressDto)).thenReturn(address);
		when(this.userDetailExtractor.getUserInfo()).thenReturn(userInfo);
		when(this.addressService.save(any(), any())).thenReturn(address);
	}

	private void mockUpdateAddress() {
		when(this.addressDtoValidator.test(addressDto)).thenReturn(true);
		when(this.addressDtoTransformer.apply(addressDto)).thenReturn(address);
		when(this.addressService.update(any(), any())).thenReturn(address);
	}

	private void mockGetAddress() {
		when(this.userDetailExtractor.getUserInfo()).thenReturn(this.userInfo);
		when(this.addressService.get(this.userInfo)).thenReturn(this.addresses);
		when(this.addressTransformer.apply(any())).thenReturn(new AddressDto());
	}

	private void mockDeleteAddress() {
		Mockito.doNothing().when(this.addressService).delete(any());
	}

}
