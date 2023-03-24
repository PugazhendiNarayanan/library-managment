package com.loginservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.dbConnection.PostgressJDBC;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//String user = request.getParameter("UserName");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user = request.getParameter("UserName");
		String pswd = request.getParameter("pwd");
		PostgressJDBC jdbc = new PostgressJDBC();
		String db_email = null, db_name = null, db_pwd = null;
		int db_phone = 0;
		
		try (Connection con = jdbc.getConnection()) {
//			Statement smt = con.createStatement();
//			smt.executeQuery("select * from public.user_table where user_name='"+ user +"' and user_pwd ='"+ pswd +"'");
			PreparedStatement psmt = con.prepareStatement("select * from public.user_table where user_name= ? and user_pwd=?");
			psmt.setString(1, user);
			psmt.setString(2, pswd);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				db_email = rs.getString(1);
				db_phone = rs.getInt(2);
				db_name = rs.getString(3);
				db_pwd = rs.getString(4);
			}
			jdbc.closeConnection(psmt, con);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!Objects.isNull(db_email)) {
			HttpSession s = request.getSession(true);
			s.setAttribute("email", db_email);
			request.getRequestDispatcher("Library.html").forward(request,response);	
		}
		response.getWriter().append("Login Failed");
}
}
