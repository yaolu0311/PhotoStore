package com.ry.lib.ps.exception;

public class NoSdcardException extends TakePhotoException{
	
	public NoSdcardException() {
		super("当前无可用SD卡");
	}
}
