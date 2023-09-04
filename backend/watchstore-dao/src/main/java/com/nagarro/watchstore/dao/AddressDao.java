package com.nagarro.watchstore.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.watchstore.entity.Address;

/**
 * @author deepankarbatra
 * 
 * This interface represents the data access object (DAO) for the Address entity.
 * It provides methods to perform CRUD operations and retrieve Address entities from the underlying database.
 */
@Repository
public interface AddressDao extends CrudRepository<Address, Long> {
	
	/**
     * Retrieves a list of addresses associated with the specified user ID.
     *
     * @param userId The user ID to retrieve addresses for.
     * @return A list of Address entities associated with the user ID.
     */
	List<Address> findByUserId(String userId);

	/**
     * Retrieves an Address entity based on the specified address ID.
     *
     * @param addressId The address ID to retrieve the Address entity for.
     * @return The Address entity associated with the address ID, or null if not found.
     */
	Address findByAddressId(long addressId);

	Optional<Address> findByAddressIdAndUserId(long id,String userId);
}
