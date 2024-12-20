package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/registration")
public class registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       PrintWriter out = null;
   
    public registration() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    PrintWriter out = response.getWriter();
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    String email = request.getParameter("email");

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acts", "root", "1234");
	         PreparedStatement psInsert = connection.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)")) {

	        psInsert.setString(1, username);
	        psInsert.setString(2, password);
	        psInsert.setString(3, email);

	        int rowsAffected = psInsert.executeUpdate();
	        if (rowsAffected > 0) {
	            out.println("User added successfully.");
	            
	            response.sendRedirect("login.html");
	        } else {
	            out.println("User addition failed.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	}

