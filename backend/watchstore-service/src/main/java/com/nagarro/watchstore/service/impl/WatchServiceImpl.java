package com.nagarro.watchstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.watchstore.dao.WatchDao;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.exception.BadRequestException;
import com.nagarro.watchstore.exception.NotFoundException;
import com.nagarro.watchstore.service.WatchService;


/**
 * Implementation of the WatchService interface that handles watch-related
 * endpoints.
 * 
 * @author karan
 * @version 1.0
 */

@Service
public class WatchServiceImpl implements WatchService {

	private WatchDao watchDao;

	@Autowired
	public WatchServiceImpl(WatchDao watchDao) {
		super();
		this.watchDao = watchDao;
	}

	@Override
	public Watch addWatch(Watch watch) {
		Optional<Watch> optionalWatch = watchDao.findById(watch.getModelNumber());
		if (optionalWatch.isPresent()) {
			throw new BadRequestException("Watch model",
					"Watch already exist with this model number try updating watch");
		}
		return watchDao.save(watch);
	}

	@Override
	public List<Watch> getallWatch() {
		List<Watch> watch = watchDao.findAll();
		if (watch.isEmpty()) {
			throw new NotFoundException("In get all watch", "Watches not found");
		} else {
			return watch;
		}
	}

	@Override
	public Watch getWatchByModel(String modelNumber) {
		Optional<Watch> optionalWatch = watchDao.findById(modelNumber);
		if (optionalWatch.isPresent()) {
			return optionalWatch.get();
		} else {
			throw new NotFoundException("In Get Watch By model", "Watch Not Found");
		}
	}

	@Override
	public Watch updateWatch(String modelNumber, Watch watch) {
		Optional<Watch> optionalWatch = watchDao.findById(modelNumber);
		if (optionalWatch.isPresent()) {
			Watch updateWatch = optionalWatch.get();
			updateWatch.setWatchBrand(watch.getWatchBrand());
			updateWatch.setPrice(watch.getPrice());
			updateWatch.setStockQuantity(watch.getStockQuantity());
			updateWatch.setWatchName(watch.getWatchName());
			updateWatch.setWatchType(watch.getWatchType());
			updateWatch.setAvailableStatus(watch.isAvailableStatus());
			updateWatch.setImages(watch.getImages());
			watchDao.save(updateWatch);
			return updateWatch;
		} else {
			throw new NotFoundException("In update watch", "Watch not found");
		}
	}

	@Override
	public List<Watch> searchWatch(String query) {
		List<Watch> watches = watchDao.searchWatch(query);
		if (watches.isEmpty()) {
			throw new NotFoundException("In search watch", "Watch not found");
		} else {
			return watches;
		}
	}

	@Override
	public List<String> getBrand() {
		List<String> brand = watchDao.getBrand();
		if (brand.isEmpty()) {
			throw new NotFoundException("In get brand", "Brands are not available");
		} else {
			return brand;
		}
	}



}
