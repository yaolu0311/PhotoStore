package com.ry.lib.ps.utils;

import android.app.Activity;
import android.content.Context;

public class ParamCheckUtils {
	
	public static <T> T checkNotNull(T reference) {
		if (reference == null) {
			throw new NullPointerException();
		}
		return reference;
	}

	public static <T> T checkNotNull(T reference, Object errorMessage) {
		if (reference == null) {
			throw new NullPointerException(String.valueOf(errorMessage));
		}
		return reference;
	}
	
	public static Activity checkContextIsActivity(Context context) {
		if (context == null) {
			throw new NullPointerException();
		}
		if(!(context instanceof Activity)){
			throw new IllegalArgumentException("Context is not activity");
		}
		return (Activity)context;
	}
}
