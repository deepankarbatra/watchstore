package com.nagarro.watchstore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.nagarro.watchstore.enums.WatchType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * Represents a watch in the watch store application. This class is used to
 * store information about a watch.
 * 
 * @author karan
 * @version 1.0
 */
@Data
@Entity
@Table(name = "watches")
@EntityListeners(AuditingEntityListener.class)
public class Watch {

	/**
	 * The model number of the watch.
	 */
	@Id
	private String modelNumber;

	/**
	 * The name of the watch.
	 */
	private String watchName;

	/**
	 * The brand of the watch.
	 */
	private String watchBrand;

	/**
	 * The type of the watch.
	 */
	@Enumerated(EnumType.STRING)
	private WatchType watchType;

	/**
	 * The quantity of the watch in stock.
	 */
	private int stockQuantity;

	/**
	 * The price of the watch.
	 */
	private BigDecimal price;

	/**
	 * The availability status of the watch.
	 */
	private boolean availableStatus;

	/**
	 * The date of arrival for the watch.
	 */
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date dateOfArrival;

	/**
	 * The list of images associated with the watch.
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<Image> images;

}
