package com.code5.biz.com003;

import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.web.Box;
import com.code5.fw.web.MasterController;

/**
 * 
 * 로그인
 * 
 * @author seuk
 * 
 */
public class Com003 {

	/**
	 * 
	 * 로그인 화면
	 * 
	 * @param box
	 * @return
	 */
	public String com00310() throws Exception {
		return "com00310";
	}

	/**
	 * 로그인 처리
	 * 
	 * @return
	 */
	public String com00311() throws Exception {

		Box box = Box.getThread();

		// String ID = box.s("ID");
		String PIN = box.s("PIN");

		Com003D dao = new Com003D();

		Table com00311_1 = dao.com00311_1();

		if (com00311_1.size() != 1) {
			box.put("ret", "아이디가 없거나 패스워드가 일치하지 않습니다.");
			return MasterController.execute("com00310");
		}

		Box thisUser = com00311_1.getBox();

		if (!PIN.equals(thisUser.s("PIN"))) {
			box.put("ret", "아이디가 없거나 패스워드가 일치하지 않습니다.");

			if (dao.com00311_2() != 1) {
				throw new Exception();
			}

			return MasterController.execute("com00310");
		}

		int FAIL_CNT = thisUser.getInt("FAIL_CNT");
		if (FAIL_CNT > 5) {
			box.put("ret", "패스워드를 5회 이상 실패하였습니다.");
			return MasterController.execute("com00310");
		}

		String ID = thisUser.s("ID");
		String AUTH = thisUser.s("AUTH");

		SessionB user = new SessionB(ID, AUTH);
		box.setSessionB(user);

		if (dao.com00311_3() != 1) {
			throw new Exception();
		}

		return MasterController.execute("com00320");

	}

	/**
	 * 
	 * 로그인 후 화면
	 * 
	 * @return
	 * @throws Exception
	 */
	public String com00320() throws Exception {
		return "com00320";
	}

	/**
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String com00330() throws Exception {

		return MasterController.execute("mng00110");
	}
}