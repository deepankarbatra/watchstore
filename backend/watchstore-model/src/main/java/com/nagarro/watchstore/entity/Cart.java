package com.nagarro.watchstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * The Cart class represents a shopping cart entity. 
 * It contains information about the cart ID, user ID, watch quantity, and the associated watch.
 * 
 * @author yogesh04
 * @version 1.0
 */
@Entity
@Data
@Table(name = "Carts")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cartId;
	private String userId;
	private int watchQty;
	@ManyToOne
	private Watch watch;
}
