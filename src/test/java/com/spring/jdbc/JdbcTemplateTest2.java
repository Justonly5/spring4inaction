package com.spring.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcTemplateTest2 {

	private static JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void setUpClass() {
		String url = "jdbc:hsqldb:mem:test";
		String username = "sa";
		String password = "";
		DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Test
	public void test() {
		// 1.声明SQL
		String sql = "select * from INFORMATION_SCHEMA.SYSTEM_TABLES";
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				// 2.处理结果集
				String value = rs.getString("TABLE_NAME");
				System.out.println("Column TABLENAME:" + value);
			}
		});

	}
}
