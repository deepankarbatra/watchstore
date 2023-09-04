package com.nagarro.watchstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.watchstore.constants.Constant;
import com.nagarro.watchstore.dao.NotificationsDao;
import com.nagarro.watchstore.dao.WatchDao;
import com.nagarro.watchstore.entity.Notifications;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.exception.AlreadyInStockException;
import com.nagarro.watchstore.exception.NotFoundException;
import com.nagarro.watchstore.service.NotificationService;


/**
 * This class have all the business logics for notification service
 * 
 * @author mdsharif
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	private NotificationsDao notificationDao;
	private WatchDao watchDao;

	@Autowired
	public NotificationServiceImpl(NotificationsDao notificationsDao, WatchDao watchDao) {
		this.notificationDao = notificationsDao;
		this.watchDao = watchDao;

	}

	@Override
	public String saveNotification(String modelNumber, String currentUserEmail) {
		Watch watch = watchDao.findById(modelNumber)
				.orElseThrow(() -> new NotFoundException(Constant.MODEL_NUMBER, Constant.NO_WATCH + modelNumber));

		if (watch.isAvailableStatus()) {

			throw new AlreadyInStockException(Constant.MODEL_NUMBER, Constant.ALREADY_IN_STOCK);
		}

		Optional<Notifications> findByEmailIdAndModelId = notificationDao
				.findByEmailIdAndWatchModelNumber(currentUserEmail, modelNumber);

		findByEmailIdAndModelId.ifPresentOrElse(notification -> {
			if (notification.isSeenByUser()) {
				throw new AlreadyInStockException(Constant.MODEL_NUMBER, Constant.NOTIFICATION_EXISTS);
			} else {
				notification.setSeenByUser(true);
				notification.setSeenByAdmin(false);
				notification.setEmailId(currentUserEmail);
				notification.setNotificationMessage(
						Constant.ADD + notification.getWatch().getWatchName() + Constant.IN_STOCK);
				notification.setWatch(watch);
				notificationDao.save(notification);
			}
		}, () -> {
			Notifications notifications = new Notifications();
			notifications.setSeenByUser(true);
			notifications.setSeenByAdmin(false);
			notifications.setEmailId(currentUserEmail);
			notifications
					.setNotificationMessage(Constant.ADD + watch.getWatchName() + Constant.IN_STOCK);
			notifications.setWatch(watch);
			notificationDao.save(notifications);
		});

		return Constant.NOTIFICATION_SENT_SUCCESS;
	}

	@Override
	public String updateNotification(String modelNumber) {

		List<Notifications> findByModelNumber = notificationDao.findByWatchModelNumber(modelNumber);

		findByModelNumber.stream()
				.filter(notification -> notification.getWatch().isAvailableStatus() && !notification.isSeenByAdmin())
				.forEach(notification -> {
					notification
							.setNotificationMessage(notification.getWatch().getWatchName() + Constant.ADDED_IN_STOCK);
					notification.setSeenByAdmin(true);
					notification.setSeenByUser(false);
					notificationDao.save(notification);
				});

		return Constant.NOTIFICATION_UPDATE_SUCCESS;
	}

	@Override
	public List<Notifications> getNotificationByUserId(String emailId) {
		List<Notifications> findbyuserId = notificationDao.findByEmailId(emailId);

		return findbyuserId;
	}

	@Override
	public List<Notifications> getAllNotifications() {
		List<Notifications> allNotifications = notificationDao.findAll();
		return allNotifications;
	}

}
