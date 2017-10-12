package com.shiro.chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class LoginLogoutTest {

	@Test
	public void testHelloWorld() {
		// 1. 获取 SecurityManager 工厂,此处使用ini 配置文件初始化工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 2. 得到 SecurityManager 实例,并绑定给 SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3. 得到 Subject 及创建用户名/密码身份验证 Token(即身份验证凭证)
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		// 4. 登录 ,即身份验证
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			// 5. 身份验证失败
			System.out.println("身份验证失败!!!!");
		}
		Assert.assertEquals(true, subject.isAuthenticated());
		// 6. 退出
		subject.logout();
	}

	@Test
	public void testReaml() {
		// 1. 使用Ini文件初始化工厂对象
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
		// 2. 从工厂实例中获取SecurityManager 实例,并绑定到SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 3. 获取Subject 实例
		Subject subject = SecurityUtils.getSubject();
		// System.out.println(subject.getPrincipal());
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		// 4. 登录,进行身份验证
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			// 登录失败
			System.out.println("登陆失败!!!!!!!!");
		}
		Assert.assertEquals(true, subject.isAuthenticated());
		// 6. 退出
		subject.logout();
	}

	@Test
	public void testJDBCRealm() {
		System.out.println("1111111111111111111111");
		// 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:shiro-jdbc-realm.ini");
		System.out.println("22222222222222");
		// 2、得到SecurityManager实例 并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		System.out.println("3333333333333333333333");
		// 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		System.out.println(token.getUsername());
		System.out.println(token.getPrincipal());
		System.out.println(token.getPassword());
		System.out.println("444444444444444444444");
		try {
			// 4、登录，即身份验证
			subject.login(token);
		} catch (AuthenticationException e) {
			// 5、身份验证失败
			e.printStackTrace();
			System.out.println("身份验证失败!!!");
		}
		System.out.println("5555555555555555555555");
		Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

		// 6、退出
		subject.logout();
	}

	@After
	public void tearDown() throws Exception {
		ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}
}
