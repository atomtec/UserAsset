package com.avipeta.b11.asset;

import java.util.Calendar;

import com.avipeta.b11.asset.entity.AssetEntity;

public class Utility {

	public static  AssetEntity getTestAsset() {
		AssetEntity assetEntity = new AssetEntity();
		assetEntity.setAddeddate(Calendar.getInstance().getTime());
		assetEntity.setDesrciption("This is a demo asset");
		assetEntity.setIsinuse(true);
		assetEntity.setBondedid("WT-01");
		assetEntity.setTitle("HondaAsset");
		return assetEntity;
	}
	
	public static  AssetEntity getTestAsset2() {
		AssetEntity assetEntity = new AssetEntity();
		assetEntity.setAddeddate(Calendar.getInstance().getTime());
		assetEntity.setDesrciption("This is a demo asset2");
		assetEntity.setIsinuse(true);
		assetEntity.setBondedid("WT-02");
		assetEntity.setTitle("HondaAsset2");
		return assetEntity;
	}
	
	public static  AssetEntity getTestAsset3() {
		AssetEntity assetEntity = new AssetEntity();
		assetEntity.setAddeddate(Calendar.getInstance().getTime());
		assetEntity.setDesrciption("This is a demo asset3");
		assetEntity.setIsinuse(false);
		assetEntity.setIsreleased(true);
		assetEntity.setReleasedate(Calendar.getInstance().getTime());
		assetEntity.setBondedid("WT-03");
		assetEntity.setTitle("HondaAsset3");
		return assetEntity;
	}
}
