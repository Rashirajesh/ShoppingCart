package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/addCategory")
public class addCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String tmp = request.getParameter("categoryid");
		int categoryid = Integer.parseInt(tmp);
		String categoryname = request.getParameter("categoryname");
		String description = request.getParameter("description");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
			try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acts", "root", "1234");
				PreparedStatement psInsert = connection.prepareStatement("insert into category values(?,?,?)"); )
			{
				psInsert.setInt(1, categoryid);
				psInsert.setString(2, categoryname);
				psInsert.setString(3, description);
				
				int rowsAffected = psInsert.executeUpdate();
		        if (rowsAffected > 0) {
		        	  out.println("<script type=\"text/javascript\">");
		              out.println("alert('Category added successfully!');");
		              out.println("window.location.href = 'admin.html';");
		              out.println("</script>");
		            
		        } else {
		            out.println("category addition failed.");
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
