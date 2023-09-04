package com.nagarro.watchstore.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dao.NotificationsDao;
import com.nagarro.watchstore.dao.WatchDao;
import com.nagarro.watchstore.entity.Notifications;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.exception.AlreadyInStockException;
import com.nagarro.watchstore.exception.NotFoundException;
import com.nagarro.watchstore.service.impl.NotificationServiceImpl;


@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

	@Mock
	private NotificationsDao notificationDao;

	@Mock
	private WatchDao watchDao;

	@InjectMocks
	private NotificationServiceImpl notificationService;

	@Test
	void testSaveNotificationSuccess() {
		String modelNumber = "123";
		String currentUserEmail = "Sharif@gmail.com";

		Watch watch = new Watch();
		watch.setModelNumber(modelNumber);
		watch.setAvailableStatus(false);

		when(watchDao.findById(modelNumber)).thenReturn(Optional.of(watch));
		when(notificationDao.findByEmailIdAndWatchModelNumber(currentUserEmail, modelNumber))
				.thenReturn(Optional.empty());

		String result = notificationService.saveNotification(modelNumber, currentUserEmail);

		verify(notificationDao).save(any(Notifications.class));
		assertEquals(Constant.NOTIFICATION_SENT_SUCCESS, result);
	}

	@Test
	void testSaveNotificationShouldThrowAlreadyInStockException() {
		String modelNumber = "123";
		String currentUserEmail = "sharif@gmail.com";

		Watch watch = new Watch();
		watch.setModelNumber(modelNumber);
		watch.setAvailableStatus(true);

		when(watchDao.findById(modelNumber)).thenReturn(Optional.of(watch));

		assertThrows(AlreadyInStockException.class, () -> {
			notificationService.saveNotification(modelNumber, currentUserEmail);
		});
	}

	@Test
	void testSaveNotificationShouldThrowNotFoundException() {
		String modelNumber = "111";
		String currentUserEmail = "sahil@gmail.com";

		when(watchDao.findById(modelNumber)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> {
			notificationService.saveNotification(modelNumber, currentUserEmail);
		});
	}

	@Test
	void testGetNotificationByUserIdSuccess() {
		String emailId = "sahil@gmail.com";

		List<Notifications> notificationsList = new ArrayList<>();
		notificationsList.add(new Notifications());
		notificationsList.add(new Notifications());

		when(notificationDao.findByEmailId(emailId)).thenReturn(notificationsList);

		List<Notifications> result = notificationService.getNotificationByUserId(emailId);

		assertEquals(notificationsList, result);
	}

	@Test
	void testGetAllNotificationsSuccess() {
		List<Notifications> allNotifications = new ArrayList<>();
		allNotifications.add(new Notifications());
		allNotifications.add(new Notifications());

		when(notificationDao.findAll()).thenReturn(allNotifications);

		List<Notifications> result = notificationService.getAllNotifications();

		assertEquals(allNotifications, result);
	}
}
