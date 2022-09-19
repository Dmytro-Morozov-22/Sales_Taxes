package com.ua.itemis.sales_taxes.exception;

/**
 * This class is a custom exception, and the program throws it when the chosen file's contents are wrong
 * @author Dmytro Morozov
 */
@SuppressWarnings("serial")
public class WrongFileContentException extends RuntimeException {

	public WrongFileContentException(String errorMessage) {
		super(errorMessage);
	}
	
}
