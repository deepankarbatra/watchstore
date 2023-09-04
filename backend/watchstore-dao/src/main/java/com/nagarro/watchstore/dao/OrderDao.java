package com.nagarro.watchstore.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.watchstore.entity.Order;

/**
 * The OrderDao interface provides data access methods for the Order entity.
 * It extends the JpaRepository interface, providing CRUD operations for the Order entity
 * with the primary key of type String.
 *
 * @author tushar01
 */
public interface OrderDao extends JpaRepository<Order, String> {

	/**
     * Retrieves all orders in descending order based on the timestamp.
     *
     * @return a list of orders ordered by timestamp in descending order
     */
	List<Order> findAllByOrderByTimestampDesc();
	
	 /**
     * Retrieves all orders associated with the given user email ID in descending order based on the timestamp.
     *
     * @param userEmailId the email ID of the user
     * @return a list of orders associated with the user email ID ordered by timestamp in descending order
     */
	List<Order> findByUserEmailIdOrderByTimestampDesc(final String userEmailId);

	 /**
     * Retrieves an order by its ID and user email ID.
     *
     * @param id          the ID of the order
     * @param userEmailId the email ID of the user
     * @return an Optional containing the order if found, or empty if not found
     */
	Optional<Order> findByIdAndUserEmailId(final String id,final String userEmailId);
}
