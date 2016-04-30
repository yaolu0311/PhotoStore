package com.ry.lib.ps.core;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class IntentFactory {
	
	public static final String INTENT_TYPE_IMAGE = "image/*";
	public static final String INTENT_ACTION_PHOTO_CROP = "com.android.camera.action.CROP";
	
	public static final String EXTRA_KEY_OUTPUT = "output";
	public static final String EXTRA_KEY_CROP = "crop";
	public static final String EXTRA_KEY_ASPECTX = "aspectX";
	public static final String EXTRA_KEY_ASPECTY = "aspectY";
	public static final String EXTRA_KEY_OUTPUTX = "outputX";
	public static final String EXTRA_KEY_OUTPUTY = "outputY";
	public static final String EXTRA_KEY_SCALE = "scale";
	public static final String EXTRA_KEY_SCALEUPIFNEEDED = "scaleUpIfNeeded";
	public static final String EXTRA_KEY_NO_FACE_DETECTION = "noFaceDetection";
	
	public static final String TRUE = "true";

	public static Intent createCorpPhotoIntent(PhotoStoreConfig photoStoreConfig,Uri rawData,String outPutPath) {
		
		Intent intent = new Intent(INTENT_ACTION_PHOTO_CROP);
		intent.setDataAndType(rawData, INTENT_TYPE_IMAGE);
		intent.putExtra(EXTRA_KEY_OUTPUT, Uri.fromFile(new File(outPutPath)));
		intent.putExtra(EXTRA_KEY_CROP, TRUE);
		intent.putExtra(EXTRA_KEY_ASPECTX, photoStoreConfig.getCorpPhotoAspectX());
		intent.putExtra(EXTRA_KEY_ASPECTY, photoStoreConfig.getCorpPhotoAspectY());
		intent.putExtra(EXTRA_KEY_OUTPUTX, photoStoreConfig.getCorpPhotoOutPutX());
		intent.putExtra(EXTRA_KEY_OUTPUTY, photoStoreConfig.getCorpPhotoOutPutY());
		intent.putExtra(EXTRA_KEY_SCALE, true);
		intent.putExtra(EXTRA_KEY_NO_FACE_DETECTION, true);
		intent.putExtra(EXTRA_KEY_SCALEUPIFNEEDED, true);
		
		return intent;
	}
	
	public static Intent getCompatImagePickIntent() {
		if (Build.VERSION.SDK_INT < 19) {
			return getSdkLow19ImagePickIntent();
		} else {
			return getSdkMoreThan19ImagePickIntent();
		}
	}
	
	public static Intent getTakePhotoIntent(Uri uri){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		return intent;
	}

	private static Intent getSdkMoreThan19ImagePickIntent() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setData(Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType(INTENT_TYPE_IMAGE);
		return intent;
	}

	private static Intent getSdkLow19ImagePickIntent() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType(INTENT_TYPE_IMAGE);
		return intent;
	}

}
