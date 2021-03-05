package com.code5.fw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zero
 *
 */
public class Transaction_SQLITE_JDBC_OLD extends Transaction {

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}

		Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\public\\sqlite\\code5.db");

		conn.setAutoCommit(false);

		return conn;

	}

}
