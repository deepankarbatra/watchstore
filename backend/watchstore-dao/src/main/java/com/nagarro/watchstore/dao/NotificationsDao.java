package com.nagarro.watchstore.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.watchstore.entity.Notifications;

/**
 * 
 * @author mdsharif
 *
 */
@Repository
public interface NotificationsDao extends JpaRepository<Notifications, Integer> {

	/**
	 * 
	 * @param emailId
	 * @param modelNumber
	 * @return list of notifications
	 */
	Optional<Notifications> findByEmailIdAndWatchModelNumber(String emailId, String modelNumber);

	/**
	 * 
	 * @param emailId
	 * @return list of notifications
	 */
	List<Notifications> findByEmailId(String emailId);

	/**
	 * 
	 * @param modelNumber
	 * @return list of notifications
	 */
	List<Notifications> findByWatchModelNumber(String modelNumber);

}
