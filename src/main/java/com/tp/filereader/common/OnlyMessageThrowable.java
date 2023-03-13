package com.tp.filereader.common;

public class OnlyMessageThrowable extends Throwable {

	public OnlyMessageThrowable(String message) {
		super(message, null, false, false);
	}
}