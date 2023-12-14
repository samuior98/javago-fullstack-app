package com.generation.javago.controller.util;

@SuppressWarnings("serial")
public class ForbiddenOperationException extends RuntimeException
{
	public ForbiddenOperationException(String message)
	{
		super(message);
	}
}
