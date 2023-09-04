package com.nagarro.watchstore.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nagarro.watchstore.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * The Order entity represents an order in the watch store.
 *
 * @author tushar01
 */
@Data
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Setter(value = AccessLevel.NONE)
	private String id;
	
	
	private int quantiy;
	

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private  BigDecimal amount;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime timestamp;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime statusTimestamp;

	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Watch watch;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private DeliveryAddress deliveryAddress;

}
