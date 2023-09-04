package com.nagarro.watchstore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nagarro.watchstore.dto.NotificationDto;
import com.nagarro.watchstore.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * The Notification interface provides endpoints for managing notifications. 
 * @author mdsharif
 *
 */
@CrossOrigin
@RequestMapping("/notifications")
@Tag(name = "Notification API")
public interface NotificationController {

	/**
	 * 
	 * @param modelNumber
	 * @param authentication
	 * @return New ApiResponse containing notification sent successfully with 200
	 *         status
	 */
	@Operation(summary = "Sent Notification for out of stock watch")
	@PostMapping("/{modelNumber}")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<ApiResponse> saveNotification(@PathVariable String modelNumber,
			Authentication authentication);

	/**
	 * 
	 * @param modelNumber
	 * @return This end point is not exposed to any user.This API will be called
	 *         when admin will update the watch
	 */
	public ResponseEntity<ApiResponse> updateNotification(@PathVariable String modelNumber);

	/**
	 * 
	 * @param emailId
	 * @return List of all notifications by user
	 */
	@Operation(summary = "Give list of all notification sent by user")
	@GetMapping("/{emailId}")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<List<NotificationDto>> getAllNotificationByUserEmailId(@PathVariable String emailId);

	/**
	 * 
	 * @return List of all notification present in db
	 */
	@Operation(summary = "Give list of all notifications")
	@GetMapping()
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<NotificationDto>> getAllNotifications();

}
