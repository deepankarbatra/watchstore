package com.nagarro.watchstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nagarro.watchstore.dao.AddressDao;
import com.nagarro.watchstore.entity.Address;
import com.nagarro.watchstore.exception.NotFoundException;
import com.nagarro.watchstore.extractor.UserInfo;
import com.nagarro.watchstore.service.AddressService;

/**
 * @author deepankarbatra Unit tests for AddressServiceImpl class.
 */
public class AddressServiceImplTest {

	@Mock
	private AddressDao addressDao;

	private AddressService addressService;

	private Address address;
	private UserInfo user;
	private Address actualAddress;
	private Address addressToUpdate;
	private Address updatedAddress;
	private List<Address> addresses;
	private List<Address> actualList = new ArrayList<>();
	private Optional<Address> optionalAddress;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.addressService = new AddressServiceImpl(addressDao);
	}

	/**
	 * Test case for saving an address.
	 */
	@Test
	public void saveAddressTest() {
		givenAddressAndUser();
		mockSaveAddress();
		whenSaveMethodCalled();
		thenVerify();
	}

	/**
	 * Test case for getting addresses by user.
	 */
	@Test
	public void getAddressByUserTest() {
		givenAddressAndUser();
		mockGetAddress();
		whenFindByUserIdIsCalled();
		thenVerifyList();
	}

	/**
	 * Test case for handling the NotFound exception when getting address list.
	 */
	@Test
	public void getAddressList_NotFoundExceptionTest() {
		givenUser();
		mockGetAddress();
		thenVerifyNotFoundException();
	}

	/**
	 * Test case for updating an address.
	 */
	@Test
	public void updateAddressTest() {
		givenAddressIdAndAddress(1);
		mockUpdateAddress();
		whenFindByIdIsCalledUpdate();
		thenVerifyUpdate();
	}

	/**
	 * Test case for handling the NotFound exception when updating an address.
	 */
	@Test
	public void updateAddress_NotFoundExceptionTest() {
		givenAddressIdAndAddress(0);
		mockUpdateAddress();
		thenVerifyUpdateAddressNotFound();
	}

	/**
	 * Test case for deleting an address.
	 */
	@Test
	public void deleteAddressTest() {
		givenAddressIdForDelete(1);
		mockDeleteAddress();
		whenAddressIsDeleted();
		thenVerifyDelete();
	}

	/**
	 * Test case for handling the NotFound exception when deleting an address.
	 */
	@Test
	public void deleteAddress_NotFoundExceptionTest() {
		givenAddressIdForDelete(0);
		mockDeleteAddress();
		thenVerifyDeleteAddress_NotFound();
	}

	/**
	 * Test case for finding an address by ID and user ID.
	 */
	@Test
	public void FindByIdAndUserIdTest() {
		givenAddressIdAndUserId(1, "test@abc.com");
		mockIdAndUserId();
		whenFindByIdAndUserIdIsCalled();
		thenVerifyFindByIdAndUserId();
	}

	/**
	 * Test case for handling the NotFound exception when finding an address by ID
	 * and user ID.
	 */
	@Test
	public void FindByIdAndUserId_NotFoundTest() {
		givenAddressIdAndUserId(0, "test@abc.com");
		mockIdAndUserId();
		thenVerifyFindByIdAndUserId_NotFound();
	}

	private void givenAddressAndUser() {
		this.setUser();
		this.setAddress();
	}

	private void setAddress() {
		address = new Address();
		address.setAddressId(1);
		address.setCity("city");
		address.setCountry("country");
		address.setLandmark("landmark");
		address.setPhoneNumber("9999999999");
		address.setPincode(121006);
		address.setState("state");
		address.setStreetName("street");
		address.setUserId(user.getEmailId());
	}

	private void setUser() {
		user = new UserInfo();
		user.setEmailId("test@email.com");
	}

	private void givenAddressIdAndUserId(long addressId, String userId) {
		this.givenAddressAndUser();
		this.address.setAddressId(addressId);
		this.address.setUserId(userId);
	}

	private void givenUser() {
		this.setUser();
	}

	private void givenAddressIdAndAddress(long addressId) {
		if (addressId != 0) {
			this.givenAddressAndUser();
		} else {
			this.address = new Address();
		}
		this.addressToUpdate = this.address;
	}

	private void givenAddressIdForDelete(long addressId) {
		this.givenAddressAndUser();
		this.address.setAddressId(addressId);
	}

	private void whenAddressIsDeleted_NotFoundException() {
		this.addressService.delete(this.address.getAddressId());
	}

	private void whenSaveMethodCalled() {
		this.actualAddress = this.addressService.save(this.address, this.user);
	}

	private void whenFindByUserIdIsCalled() {
		this.addresses.add(this.address);
		this.actualList = this.addressService.get(this.user);
	}

	private void whenFindByIdAndUserIdIsCalled() {
		this.actualAddress = new Address();
		this.actualAddress = this.addressService.findByIdAndUserId(this.address.getAddressId(),
				this.address.getUserId());
	}

	private void whenFindByUserIdIsCalled_NotFoundException() throws NotFoundException {
		this.actualList = this.addressService.get(this.user);
	}

	private void whenFindByIdIsCalledUpdate() {
		this.updatedAddress = this.addressService.update(this.address.getAddressId(), this.addressToUpdate);
	}

	private void whenAddressIsDeleted() {
		this.addressService.delete(this.address.getAddressId());
	}

	private void whenAddressUpdate_NotFound() throws NotFoundException {
		this.updatedAddress = this.addressService.update(this.address.getAddressId(), this.addressToUpdate);
	}

	private void whenFindByIdAndUserIdNotFound() {
		this.actualAddress = new Address();
		this.actualAddress = this.addressService.findByIdAndUserId(this.address.getAddressId(),
				this.address.getUserId());
	}

	private void thenVerifyUpdateAddressNotFound() {
		assertThrows(NotFoundException.class, () -> whenAddressUpdate_NotFound());
	}

	private void thenVerifyDelete() {
		assertDoesNotThrow(() -> this.addressService.delete(this.address.getAddressId()));
	}

	private void thenVerifyUpdate() {
		assertEquals(this.addressToUpdate, this.updatedAddress);
	}

	private void thenVerifyNotFoundException() {
		assertThrows(NotFoundException.class, () -> whenFindByUserIdIsCalled_NotFoundException());
	}

	private void thenVerifyList() {
		assertEquals(this.addresses, this.actualList);
	}

	private void thenVerifyDeleteAddress_NotFound() {
		assertThrows(NotFoundException.class, () -> whenAddressIsDeleted_NotFoundException());
	}

	private void thenVerify() {
		assertEquals(this.address, this.actualAddress);
	}

	private void thenVerifyFindByIdAndUserId() {
		assertEquals(this.optionalAddress.get(), this.actualAddress);
	}

	private void thenVerifyFindByIdAndUserId_NotFound() {
		assertThrows(NotFoundException.class, () -> whenFindByIdAndUserIdNotFound());
	}

	private void mockSaveAddress() {
		when(this.addressDao.save(this.address)).thenReturn(this.address);
	}

	private void mockGetAddress() {
		this.addresses = new ArrayList<>();
		when(this.addressDao.findByUserId(this.user.getEmailId())).thenReturn(this.addresses);
	}

	private void mockUpdateAddress() {
		if (this.address.getAddressId() == 0) {
			this.optionalAddress = Optional.empty();
		} else {
			this.optionalAddress = Optional.of(this.addressToUpdate);
		}
		when(this.addressDao.findById(this.address.getAddressId())).thenReturn(optionalAddress);
		when(this.addressDao.save(this.addressToUpdate)).thenReturn(this.addressToUpdate);
	}

	private void mockDeleteAddress() {
		if (this.address.getAddressId() == 0) {
			this.optionalAddress = Optional.empty();
		} else {
			optionalAddress = Optional.of(this.address);
		}
		when(this.addressDao.findById(this.address.getAddressId())).thenReturn(optionalAddress);

	}

	private void mockIdAndUserId() {
		if (this.address.getAddressId() == 0) {
			this.optionalAddress = Optional.empty();
		} else {
			this.optionalAddress = Optional.of(this.address);
		}
		when(this.addressDao.findByAddressIdAndUserId(this.address.getAddressId(), this.address.getUserId()))
				.thenReturn(optionalAddress);
	}

}
