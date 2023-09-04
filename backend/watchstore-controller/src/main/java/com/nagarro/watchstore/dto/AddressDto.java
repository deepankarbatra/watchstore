package com.nagarro.watchstore.dto;

import lombok.Data;

/**
 * @author deepankarbatra
 * 
 *   This class represents a data transfer object (DTO) for posting /
 *   updating address information. It contains the details of an address
 *   for update purposes.
 */
@Data
public class AddressDto {
	
	private long addressId;
	
	private String streetName;

	private String city;

	private String country;

	private String state;

	private String pincode;

	private String landmark;

	private String phoneNumber;
}
