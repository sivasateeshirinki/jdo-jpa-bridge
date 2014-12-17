package com.iss.datastore.exception;
/**
 * It will raise an exception if the Key already exists
 * @author Siva Sateesh Irinki
 * @version 1.0
 */
public class KeyViolationException extends Exception {
	public KeyViolationException(String s) {
		super(s);
	}
}
