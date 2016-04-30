package com.ry.lib.ps;

import com.ry.lib.ps.core.TakePhotoListener;
import com.ry.lib.ps.exception.TakePhotoException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PhotoStoreInterface.init();
		Button button = (Button) findViewById(R.id.test_photo_btn);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				PhotoStoreInterface.requestTakePhoto(MainActivity.this, new TakePhotoListener() {
					
					@Override
					public void onFailure(TakePhotoException exception) {
						
					}
					
					@Override
					public void onComplete(String rawPhotoPath, String corpPhotoPath) {
						Log.e("SEE", rawPhotoPath + " " + corpPhotoPath);
					}
				});
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		PhotoStoreInterface.handActivityResult(this, requestCode, resultCode, data);
	}
}
