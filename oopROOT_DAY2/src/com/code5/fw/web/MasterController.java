package com.code5.fw.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.code5.biz.board.web.BoardC;

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
		request.setAttribute("com.code5.fw.web.Box", box);

		BoardC boardC = new BoardC();

		String jsp = boardC.welcome(box);

		RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
		dispatcher.forward(request, response);

	}

}
