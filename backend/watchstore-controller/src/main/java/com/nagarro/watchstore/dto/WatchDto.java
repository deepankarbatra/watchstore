package com.nagarro.watchstore.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * Represents a data transfer object for a watch.
 * 
 * @author karan
 */
@Data
public class WatchDto {

	/**
	 * The model number of the watch.
	 */
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
	private String watchType;

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
	private Boolean availableStatus;

	/**
	 * The list of image paths associated with the watch.
	 */
	private List<String> imagePathList;

}
