package com.imp.saas.exception;

public class DatabaseCustomException extends RuntimeException {

	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 4657491283614455649L;

	public DatabaseCustomException(String msg) {
		super(msg);
	}

	public DatabaseCustomException(String msg, Throwable t) {
		super(msg, t);
	}

}
