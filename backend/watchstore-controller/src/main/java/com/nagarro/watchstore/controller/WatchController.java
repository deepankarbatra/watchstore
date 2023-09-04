package com.nagarro.watchstore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nagarro.watchstore.dto.WatchDto;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller interface for managing watch-related endpoints. This interface
 * defines the contract for handling watch-related operations. The base path for
 * all endpoints is "/watches".
 * 
 * @author karan
 * @version 1.0
 */
@RequestMapping("/watches")
@Tag(name = "Watch API")
public interface WatchController {

	/**
	 * Retrieve all watches.
	 *
	 * @return A ResponseEntity containing the list of watches.
	 */
	@Operation(summary = "Retrieve All Watches")
	@GetMapping
	public ResponseEntity<List<Watch>> getAllWatches();

	/**
	 * Add a new watch.
	 *
	 * @param watchDto The WatchDto object containing the details of the watch.
	 * @return A ResponseEntity with the response message indicating the watch added
	 *         successfully.
	 */
	@Operation(summary = "Save a new Watch")
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse> addWatch(@RequestBody WatchDto watchDto);

	/**
	 * Retrieve a watch by its model number.
	 *
	 * @param modelNumber The model number of the watch.
	 * @return A ResponseEntity containing the watch if found, or an appropriate
	 *         error response if not found.
	 */
	@Operation(summary = "Retrieve a watch by its model number")
	@GetMapping("/{modelNumber}")
	public ResponseEntity<Watch> getWatchByModel(@PathVariable("modelNumber") String modelNumber);

	/**
	 * Update the existing watch details.
	 *
	 * @param modelNumber    The model number of the watch.
	 * @param watchUpdateDto The WatchUpdateDto object containing the details of the
	 *                       watch to be updated.
	 * @return A ResponseEntity with the response message indicating the watch
	 *         updated successfully.
	 */
	@Operation(summary = "Update Exisiting watch")
	@PutMapping("/{modelNumber}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse> updateWatch(@PathVariable String modelNumber,
			@RequestBody  WatchDto watchDto);

	/**
	 * Search watches based on the provided query.
	 *
	 * @param query The keyword to search.
	 * @return A ResponseEntity containing the list of watches.
	 */
	@Operation(summary = "Search Watch")
	@GetMapping(params = "query")
	public ResponseEntity<List<Watch>> searchWatch(@RequestParam("query") String query);

	/**
	 * Retrieve All brands
	 *
	 * @return A ResponseEntity containing the list of brand.
	 */
	@Operation(summary = "Retreve all brands")
	@GetMapping("/brands")
	public ResponseEntity<List<String>> getBrand();

}
