package com.avipeta.b11.userasset.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avipeta.b11.userasset.entity.ActiveUserEntity;
import com.avipeta.b11.userasset.entity.TaggedAssetEntity;

public interface TaggedAssetRepo  extends JpaRepository<TaggedAssetEntity, Long> {
	List<TaggedAssetEntity> findByTaggeduser(ActiveUserEntity taggeduser);
	TaggedAssetEntity findByBondedid(String bondedId);
}
