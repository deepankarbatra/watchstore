package com.nagarro.watchstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.watchstore.entity.DeliveryAddress;

/**
 * The DeliveryAddressDao interface provides data access methods for the DeliveryAddress entity.
 * It extends the JpaRepository interface, providing CRUD operations for the DeliveryAddress entity
 * with the primary key of type Long.
 *
 * @author tushar01
 */
public interface DeliveryAddressDao extends JpaRepository<DeliveryAddress, Long> {

}
