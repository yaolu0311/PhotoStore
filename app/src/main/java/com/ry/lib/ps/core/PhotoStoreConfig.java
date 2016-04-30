package com.ry.lib.ps.core;

import android.text.TextUtils;

public class PhotoStoreConfig {
	
	
	public static final String DEFAULT_FILE_TYPE = ".jpg";
	public static final String DEFAULT_RAW_PHOTO_DIR_SUB_PATH = "ry_photo_raw_photo";
	public static final String DEFAULT_CROP_PHOTO_DIR_SUB_PATH = "ry_photo_crop_photo";
	
	public static final int DEFAULT_CORP_PHOTO_OUTPUT_X = 1280;
	public static final int DEFAULT_CORP_PHOTO_OUTPUT_Y = 720;
	
	public static final int DEFAULT_CORP_PHOTO_ASPECT_X = 4;
	public static final int DEFAULT_CORP_PHOTO_ASPECT_Y = 3;
	
	
	private String fileType;
	private String rawPhotoDirSubPath;
	private String cropPhotoDirSubPath;
	
	private int corpPhotoOutPutx;
	private int corpPhotoOutPuty;
	private int corpPhotoAspectx;
	private int corpPhotoAspecty;
	
	
	private PhotoStoreConfig(String fileType, String rawPhotoDirSubPath, String cropPhotoDirSubPath,
			int corpPhotoOutPutx, int corpPhotoOutPuty, int corpPhotoAspectx, int corpPhotoAspecty) {
		this.fileType = fileType;
		this.rawPhotoDirSubPath = rawPhotoDirSubPath;
		this.cropPhotoDirSubPath = cropPhotoDirSubPath;
		this.corpPhotoOutPutx = corpPhotoOutPutx;
		this.corpPhotoOutPuty = corpPhotoOutPuty;
		this.corpPhotoAspectx = corpPhotoAspectx;
		this.corpPhotoAspecty = corpPhotoAspecty;
	}


	public static class Builder{
		
		private String fileType;
		private String rawPhotoDirSubPath;
		private String cropPhotoDirSubPath;
		
		private int corpPhotoOutPutx;
		private int corpPhotoOutPuty;
		private int corpPhotoAspectx;
		private int corpPhotoAspecty;
		
		public Builder setFileType(String fileType) {
			this.fileType = fileType;
			return this;
		}
		public Builder setRawPhotoDirSubPath(String rawPhotoDirSubPath) {
			this.rawPhotoDirSubPath = rawPhotoDirSubPath;
			return this;
		}
		public Builder setCropPhotoDirSubPath(String cropPhotoDirSubPath) {
			this.cropPhotoDirSubPath = cropPhotoDirSubPath;
			return this;
		}
		public Builder setCorpPhotoOutPutXY(int x , int y){
			this.corpPhotoOutPutx = x;
			this.corpPhotoOutPuty = y;
			return this;
		}
		public Builder setCorpPhotoAspectXY(int x,int y){
			this.corpPhotoAspectx = x;
			this.corpPhotoAspecty = y;
			return this;
		}
		
		public PhotoStoreConfig build(){
			
			checkfields();
			
			PhotoStoreConfig photoStoreConfig = new PhotoStoreConfig(
					this.fileType,
					this.rawPhotoDirSubPath,
					this.cropPhotoDirSubPath,
					this.corpPhotoOutPutx,
					this.corpPhotoOutPuty,
					this.corpPhotoAspectx,
					this.corpPhotoAspecty
					);
			
			return photoStoreConfig;
		}
		
		private void checkfields() {
			
			if(TextUtils.isEmpty(this.fileType)){
				this.fileType = DEFAULT_FILE_TYPE;
			}
			if(TextUtils.isEmpty(this.rawPhotoDirSubPath)){
				this.rawPhotoDirSubPath = DEFAULT_RAW_PHOTO_DIR_SUB_PATH;
			}
			if(TextUtils.isEmpty(this.cropPhotoDirSubPath)){
				this.cropPhotoDirSubPath = DEFAULT_CROP_PHOTO_DIR_SUB_PATH;
			}
			if(this.corpPhotoAspectx == 0 ){
				this.corpPhotoAspectx = DEFAULT_CORP_PHOTO_ASPECT_X;
			}
			if(this.corpPhotoAspecty == 0){
				this.corpPhotoAspecty = DEFAULT_CORP_PHOTO_ASPECT_Y;
			}
			if(this.corpPhotoOutPutx == 0){
				this.corpPhotoOutPutx = DEFAULT_CORP_PHOTO_OUTPUT_X;
			}
			if(this.corpPhotoOutPuty == 0){
				this.corpPhotoOutPuty = DEFAULT_CORP_PHOTO_OUTPUT_Y;
			}
			
		}
		
		
	}

	public static PhotoStoreConfig createDefaultConfig() {
		return new Builder().build();
	}

	public String getRawPhotoDirSubPath() {
		return rawPhotoDirSubPath;
	}

	public String getCropPhotoDirSubPath() {
		return cropPhotoDirSubPath;
	}
	public String getFileType() {
		if(!fileType.contains(".")){
			fileType = "." + fileType;
		}
		return fileType;
	}
	public int getCorpPhotoOutPutX() {
		return corpPhotoOutPutx;
	}
	public int getCorpPhotoOutPutY() {
		return corpPhotoOutPuty;
	}
	public int getCorpPhotoAspectY() {
		return corpPhotoAspecty;
	}
	public int getCorpPhotoAspectX() {
		return corpPhotoAspectx;
	}

}
