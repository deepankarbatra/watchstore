package com.nagarro.watchstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.watchstore.entity.Watch;

/**
 * Repository interface for managing watch data. Provides CRUD operations for
 * the Watch entity.
 * @author karan 
 *
 */
@Repository
public interface WatchDao extends JpaRepository<Watch, String> {

	/**
	 * Searches for watches based on the provided query string.
	 *
	 * @param query the search query
	 * @return a list of watches matching the query
	 */
	@Query("select w from Watch w where w.watchName LIKE CONCAT('%',:query, '%') or w.modelNumber LIKE CONCAT('%',:query, '%') or w.watchType LIKE CONCAT('%',:query, '%')or w.watchBrand LIKE CONCAT('%',:query, '%')")
	List<Watch> searchWatch(String query);

	/**
	 * Retrieves a list of distinct watch brands.
	 *
	 * @return a list of distinct watch brands
	 */
	@Query("select distinct(watchBrand) from Watch")
	List<String> getBrand();
}
