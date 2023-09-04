package com.nagarro.watchstore.service;

import java.util.List;
import java.util.Optional;

import com.nagarro.watchstore.entity.Watch;

/**
 * Service interface for managing watch-related endpoints. This interface
 * defines the contract for handling watch-related operations.
 * 
 * @author karan
 * @version 1.0
 */
public interface WatchService {
	
	//Watch findByModelNumber(String watchModel);

	/**
	 * Add a new Watch.
	 *
	 * @param watch The Watch object to be added.
	 * @return The added Watch object.
	 */
	public Watch addWatch(Watch watch);

	/**
	 * Retrieve all watches.
	 *
	 * @return A list of all watches.
	 */
	public List<Watch> getallWatch();

	/**
	 * Retrieve a Watch By Model Number.
	 *
	 * @param modelNumber The model number of the watch.
	 * @return The Watch object if found, otherwise null.
	 */
	public Watch getWatchByModel(String modelNumber);

	/**
	 * Update the existing Watch details.
	 *
	 * @param modelNumber The model number of the watch.
	 * @param watch       The updated Watch object.
	 * @return The updated Watch object.
	 */
	public Watch updateWatch(String modelNumber, Watch watch);

	/**
	 * Search watches based on the provided query.
	 *
	 * @param query The keyword to search.
	 * @return A list of watches matching the query.
	 */
	public List<Watch> searchWatch(String query);

	/**
	 * Retrieve all watch brands.
	 *
	 * @return A list of distinct watch brands.
	 */
	public List<String> getBrand();

}
