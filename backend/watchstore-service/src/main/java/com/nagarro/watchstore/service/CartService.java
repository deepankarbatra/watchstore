package com.nagarro.watchstore.service;

import java.util.List;

import com.nagarro.watchstore.entity.Cart;

/**
 * The CartService interface provides methods for managing shopping carts.
 * 
 * @author yogesh04
 * @version 1.0
 */
public interface CartService {

	/**
	 * Retrieve carts by user ID.
	 *
	 * @param userId the user ID
	 * @return a list of Cart objects associated with the user ID
	 */
	List<Cart> retrieveCartByUserId(String userId);

	/**
	 * Add a cart for the specified user and watch model.
	 *
	 * @param userId     the user ID
	 * @param watchModel the model of the watch to add to the cart
	 * @return the Cart object representing the added cart
	 */
	Cart addCart(String userId, String watchModel);

	/**
	 * Delete a cart by its ID.
	 *
	 * @param cartId the ID of the cart to delete
	 */
	void deleteCartById(int cartId);

	/**
	 * Update the quantity of a cart.
	 *
	 * @param cartId   the ID of the cart to update
	 * @param watchQty the new quantity of the watch in the cart
	 * @return the Cart object representing the updated cart
	 */
	Cart updateCartQty(int cartId, int watchQty);
}
