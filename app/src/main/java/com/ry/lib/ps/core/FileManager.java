package com.ry.lib.ps.core;

import java.io.File;
import android.os.Environment;

public class FileManager {
	
	public static final String CROP_FILE_NAME_PREFIX = "crop_";
	
	private PhotoStoreConfig photoStoreConfig;
	private String sdCardRootPath;

	public FileManager(PhotoStoreConfig photoStoreConfig) {
		this.photoStoreConfig = photoStoreConfig;
		sdCardRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
	}
	public String getRawPhotoFileUri(){
		String rawPhotoDir = makeRawPhotoDirAndGetPath();
		String rawPhotoFileName = generateRawPhotoFileName();
		return String.format("%s%s", rawPhotoDir,rawPhotoFileName);
	}
	public String getCropPhotoFilePath(){
		String cropPhotoDir = makeCropPhotoDirAndGetPath();
		String cropPhotoFileName = generateCropPhotoFileName();
		return String.format("%s%s",cropPhotoDir,cropPhotoFileName);
	}
	
	public boolean checkSdcard() {
		String storageState = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(storageState);
	}
	
	private String generateCropPhotoFileName(){
		return String.format("%s%s%s", CROP_FILE_NAME_PREFIX ,getUUID(), photoStoreConfig.getFileType());
	}
	
	private String makeCropPhotoDirAndGetPath() {
		String subPath = photoStoreConfig.getCropPhotoDirSubPath();
		String dirPath = String.format("%s%s%s", sdCardRootPath,subPath,File.separator);
		File savedir = new File(dirPath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		return dirPath;
	}
	
	private String makeRawPhotoDirAndGetPath() {
		String subPath = photoStoreConfig.getRawPhotoDirSubPath();
		String dirPath = String.format("%s%s%s", sdCardRootPath, subPath,File.separator);
		File savedir = new File(dirPath);
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		return dirPath;
	}
	
	private String generateRawPhotoFileName() {
		return String.format("%s%s", getUUID(), photoStoreConfig.getFileType());
	}
	private String getUUID(){
		return String.valueOf(System.currentTimeMillis());
	}

}
