package com.thinkerwolf.frameworkstudy.mybatis.javajdbc;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcStudy {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
		DataSource dataSource = new DriverManagerDataSource(url, "root", "123");
		Connection conn = dataSource.getConnection();
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		
		PreparedStatement ps = conn.prepareStatement("select * from blog where id = ?");
		// 抽象
		ps.setObject(1, 1, JDBCType.INTEGER);
		
		
		ResultSet rs = ps.executeQuery();
		System.out.println(rs.getMetaData().getColumnCount());
		while (rs.next()) {
			rs.getObject(1);
		}
		while (rs.next()) {
			rs.getInt(1);
		}
		conn.close();
	}
}
