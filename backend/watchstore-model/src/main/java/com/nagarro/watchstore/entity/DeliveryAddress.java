package com.nagarro.watchstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * The DeliveryAddress entity represents the delivery address on which 
 * an order is placed in the watch store.
 * 
 * @author tushar01
 */
@Data
@Entity
public class DeliveryAddress {
	 @Id
	 private long id;
	 private String city;
	 private String streetName;
	 private String country;
	 private String state;
	 private int pincode;
	 private String landmark;
	 private String phoneNumber;

}
