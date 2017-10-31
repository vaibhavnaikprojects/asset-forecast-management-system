package com.zensar.afnls.exception;

public class AssetTrackNotDeletedException extends Exception{

	public AssetTrackNotDeletedException() {
		super();
	}

	public AssetTrackNotDeletedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AssetTrackNotDeletedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssetTrackNotDeletedException(String message) {
		super(message);
	}

	public AssetTrackNotDeletedException(Throwable cause) {
		super(cause);
	}

}
