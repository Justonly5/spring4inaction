package com.spring.aop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.aop.dao.SystemLogMapper;
import com.spring.aop.entity.SystemLog;

@Service
public class SystemLogServiceImpl implements SystemLogService {

	@Autowired
	private SystemLogMapper systemLogMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return systemLogMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SystemLog record) {
		return systemLogMapper.insert(record);
	}

	@Override
	public int insertSelective(SystemLog record) {
		return systemLogMapper.insertSelective(record);
	}

	@Override
	public SystemLog selectByPrimaryKey(String id) {
		return systemLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(String id) {
		return systemLogMapper.updateByPrimaryKeySelective(id);
	}

	@Override
	public int updateByPrimaryKey(String id) {
		return systemLogMapper.updateByPrimaryKey(id);
	}

}
