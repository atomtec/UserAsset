package com.avipeta.b11.asset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avipeta.b11.asset.entity.AssetEntity;



public interface AssetRepo extends JpaRepository<AssetEntity, Long> {
	List<AssetEntity> findByIsinuse(Boolean inUse);
	List<AssetEntity> findByIsreleased(Boolean isReleased);
	List<AssetEntity> findByBondedid(String bondedId);
}
