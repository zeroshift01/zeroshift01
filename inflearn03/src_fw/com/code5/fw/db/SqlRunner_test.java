package com.code5.fw.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class SqlRunner_test {

	/**
	 * @throws Exception
	 */
	@Test
	public void test_01() throws Exception {

		Box box = BoxContext.get();
		Transaction transaction = TransactionContext.get();

		box.put("EMP_NM", "ABC");

		SqlRunner sql = SqlRunner.getSqlRunner();

		String key = "com.code5.fw.db.SqlRunner_test.SQLRUNNER_TEST_01";
		Table table = sql.getTable(transaction, box, key);

		assertEquals(2, table.size());

		String[] cols = table.getCols();

		assertEquals("EMP_N", cols[0]);
		assertEquals("EMP_NM", cols[1]);
		assertEquals("HP_N", cols[2]);

		assertEquals("N01", table.s("EMP_N", 0));
		assertEquals("N03", table.s("EMP_N", 1));

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_02() throws Exception {

		Box box = BoxContext.get();
		Transaction transaction = TransactionContext.get();

		box.put("EMP_N", "N01");
		box.put("HP_N", "010-2222-3333");

		SqlRunner sql = SqlRunner.getSqlRunner();

		String key = "com.code5.fw.db.SqlRunner_test.SQLRUNNER_TEST_02";
		int i = sql.executeSql(transaction, box, key);

		assertEquals(1, i);

		TransactionContext.commit();

	}

}
