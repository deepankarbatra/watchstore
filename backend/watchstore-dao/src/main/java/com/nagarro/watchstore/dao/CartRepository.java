package com.nagarro.watchstore.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.watchstore.entity.Cart;
import com.nagarro.watchstore.entity.Watch;

/**
 * The CartRepository interface provides CRUD operations for managing Cart
 * entities in the database.
 * 
 * @author yogesh04
 * @version 1.0
 */
@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

	/**
	 * Retrieve a list of carts by user ID.
	 *
	 * @param userId the user ID
	 * @return a list of Cart objects matching the user ID
	 */
	List<Cart> findByUserId(String userId);

	/**
	 * Retrieve a cart by user ID and watch.
	 *
	 * @param userId the user ID
	 * @param watch  the Watch object associated with the cart
	 * @return an Optional containing the Cart object matching the user ID and
	 *         watch, or an empty Optional if not found
	 */
	Optional<Cart> findByUserIdAndWatch(String userId, Watch watch);
}
