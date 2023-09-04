package com.nagarro.watchstore.dtotransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.WatchDto;
import com.nagarro.watchstore.entity.Image;
import com.nagarro.watchstore.entity.Watch;
import com.nagarro.watchstore.enums.WatchType;

/**
 * Transformer class that converts a WatchDto object to a Watch object.
 * @author karan
 */
@Component
public class WatchDtoTransformer implements Function<WatchDto, Watch> {

	/**
	 * Transforms a WatchDto object to a Watch object.
	 *
	 * @param watchDto the WatchDto object to be transformed
	 * @return the transformed Watch object
	 */
	@Override
	public Watch apply(WatchDto watchDto) {
		Watch watch = new Watch();
		List<Image> imageList = new ArrayList<>();
		watchDto.getImagePathList().forEach((imagePath) -> {
			Image image = new Image();
			image.setImagePath(imagePath);
			imageList.add(image);
		});
		watch.setModelNumber(watchDto.getModelNumber());
		watch.setWatchName(watchDto.getWatchName());
		watch.setWatchBrand(watchDto.getWatchBrand());
		watch.setPrice(watchDto.getPrice());
		watch.setStockQuantity(watchDto.getStockQuantity());
		watch.setWatchType(WatchType.typeOfWatch(watchDto.getWatchType()));
		if(watchDto.getStockQuantity()==0)
		{
			watch.setAvailableStatus(false);
		}
		else
		{
		watch.setAvailableStatus(watchDto.getAvailableStatus());
		}
		watch.setImages(imageList);
		return watch;
	}
}
