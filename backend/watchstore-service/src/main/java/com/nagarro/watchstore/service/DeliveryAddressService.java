package com.nagarro.watchstore.service;

import java.util.Optional;

import com.nagarro.watchstore.entity.DeliveryAddress;

/**
 * The DeliveryAddressService interface provides methods for managing delivery addresses.
 *
 * @author tushar01
 */
public interface DeliveryAddressService {

    /**
     * Retrieves a delivery address by its ID.
     *
     * @param id the ID of the delivery address to retrieve
     * @return an Optional containing the delivery address if found, or an empty Optional if not found
     */
    Optional<DeliveryAddress> findByAddressId(long id);

    /**
     * Saves a delivery address.
     *
     * @param deliveryAddress the delivery address to be saved
     * @return the saved delivery address
     */
    DeliveryAddress save(DeliveryAddress deliveryAddress);
}
