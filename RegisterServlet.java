package com.loginservlet;

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

import com.library.dbConnection.PostgressJDBC;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/registerservlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String user=request.getParameter("user");
		String pass=request.getParameter("pwd");
		int number=Integer.parseInt(request.getParameter("phone"));
		String email= request.getParameter("email");
		String bookrented= request.getParameter("bookrented");
		String bookbought= request.getParameter("bookbought");
		String db_email=null, db_name=null, db_pwd =null;
		int db_phone=0;
		
		try {
			Class.forName("org.postgresql.Driver");
			 Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Data123");
			 	Statement st= con.createStatement();
			 PreparedStatement psmt = con.prepareStatement("INSERT INTO public.user_table( user_email, phone_number, user_name, user_pwd, books_rented, books_bought) VALUES (?, ?, ?, ?, ?, ?);");
				psmt.setString(1, email);
				psmt.setInt(2, number);
				psmt.setString(3, user);
				psmt.setString(4, pass);
				psmt.setString(5,bookrented);
				psmt.setString(6, bookbought);
				ResultSet rs =  st.executeQuery("select * from  public.user_table");
					while(rs.next()) {
					System.out.println(rs.getString(1)+" "+rs.getString(3)+" "+rs.getString(4)+" "+ rs.getInt(2));
					db_email = rs.getString(1);
					db_name = rs.getString(3);
					db_pwd = rs.getString(4);
					db_phone = rs.getInt(2);
					
				}
				psmt.executeUpdate();
				System.out.println("Record SuccessFully Inserted"+user);
				st.close();
				con.close();
			
			 }catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			request.getRequestDispatcher("Index.html").forward(request,response);
	}

}
