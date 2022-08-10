package com.avipeta.b11.userasset.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avipeta.b11.userasset.entity.ActiveUserEntity;


public interface ActiveUserRepo  extends JpaRepository<ActiveUserEntity, Long>  {
	ActiveUserEntity findByRemoteuserid(Long remoteuserid);
}
