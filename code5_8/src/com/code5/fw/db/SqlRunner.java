package com.code5.fw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.data.TableRecodeBase;
import com.code5.fw.trace.Trace;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author seuk
 *
 */
public class SqlRunner {

	/**
	 * 
	 */
	private Trace trace = new Trace(this);

	/**
	 *
	 */
	private static SqlRunner sql = new SqlRunner();

	/**
	 *
	 */
	private SqlRunner() {
	}

	/**
	 * 
	 * 
	 * @return
	 * 
	 */
	public static SqlRunner getSqlRunner() {
		return sql;
	}

	/**
	 * 
	 * @param transaction
	 * @param SQL
	 * @return
	 * 
	 */
	String getSQL(Transaction transaction, String KEY) throws SQLException {

		String sql = cacheSqlMap.get(KEY);
		if (sql != null) {
			return sql;
		}

		PreparedStatement ps = transaction.prepareStatement("SELECT SQL FROM FW_SQL WHERE KEY = ?");
		ps.setString(1, KEY);

		ResultSet rs = transaction.getResultSet(ps);

		if (!rs.next()) {
			throw new RuntimeException();
		}

		sql = rs.getString("SQL");

		cacheSqlMap.put(KEY, sql);

		return sql;

	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * 
	 */
	SqlRunnerB getSqlRunnerB(String sql) throws SQLException {

		ArrayList<String> param = new ArrayList<String>();

		StringBuffer sqlB = new StringBuffer();

		int fromIndex = -1;
		for (int i = 0; i < 1000; i++) {

			int sp = sql.indexOf("[", fromIndex);
			if (sp == -1) {

				sqlB.append(sql.substring(fromIndex + 1));

				break;
			}

			int ep = sql.indexOf("]", sp + 1);

			param.add(sql.substring(sp + 1, ep));
			sqlB.append(sql.substring(fromIndex + 1, sp) + "?");

			fromIndex = ep;
		}

		SqlRunnerB sqlRunnerB = new SqlRunnerB();
		sqlRunnerB.param = param;
		sqlRunnerB.sql = sqlB.toString();
		sqlRunnerB.sqlOrg = sql;

		return sqlRunnerB;
	}

	/**
	 * 
	 * 
	 * @param sql
	 * @return
	 * 
	 */
	SqlRunnerB getSqlRunnerB(Transaction transaction, String KEY) throws SQLException {

		String sql = getSQL(transaction, KEY);

		SqlRunnerB sqlRunnerB = getSqlRunnerB(sql);
		sqlRunnerB.key = KEY;

		return sqlRunnerB;
	}

	/**
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Table getTable(Transaction transaction, Box box, String FORM_NO) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, FORM_NO);

		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String key = sqlRunnerB.param.get(i);
			String data = box.s(key);
			ps.setString(i + 1, data);

			// [8]
			exeSql = exeSql.replaceFirst("\\?", "'" + data + "'");
		}

		trace.write(sqlRunnerB.key);
		trace.write(exeSql);

		ResultSet rs = transaction.getResultSet(ps);

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] cols = new String[columnCount];

		for (int i = 0; i < cols.length; i++) {
			cols[i] = metaData.getColumnName(i + 1);
		}

		Table table = new TableRecodeBase(cols);

		while (rs.next()) {

			String[] recode = new String[columnCount];

			for (int i = 0; i < cols.length; i++) {
				recode[i] = rs.getString(cols[i]);
			}

			// [9]
			boolean isAddRecode = table.addRecode(recode);
			if (!isAddRecode) {
				break;
			}
		}

		transaction.close();

		return table;
	}

	/**
	 * 
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Table getTable(String FORM_NO) throws SQLException {

		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();

		return getTable(transaction, box, FORM_NO);
	}

	/**
	 * 
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Table getTable(String FORM_NO, Box box) throws SQLException {

		Transaction transaction = TransactionContext.getThread();

		return getTable(transaction, box, FORM_NO);
	}

	/**
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public int executeSql(Transaction transaction, Box box, String FORM_NO) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, FORM_NO);
		trace.write(sqlRunnerB.key);
		trace.write(sqlRunnerB.sql);
		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String key = sqlRunnerB.param.get(i);
			String data = box.s(key);
			ps.setString(i + 1, data);

			exeSql = exeSql.replaceFirst("\\?", "'" + data + "'");
		}

		trace.write(exeSql);

		int i = ps.executeUpdate();

		trace.write("execute cnt [" + i + "]");

		transaction.close();

		return i;
	}

	/**
	 * 
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public int executeSql(String FORM_NO) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return executeSql(transaction, box, FORM_NO);
	}

	/**
	 * @param FORM_NO
	 * @param box
	 * @return
	 * @throws SQLException
	 * 
	 */
	public int executeSql(String FORM_NO, Box box) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		return executeSql(transaction, box, FORM_NO);
	}

	/**
	 * 
	 */
	private ConcurrentHashMap<String, Table> cacheTableMap = new ConcurrentHashMap<String, Table>();

	/**
	 * 
	 */
	private ConcurrentHashMap<String, String> cacheSqlMap = new ConcurrentHashMap<String, String>();

	/**
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(String FORM_NO, Box box) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		return getTableByCache(transaction, FORM_NO, box);
	}

	/**
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(String FORM_NO) throws SQLException {

		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return getTableByCache(transaction, FORM_NO, box);
	}

	/**
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(Transaction transaction, String FORM_NO, Box box) throws SQLException {

		StringBuffer cashKeyB = new StringBuffer();
		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, FORM_NO);

		cashKeyB.append(FORM_NO + "|");

		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String key = sqlRunnerB.param.get(i);
			String data = box.s(key);

			cashKeyB.append(data + "|");
		}

		String cashKey = cashKeyB.toString();
		Table table = cacheTableMap.get(cashKey);
		if (table != null) {
			return table;
		}

		table = getTable(transaction, box, FORM_NO);
		cacheTableMap.put(cashKey, table);
		return table;

	}

	/**
	 * 
	 */
	public void reload() {
		cacheSqlMap.clear();
		cacheTableMap.clear();
	}

}
