package com.example.inventory.exception;

public class ResouceNotFoundExcpetion extends RuntimeException {

	private static final long serialVersionUID = -340522638455282327L;

	public ResouceNotFoundExcpetion() {
	}

	public ResouceNotFoundExcpetion(String message) {
		super(message);
	}

	public ResouceNotFoundExcpetion(Throwable cause) {
		super(cause);
	}

	public ResouceNotFoundExcpetion(String message, Throwable cause) {
		super(message, cause);
	}

	public ResouceNotFoundExcpetion(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
