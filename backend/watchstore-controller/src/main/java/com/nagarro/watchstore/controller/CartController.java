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

import com.nagarro.watchstore.dto.CartDto;
import com.nagarro.watchstore.dto.CartSaveDto;
import com.nagarro.watchstore.dto.CartUpdateDto;
import com.nagarro.watchstore.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The CartController interface provides endpoints for managing carts.
 * 
 * @author yogesh04
 * @version 1.0
 */
@CrossOrigin
@RequestMapping("/carts")
@PreAuthorize( "hasAuthority('CUSTOMER')")
@Tag(name = "Cart API")
public interface CartController {

	/**
	 * Retrieve a list of carts by user ID.
	 *
	 * @return a ResponseEntity containing a list of CartDto objects
	 */
	@Operation(summary = "Retrieve a list of carts")
	@GetMapping()
	ResponseEntity<List<CartDto>> getCartsByUserId();

	/**
	 * Create a new cart.
	 *
	 * @param cartDto the CartSaveDto object representing the cart to be created
	 * @return a ResponseEntity containing an ApiResponse indicating the status of
	 *         the operation
	 */
	@Operation(summary = "Create a new cart")
	@PostMapping
	ResponseEntity<ApiResponse> createCart(@RequestBody CartSaveDto cartDto);

	/**
	 * Update an existing cart.
	 *
	 * @param cartId        the ID of the cart to be updated
	 * @param cartUpdateDto the CartUpdateDto object representing the updated cart
	 *                      data
	 * @return a ResponseEntity containing an ApiResponse indicating the status of
	 *         the operation
	 */
	@Operation(summary = "Update an existing cart")
	@PutMapping("/{cartId}")
	ResponseEntity<ApiResponse> updateCart(@PathVariable String cartId, @RequestBody CartUpdateDto cartUpdateDto);

	/**
	 * Remove a cart by its ID.
	 *
	 * @param cartId the ID of the cart to be removed
	 * @return a ResponseEntity containing an ApiResponse indicating the status of
	 *         the operation
	 */
	@Operation(summary = "Remove a cart by its ID")
	@DeleteMapping("/{cartId}")
	ResponseEntity<ApiResponse> removeCartById(@PathVariable String cartId);

}
