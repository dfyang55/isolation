package com.dfyang.isolation.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	
	private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	
	private static final String URL = "jdbc:mysql:///test?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
	
	private static final String USERNAME = "root";
	
	private static final String PASSWORD = "151310";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DRIVER_CLASS);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
}
