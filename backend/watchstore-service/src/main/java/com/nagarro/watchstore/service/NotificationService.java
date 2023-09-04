package com.nagarro.watchstore.service;

import java.util.List;

import com.nagarro.watchstore.entity.Notifications;

/**
 * 
 * @author mdsharif
 *
 */
public interface NotificationService {
	
	/**
	 * @param modelNumber
	 * @param currentUserEmail 
	 * @return String message based on success
	 * @implNote This method is used to save notification by user whenever any watch
	 *           is out of stock
	 */
	public String saveNotification(String modelNumber, String currentUserEmail);
	
	/**
	 * @param modelNumber
	 * @return String message based on success
	 * @implNote This method is used to update notification by admin whenever any
	 *           watch is in stock
	 */
	public String updateNotification(String modelNumber);
	
	/**
	 * @param userId
	 * @return list of notifications
	 * @implNote This method is used to get list of all notifications of particular
	 *           user
	 */
	public List<Notifications> getNotificationByUserId(String emailId);
	
	/**
	 * @return list of all notifications
	 * @implNote This method is used to get the list of all the notifications
	 */
	public List<Notifications> getAllNotifications();

}
