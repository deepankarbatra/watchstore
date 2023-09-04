package com.nagarro.watchstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * This is a model class for Notification
 * 
 * @author mdsharif
 *
 */
public class Notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationId;

	private boolean seenByAdmin;

	private boolean seenByUser;

	private String notificationMessage;

	private String emailId;

	@ManyToOne
	private Watch watch;

}
