package com.nagarro.watchstore.entitytransformer;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nagarro.watchstore.dto.WatchDto;
import com.nagarro.watchstore.entity.Watch;

@Component
public class WatchTransformer implements Function<Watch,WatchDto> {

	@Override
	public WatchDto apply(Watch watch) {
		WatchDto watchDto=new WatchDto();
		watchDto.setAvailableStatus(watch.isAvailableStatus());
		watchDto.setImagePathList(watch.getImages().stream().map(image->image.getImagePath()).collect(Collectors.toList()));
		watchDto.setModelNumber(watch.getModelNumber());
		watchDto.setPrice(watch.getPrice());
		watchDto.setStockQuantity(watch.getStockQuantity());
		watchDto.setWatchBrand(watch.getWatchBrand());
		watchDto.setWatchName(watch.getWatchName());
		watchDto.setWatchType(watch.getWatchType().toString());
		return watchDto;
	}

}
