package com.shiro.chapter2.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm {

	@Override
	// 根据 Token 获取认证信息
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("Start getAuthenticationInfo...........");
		String userName = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());
		System.out.println("UserName:" + userName);
		System.out.println("Password:" + password);
		if (!"zhang".equals(userName)) {
			throw new UnknownAccountException();
		}
		if (!"123".equals(password)) {
			throw new IncorrectCredentialsException();
		}
		return new SimpleAuthenticationInfo(userName, password, getName());
	}

	// 返回一个唯一的 Realm 名字
	@Override
	public String getName() {
		return "myRealm1";
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		// 支持的 Token 类型
		return token instanceof UsernamePasswordToken;
	}

}
