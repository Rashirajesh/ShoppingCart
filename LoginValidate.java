package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/LoginValidate")
public class LoginValidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String username = request.getParameter("Username");
		String password = request.getParameter("password");
		
		if(username == null || password == null) {
			throw new ServletException("Input para issues!!");
		};
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acts","root","1234");
			PreparedStatement psAuth = connection.prepareStatement("select * from users where username = ? and password = ?");
				)
		{
			
			psAuth.setString(1,username);
			psAuth.setString(2,password);
			
			try(ResultSet result = psAuth.executeQuery()){
				if(result.next()) {
					if(username.equals("admin")){
						HttpSession session = request.getSession();
						session.setAttribute("username",username);
						response.sendRedirect("admin.html");
						return;
						}
					response.sendRedirect("category");
				}
				else {
					out.println("Authentication failed");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
