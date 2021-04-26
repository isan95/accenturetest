package io.github.isan95.accenture.exception;

public class NotStockException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3210828931972850325L;
	
	public NotStockException() {
		super();
	}
	
	public NotStockException(final String message) {
		super(message);
	}

}
