package com.nagarro.watchstore.service;

import java.util.List;

import com.nagarro.watchstore.entity.Order;
import com.nagarro.watchstore.enums.OrderStatus;
import com.nagarro.watchstore.enums.UserRole;

/**
 * The OrderService interface provides methods for managing orders.
 * 
 * @author tushar01
 */
public interface OrderService {
	
	 /**
     * Adds a new order.
	 * @param watchModel        the model of the watch
	 * @param deliveryAddressId the ID of the delivery address for the order
	 * @param userEmailId       the email ID of the user placing the order
     * @param quantity          the quantity of watches
     * @return the added order
     */
	Order add(final String watchModel, final long deliveryAddressId, final String userEmailId, final int quantity);	
	
	/**
     * Retrieves all orders if user has admin access otherwise 
     * retrieves all orders associated with the given user.
     *
     * @param userEmailId the email ID of the user
     * @param role the role of the user
     * @return a list of orders
     */
	List<Order> findAllByUser(final String userEmailId, final UserRole role);

	 /**
     * Updates the status of an order.
     *
     * @param id           the ID of the order to update
     * @param orderStatus  the new order status
     * @return true if the order status was successfully updated, otherwise throws BadRequestException
     */
	boolean updateOrderStatus(final String id, final OrderStatus orderStatus);
	


	/**
	 * Retrieves an order by its ID based on user access level.
	 * If the user has access as a regular user, the method will retrieve their own order
	 * based on the provided order ID. If the user has access as an admin,
	 * they can retrieve an order by its ID for any user.
	 * 
     * @param orderId     the order ID
     * @param userEmailId the email ID of the user
     * @param role the role of the user
     * @return the tracked order if found, or throws NotFoundException if not found
     */
	Order findById(final String orderId, final String userEmailId, final UserRole role);

}
