package com.nagarro.watchstore.entitytransformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.AddressDto;
import com.nagarro.watchstore.entity.Address;

/**
 * @author deepankarbatra
 * 
 * This class implements the Function interface to transform an Address object into an AddressDto object.
 */
@Component
public class AddressEntityTransformer implements Function<Address, AddressDto> {

	/**
     * Transforms an Address object into an AddressDto object.
     *
     * @param address The Address object to be transformed.
     * @return The transformed AddressDto object.
     */
	@Override
	public AddressDto apply(Address address) {
		AddressDto addressDto = new AddressDto();
		addressDto.setAddressId(address.getAddressId());
		addressDto.setStreetName(address.getStreetName());
		addressDto.setCity(address.getCity());
		addressDto.setCountry(address.getCountry());
		addressDto.setLandmark(address.getLandmark());
		addressDto.setPhoneNumber(address.getPhoneNumber());
		addressDto.setPincode(String.valueOf(address.getPincode()));
		addressDto.setState(address.getState());
		
		return addressDto;
	}

}
