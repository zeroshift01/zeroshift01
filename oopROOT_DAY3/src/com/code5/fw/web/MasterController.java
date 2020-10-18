package com.code5.fw.web;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.biz.welcome.Welcome;

/**
 * @author seuk
 *
 */
public class MasterController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// [1]
		Box box = new BoxHttp(request);
		Box.setThread(box);

		try {

			Welcome welcome = new Welcome();

			String jsp = welcome.service();

			RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);

			dispatcher.forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			// [2]
			Box.removeThread();
		}

	}

}
