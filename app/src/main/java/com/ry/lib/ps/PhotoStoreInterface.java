package com.ry.lib.ps;

import static com.ry.lib.ps.utils.ParamCheckUtils.*;

import com.ry.lib.ps.core.CropPhotoOption;
import com.ry.lib.ps.core.PhotoStore;
import com.ry.lib.ps.core.PhotoStoreConfig;
import com.ry.lib.ps.core.TakePhotoListener;

import android.content.Context;
import android.content.Intent;

public class PhotoStoreInterface {
	
	private static final String MUST_CALL_INIT_MSG = "You must call init";
	
	private static PhotoStore sPhotoStore = null;
	private static boolean inited = false;
	
	public static void init(PhotoStoreConfig photoStoreConfig){
		instancePhotoStore(photoStoreConfig);
		inited = true;
	}
	public static void init(){
		instancePhotoStore(PhotoStoreConfig.createDefaultConfig());
		inited = true;
	}
	public static void destory(){
		sPhotoStore.destory();
		inited = false;
	}
	public static void requestTakePhoto(Context activityContext,TakePhotoListener takePhotoListener){
		requestTakePhoto(activityContext, takePhotoListener, CropPhotoOption.createDefault());
	}
	public static void requestTakePhoto(Context activityContext , TakePhotoListener takePhotoListener , CropPhotoOption cropPhotoOption){
		checkInitCalled();
		//sPhotoStore.requestTakePhoto(activityContext,takePhotoListener,cropPhotoOption);
	}
	public static void handActivityResult(Context activityContext , int requestCode,int resultCode , Intent data){
		checkInitCalled();
		sPhotoStore.handActivityResult(activityContext, requestCode, resultCode, data);
	}
	private static void checkInitCalled() {
		if(!inited){
			throw new RuntimeException(MUST_CALL_INIT_MSG);
		}
	}
	private static void instancePhotoStore(PhotoStoreConfig photoStoreConfig) {
		if(null == sPhotoStore){
			sPhotoStore = new PhotoStore(checkNotNull(photoStoreConfig));
		}
	}
}
