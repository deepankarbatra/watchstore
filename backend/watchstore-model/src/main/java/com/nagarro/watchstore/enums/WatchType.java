package com.nagarro.watchstore.enums;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import com.nagarro.watchstore.exception.InvalidWatchTypeException;

import java.util.Arrays;

/**
 * Represents the type of a watch.
 * 
 * @author karan
 */
public enum WatchType {
	AUTOMATIC("AUTOMATIC"), QUARTZ("QUARTZ"), SOLAR("SOLAR"), ANALOGUE("ANALOGUE"), DIGITAL("DIGITAL"),
	CHRONOGRAPH("CHRONOGRAPH"), HYBRID("HYBRID");

	private String value;

	WatchType(String value) {
		this.value = value;
	}

	/**
	 * Returns the WatchType enum constant that matches the specified watch type
	 * value.
	 *
	 * @param watchType the watch type value to match
	 * @return the matching WatchType enum constant
	 * @throws InvalidWatchTypeException if the watch type value is invalid
	 */
	public static WatchType typeOfWatch(String watchType) {
		return Arrays.stream(values()).filter(status -> status.value.equalsIgnoreCase(watchType)).findFirst()
				.orElseThrow(() -> new InvalidWatchTypeException("Invalid type", HttpStatus.BAD_REQUEST, "Watch Type"));
	}

	public static String getValue(WatchType watchType) {
		return watchType.value;
	}
}
