package com.nagarro.watchstore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nagarro.watchstore.dto.OrderDto;
import com.nagarro.watchstore.dto.OrderSaveDto;
import com.nagarro.watchstore.dto.OrderSummaryDto;
import com.nagarro.watchstore.dto.StatusDto;
import com.nagarro.watchstore.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The OrderController interface defines the REST API endpoints for managing orders.
 * 
 * @author tushar01
 */
@CrossOrigin
@RequestMapping("/orders")
@Tag(name = "Orders API")
public interface OrderController {

    /**
     * Book a new order based on the provided place order DTO.
     *
     * @param orderSaveDto    the order save DTO containing the order details
     * @return a ResponseEntity with the API response for the successful saved order
     */
	@Operation(summary = "Book an order")
    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    ResponseEntity<ApiResponse> book(@RequestBody final OrderSaveDto orderSaveDto);

    /**
     * Retrieves all orders.

     * @return a ResponseEntity with a list of order summary DTOs representing all orders
     */
	@Operation(summary = "Retrieves all orders.",description = "Retrieves all orders if user has admin access otherwise \r\n"
			+ "retrieves all orders associated with the given user.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    ResponseEntity<List<OrderSummaryDto>> fetchAll();


    /**
     * Retrieves an order by its ID.
     *
     * @param orderId    the ID of the order
     * @return a ResponseEntity with the order DTO representing the order
     */

	@Operation(summary = "Retrieves an order by its ID",description = "Retrieves an order by its ID based on user access level.\r\n"
			+ "	 If the user has access as a regular user, the method will retrieve their own order\r\n"
			+ "	 based on the provided order ID. If the user has access as an admin,\r\n"
			+ "	 they can retrieve an order by its ID for any user.")
	@GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    ResponseEntity<OrderDto> fetchById(@PathVariable("id") final String orderId);


    /**
     * Updates the status of an order with the provided ID.
     *
     * @param id                 the ID of the order
     * @param statusDto     the status DTO containing the new order status
     * @return a ResponseEntity with the API response for the successful updation of order status
     */
	@Operation(summary = "Updates the status of an order with the provided ID")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable("id")final String id, @RequestBody final StatusDto statusDto);
}
