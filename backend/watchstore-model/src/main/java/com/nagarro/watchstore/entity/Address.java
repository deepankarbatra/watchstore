package com.nagarro.watchstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author deepankarbatra
 * 
 * Represents an address entity.
 */
@Entity
@Table(name = "address")
@Data
public class Address {

	/**
     * The unique identifier of the address.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long addressId;
	
	/**
     * The street name of the address.
     */
	private String streetName;
	
	/**
     * The city of the address.
     */
	private String city;
	
	/**
     * The country of the address.
     */
	private String country;
	
	/**
     * The state of the address.
     */
	private String state;
	
	/**
     * The pin code of the address.
     */
	private int pincode;
	
	/**
     * The landmark of the address.
     */
	private String landmark;
	
	/**
     * The phone number associated with the address.
     */
	private String phoneNumber;
	
	/**
     * The user ID associated with the address.
     */
	private String userId;
}
