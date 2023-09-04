package com.nagarro.watchstore.dtotransformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.AddressDto;
import com.nagarro.watchstore.entity.Address;

/**
 * @author deepankarbatra
 * 
 *         This class implements the Function interface to transform an
 *         AddressDto object into an Address object.
 */
@Component
public class AddressDtoTransformer implements Function<AddressDto, Address> {

	/**
	 * Transforms an AddressDto object into an Address object.
	 *
	 * @param addressDto The AddressDto object to be transformed.
	 * @return The transformed Address object.
	 */
	@Override
	public Address apply(AddressDto addressDto) {
		Address address = new Address();
		address.setStreetName(addressDto.getStreetName());
		address.setCity(addressDto.getCity());
		address.setCountry(addressDto.getCountry());
		address.setLandmark(addressDto.getLandmark());
		address.setPhoneNumber(addressDto.getPhoneNumber());
		address.setPincode(Integer.parseInt(addressDto.getPincode()));
		address.setState(addressDto.getState());
		return address;
	}

}
