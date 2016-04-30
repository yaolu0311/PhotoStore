package com.ry.lib.ps.core;

import com.ry.lib.ps.exception.TakePhotoException;

public interface TakePhotoListener {
	void onComplete(String rawPhotoPath, String corpPhotoPath);
	void onFailure(TakePhotoException exception);
}
