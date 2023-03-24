package com.libraryservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.dbConnection.PostgressJDBC;

/**
 * Servlet implementation class LibraryServlet
 */
@WebServlet("/libraryservlet")
public class LibraryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LibraryServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession s = request.getSession(false);
		String email = (String) s.getAttribute("email");
		response.getWriter().append("Selected Book were added under your name: ").append(email);

		String bookrented= request.getParameter("book");
		try {
			PostgressJDBC obj = new PostgressJDBC();
			Connection con = obj.getConnection();
			PreparedStatement psmt = con.prepareStatement("UPDATE public.user_table SET books_rented=? WHERE user_email = ?");
				psmt.setString(1,bookrented);
				psmt.setString(2, email);
				psmt.executeUpdate();
				//System.out.println("Record SuccessFully Inserted"+user);
				con.close();
			}
		catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}

