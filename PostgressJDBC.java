package com.library.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgressJDBC {
	
	public Connection getConnection() {
		Connection con = null;
		try {
			//register the driver
			Class.forName("org.postgresql.Driver");
			//create connection
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Data123");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public void closeConnection (Statement smt, Connection con) {
		try {
			smt.close();
			con.close();
		}catch (SQLException se) {
			se.printStackTrace();
		}finally {
			try {
				con.close();
			}catch(SQLException se) {
				se.getStackTrace();
			}
		}
	}
	
}
