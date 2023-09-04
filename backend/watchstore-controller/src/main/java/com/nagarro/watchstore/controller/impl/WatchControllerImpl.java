package com.nagarro.watchstore.controller.impl;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.watchstore.controller.WatchController;
import com.nagarro.watchstore.dto.WatchDto;

import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.response.ApiResponse;
import com.nagarro.watchstore.service.NotificationService;
import com.nagarro.watchstore.service.WatchService;

/**
 * Implementation of the WatchController interface that handles watch-related
 * endpoints.
 * 
 * @author karan
 * @version 1.0
 */
@RestController
public class WatchControllerImpl implements WatchController {

	private WatchService watchService;

	private NotificationService notificationService;

	private Function<WatchDto, Watch> watchDtoTransformer;

	private Predicate<WatchDto> watchDtoValidator;

	private Predicate<WatchDto> watchUpdateDtoValidator;

	private static final Logger logger = LoggerFactory.getLogger(WatchControllerImpl.class);

	@Autowired
	public WatchControllerImpl(WatchService watchService, Function<WatchDto, Watch> watchDtoTransformer,
			Predicate<WatchDto> watchDtoValidator, Predicate<WatchDto> watchUpdateDtoValidator,
			NotificationService notificationService) {
		super();
		this.watchService = watchService;
		this.watchDtoTransformer = watchDtoTransformer;
		this.watchDtoValidator = watchDtoValidator;
		this.watchUpdateDtoValidator = watchUpdateDtoValidator;
		this.notificationService = notificationService;
	}

	@Override
	public ResponseEntity<List<Watch>> getAllWatches() {
		logger.info("Getting all watches");
		List<Watch> watches = watchService.getallWatch();
		return new ResponseEntity<>(watches, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> addWatch(WatchDto watchDto) {
		this.watchDtoValidator.test(watchDto);
		logger.info("saving Watch : {}", watchDto);
		Watch watch = watchDtoTransformer.apply(watchDto);
		watchService.addWatch(watch);

		ApiResponse customResponse = new ApiResponse("Watch added successfully");
		return new ResponseEntity<>(customResponse, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Watch> getWatchByModel(String modelNumber) {
		logger.info("Getting Watch for Model Number: {}", modelNumber);
		Watch watch = watchService.getWatchByModel(modelNumber);
		return new ResponseEntity<>(watch, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ApiResponse> updateWatch(String modelNumber, WatchDto watchDto) {
		logger.info("Updating watch with Model number: {}, Data: {}", modelNumber, watchDto);
		this.watchUpdateDtoValidator.test(watchDto);
		Watch watch = watchDtoTransformer.apply(watchDto);
		Watch updateWatch = watchService.updateWatch(modelNumber, watch);
		if (updateWatch.isAvailableStatus() == true) {
			notificationService.updateNotification(modelNumber);
		}
		ApiResponse customResponse = new ApiResponse("Watch updated successfully");
		return new ResponseEntity<>(customResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Watch>> searchWatch(String query) {
		logger.info("Searching watch with query: {}", query);
		List<Watch> watches = watchService.searchWatch(query);
		return new ResponseEntity<>(watches, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<String>> getBrand() {
		logger.info("Getting watch brands");
		List<String> brand = watchService.getBrand();
		return new ResponseEntity<>(brand, HttpStatus.OK);

	}

}
