package com.spring.aop.dao;

import com.spring.aop.entity.SystemLog;

public interface SystemLogMapper {

	int deleteByPrimaryKey(String id);

	int insert(SystemLog record);

	int insertSelective(SystemLog record);

	SystemLog selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(String id);

	int updateByPrimaryKey(String id);
}
