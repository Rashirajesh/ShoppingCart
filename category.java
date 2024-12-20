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
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class category
 */
@WebServlet("/category")
public class category extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        
        
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acts", "root", "1234");
                 PreparedStatement psSelect = connection.prepareStatement("select * from category");
                 ResultSet result = psSelect.executeQuery()) {

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Category List</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
                out.println("h2 { color: #2c3e50; text-align: center; }");
                out.println("table { width: 80%; margin: 0 auto; border-collapse: collapse; }");
                out.println("table, th, td { border: 1px solid #2c3e50; }");
                out.println("th, td { padding: 10px; text-align: left; }");
                out.println("th { background-color: #34495e; color: #ecf0f1; }");
                out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
                out.println("tr:hover { background-color: #dcdde1; }");
                out.println("a { color: #2980b9; text-decoration: none; }");
                out.println("a:hover { text-decoration: underline; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Category List</h2>");
                out.println("<table>");
                out.println("<tr><th>Category ID</th><th>Category Name</th><th>Description</th></tr>");

                while (result.next()) {
                    int categoryId = result.getInt("categoryid");
                    String categoryName = result.getString("categoryname");
                    String description = result.getString("description");

                    out.println("<tr>");
                    out.println("<td>" + categoryId + "</td>");
                    out.println("<td><a href='Products?categoryId=" + categoryId + "'>" + categoryName + "</a></td>");
                    out.println("<td>" + description + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            } catch (SQLException e) {
                e.printStackTrace(out);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(out);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
