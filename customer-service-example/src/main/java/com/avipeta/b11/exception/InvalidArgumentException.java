package com.avipeta.b11.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidArgumentException  extends Exception {
	 private static final long serialVersionUID = 1L;
	    public InvalidArgumentException(String message){
	        super(message);
	    }
	}
