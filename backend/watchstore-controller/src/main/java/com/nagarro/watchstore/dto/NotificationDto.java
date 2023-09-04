package com.nagarro.watchstore.dto;


import lombok.Data;


/**
 * 
 * @author mdsharif
 *
 */
@Data
public class NotificationDto {
	
	private int notificationId;

	private boolean seenByAdmin;

	private boolean seenByUser;

	private String notificationMessage;

	private String emailId;
	
	private WatchDto watch;

}
