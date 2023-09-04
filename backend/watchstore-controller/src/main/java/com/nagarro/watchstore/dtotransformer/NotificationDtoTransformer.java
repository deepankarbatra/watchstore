package com.nagarro.watchstore.dtotransformer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.NotificationDto;
import com.nagarro.watchstore.dto.WatchDto;
import com.nagarro.watchstore.entity.Image;
import com.nagarro.watchstore.entity.Notifications;
import com.nagarro.watchstore.enums.WatchType;

/**
 * In this class Notifications are converted to NotificationDto
 * 
 * @author mdsharif
 *
 */
@Component
public class NotificationDtoTransformer implements Function<Notifications, NotificationDto> {

	/**
	 * In this method we have converted notification to notificationDto
	 */
	@Override
	public NotificationDto apply(Notifications notifications) {
		NotificationDto notificationDto = new NotificationDto();
		notificationDto.setNotificationId(notifications.getNotificationId());
		notificationDto.setSeenByAdmin(notifications.isSeenByAdmin());
		notificationDto.setSeenByUser(notifications.isSeenByUser());
		notificationDto.setNotificationMessage(notifications.getNotificationMessage());
		notificationDto.setEmailId(notifications.getEmailId());

		WatchDto watchDto = new WatchDto();


		watchDto.setModelNumber(notifications.getWatch().getModelNumber());
		watchDto.setWatchName(notifications.getWatch().getWatchName());
		watchDto.setWatchBrand(notifications.getWatch().getWatchBrand());
		watchDto.setPrice(notifications.getWatch().getPrice());
		watchDto.setStockQuantity(notifications.getWatch().getStockQuantity());
		watchDto.setWatchType(WatchType.getValue(notifications.getWatch().getWatchType()));
		watchDto.setAvailableStatus(notifications.getWatch().isAvailableStatus());
		List<Image> imageList = notifications.getWatch().getImages();
		List<String> imagePathList = imageList.stream().map(Image::getImagePath).collect(Collectors.toList());
		watchDto.setImagePathList(imagePathList);

		notificationDto.setWatch(watchDto);

		return notificationDto;
	}

}
