package com.generation.javago.controller.util;

@SuppressWarnings("serial")
public class InvalidEntityException extends RuntimeException
{
	public InvalidEntityException(String message)
	{
		super(message);
	}
}
