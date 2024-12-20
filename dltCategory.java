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

/**
 * Servlet implementation class dltCategory
 */
@WebServlet("/dltCategory")
public class dltCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		String tmp = request.getParameter("categoryid");
		int categoryid = Integer.parseInt(tmp);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
			try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acts", "root", "1234");
				PreparedStatement psDelete = connection.prepareStatement("Delete from category where categoryid = ?"); )
			{
				psDelete.setInt(1, categoryid);
				
				
				int rowsAffected = psDelete.executeUpdate();
		        if (rowsAffected > 0) {
		        	  out.println("<script type=\"text/javascript\">");
		              out.println("alert('Category deleted successfully!');");
		              out.println("window.location.href = 'admin.html';");
		              out.println("</script>");
		            
		        } else {
		            out.println("category deletion failed.");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
