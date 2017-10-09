package com.spring.aop.service;

import com.spring.aop.entity.SystemLog;

public interface SystemLogService {

	int deleteByPrimaryKey(String id);

	int insert(SystemLog record);

	int insertSelective(SystemLog record);

	SystemLog selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(String id);

	int updateByPrimaryKey(String id);
}
