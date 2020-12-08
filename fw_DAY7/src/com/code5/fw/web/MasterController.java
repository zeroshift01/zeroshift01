package com.code5.fw.web;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxHttp;
import com.code5.fw.data.SessionB;
import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC;

/**
 * @author seuk
 *
 */
public class MasterController extends HttpServlet {

	public final static String REMOTE_ADDR_KEY = "com.code5.fw.web.MasterController.remoteAddr";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Box box = new BoxHttp(request);
		BoxContext.setThread(box);

		String remoteAddr = request.getRemoteAddr();
		box.put(REMOTE_ADDR_KEY, remoteAddr);

		Transaction transaction = new Transaction_SQLITE_JDBC();
		TransactionContext.setThread(transaction);

		try {

			String KEY = request.getPathInfo().substring(1);

			String JSP_KEY = execute(KEY);

			MasterControllerD dao = new MasterControllerD();
			Box view = dao.getView(JSP_KEY);
			String JSP = view.s("JSP");

			RequestDispatcher dispatcher = request.getRequestDispatcher(JSP);

			dispatcher.forward(request, response);

			transaction.commit();

		} catch (Exception ex) {

			transaction.rollback();
			ex.printStackTrace();

		} finally {

			transaction.close();

			TransactionContext.removeThread();
			BoxContext.removeThread();
		}

	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 * 
	 * 
	 */
	public static String execute(String KEY) throws Exception {

		MasterControllerD dao = new MasterControllerD();

		Box controller = dao.getController(KEY);

		String CLASS_NAME = controller.s("CLASS_NAME");
		String METHOD_NAME = controller.s("METHOD_NAME");

		// [1]
		boolean checkUrlAuth = checkUrlAuth(controller);
		if (!checkUrlAuth) {
			throw new Exception();
		}

		@SuppressWarnings("rawtypes")
		Class newClass = Class.forName(CLASS_NAME);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		Constructor constructor = newClass.getConstructor();

		Object instance = constructor.newInstance();

		if (!(instance instanceof BizController)) {
			throw new Exception();
		}

		Method method = instance.getClass().getDeclaredMethod(METHOD_NAME);
		String JSP_KEY = (String) method.invoke(instance);
		return JSP_KEY;

	}

	/**
	 * @param controller
	 * @return
	 * @throws Exception
	 */
	private static boolean checkUrlAuth(Box controller) throws Exception {

		String SESSION_CHECK_YN = controller.s("SESSION_CHECK_YN");

		// [2]
		if (!"Y".equals(SESSION_CHECK_YN)) {
			return true;
		}

		Box box = BoxContext.getThread();
		SessionB user = box.getSessionB();
		if (user == null) {
			return false;
		}

		// [3]
		String AUTH = controller.s("AUTH");

		if ("".equals(AUTH)) {
			return true;
		}

		if (AUTH.indexOf(user.getAuth()) >= 0) {
			return true;
		}

		// [4]
		return false;

	}

	/**
	 * 
	 * [5]
	 * 
	 * @param KEY
	 * @return
	 * @throws Exception
	 */
	public static boolean checkUrlAuth(String KEY) throws Exception {

		MasterControllerD dao = new MasterControllerD();

		Box controller = dao.getController(KEY);

		return checkUrlAuth(controller);

	}
}