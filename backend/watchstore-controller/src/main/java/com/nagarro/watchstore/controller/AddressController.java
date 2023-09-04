package com.nagarro.watchstore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nagarro.watchstore.dto.AddressDto;
import com.nagarro.watchstore.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * @author deepankarbatra
 * 
 * 
 * This interface defines the endpoints for managing addresses. It
 * provides methods for saving, retrieving, updating, and deleting
 * addresses. All endpoints are prefixed with "/addresses".
 *
 */
@CrossOrigin
@RequestMapping("/addresses")
@PreAuthorize("hasAuthority('CUSTOMER')")
@Tag(name = "Addresses")
public interface AddressController {

	/**
	 * Saves a new address.
	 *
	 * @param addressDto The AddressSaveDto object containing the address details.
	 * @return A ResponseEntity containing the ApiResponse indicating the status of
	 *         the save operation.
	 */
	@Operation(summary = "Save a new address")
	@PostMapping
	ResponseEntity<ApiResponse> save(@RequestBody AddressDto addressDto);

	/**
	 * Retrieves a list of addresses for a given user ID.
	 *
	 * @return A ResponseEntity containing a list of AddressDto objects
	 *         representing the addresses.
	 */
	@Operation(summary = "Retrieve a list of addresses of a user")
	@GetMapping
	ResponseEntity<List<AddressDto>> get();

	/**
	 * Updates an existing address identified by the address ID.
	 *
	 * @param address   The AddressDto object containing the updated address
	 *                  details.
	 * @param addressId The ID of the address to be updated.
	 * @return A ResponseEntity containing the ApiResponse indicating the status of
	 *         the update operation.
	 */

	@Operation(summary = "Update an existing address")
	@PutMapping("/{addressId}")
	ResponseEntity<ApiResponse> update(@RequestBody AddressDto address, @PathVariable long addressId);

	/**
	 * Deletes an address identified by the address ID.
	 *
	 * @param addressId The ID of the address to be deleted.
	 * @return A ResponseEntity containing the ApiResponse indicating the status of
	 *         the delete operation.
	 */

	@Operation(summary = "Delete an address")
	@DeleteMapping("/{addressId}")
	ResponseEntity<ApiResponse> delete(@PathVariable long addressId);

}
