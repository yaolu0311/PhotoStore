package com.ry.lib.ps.core;

import com.ry.lib.ps.exception.NoSdcardException;
import com.ry.lib.ps.exception.RequestTakePhotoException;
import com.ry.lib.ps.exception.TakePhotoException;
import static com.ry.lib.ps.utils.ParamCheckUtils.*;
import java.io.File;
import java.lang.ref.WeakReference;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PhotoStore {

	public static final String TAG = "PhotoStore";

	public static final int REQUEST_CODE_GETIMAGE_BYCROP = 0x0005;
	public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 0x0006;
	public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0x0007;
	
	public static final String CHOOSER_HINT_MSG = "选择图片";
	public static final String NO_SD_HINT_TOAST_MSG = "没有挂载SD卡";

	private PhotoStoreConfig mPhotoStoreConfig;
	private String currentProcessRawPhotoPath;
	private String currentProcessCropPhotoPath;
	private FileManager fileManager;
	
	/**
	 * 这个对象会持有activity的引用，使用软引用防止activity不会被回收
	 */
	private WeakReference<TakePhotoListener> listenerReference;
	

	public PhotoStore(PhotoStoreConfig photoStoreConfig) {
		this.mPhotoStoreConfig = photoStoreConfig;
		this.fileManager = new FileManager(mPhotoStoreConfig);
	}

	public void requestTakePhoto(Context activityContext ,TakePhotoListener takePhotoListener) {

		Activity activity = checkContextIsActivity(activityContext);
		if (!fileManager.checkSdcard()) {
			takePhotoListener.onFailure(new NoSdcardException());
		}
		putTakePhotoListener(takePhotoListener);
		String rawPhotoFilePath = fileManager.getRawPhotoFileUri();
		setCurrentProcessRawPhotoPath(rawPhotoFilePath);
		requestSystemCamera(activity, Uri.fromFile(new File(rawPhotoFilePath)));
	}
	
	public void handActivityResult(Context activityContext,int requestCode,int resultCode,Intent data){
		
		if(resultCode != Activity.RESULT_OK){
			return;
		}
		if(requestCode == REQUEST_CODE_GETIMAGE_BYCAMERA){
			requestCropPhoto(checkContextIsActivity(activityContext),data);
		}
		if(requestCode == REQUEST_CODE_GETIMAGE_BYSDCARD){
			callbackTakePhotoComplete();
		}
	}


	private void requestCropPhoto(Activity activity, Intent data) {
		Uri uri = createCropPhotoUri(data);
		String cropPhotoOutPutPath = fileManager.getCropPhotoFilePath();
		Intent intent = IntentFactory.createCorpPhotoIntent(mPhotoStoreConfig,uri,cropPhotoOutPutPath);
		setCurrentProcessCropPhotoPath(cropPhotoOutPutPath);
        activity.startActivityForResult(intent,REQUEST_CODE_GETIMAGE_BYSDCARD);
	}
	
	private Uri createCropPhotoUri(Intent data) {
		if(null == data || null == data.getData()){
			return Uri.fromFile(new File(currentProcessRawPhotoPath));
		}
		return data.getData();
	}

	public void destory() {
		//暂时不知道做什么:p
	}
	
	public void setCurrentProcessRawPhotoPath(String currentProcessRawPhotoPath) {
		this.currentProcessRawPhotoPath = currentProcessRawPhotoPath;
	}
	private void setCurrentProcessCropPhotoPath(String currentProcessCropPhotoPath) {
		this.currentProcessCropPhotoPath = currentProcessCropPhotoPath;
	}
	private void requestSystemCamera(Activity activityContext, Uri uri) {
		try{
			activityContext.startActivityForResult(IntentFactory.getTakePhotoIntent(uri), REQUEST_CODE_GETIMAGE_BYCAMERA);
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
			callbackTakePhoto(new RequestTakePhotoException());
		}
	}
	private void putTakePhotoListener(TakePhotoListener takePhotoListener){
		listenerReference = new WeakReference<TakePhotoListener>(takePhotoListener);
	}
	private TakePhotoListener getTakePhotoListener(){
		if(null == listenerReference){
			return null;
		}
		return listenerReference.get();
	}
	private void callbackTakePhotoComplete(){
		TakePhotoListener takePhotoListener = getTakePhotoListener();
		if(null != takePhotoListener){
			takePhotoListener.onComplete(currentProcessRawPhotoPath, currentProcessCropPhotoPath);
		}
	}
	private void callbackTakePhoto(TakePhotoException exception){
		TakePhotoListener takePhotoListener = getTakePhotoListener();
		if(null != takePhotoListener){
			takePhotoListener.onFailure(exception);
		}
	}
}
