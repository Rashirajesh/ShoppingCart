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

@WebServlet("/Products")
public class Products extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        response.setContentType("text/html");

        String categoryIdParam = request.getParameter("categoryId");

        if (categoryIdParam == null || categoryIdParam.isEmpty()) {
            out.println("<html><body><h2>No category selected. Please go back and select a category.</h2></body></html>");
            return;
        }

        try {
            int categoryId = Integer.parseInt(categoryIdParam);

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acts", "root", "1234");
                 PreparedStatement psProducts = connection.prepareStatement("SELECT * FROM products WHERE categoryid = ?")) {

                psProducts.setInt(1, categoryId);

                try (ResultSet result = psProducts.executeQuery()) {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Product List</title>");
                    out.println("<style>");
                    out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
                    out.println("h2 { color: #2c3e50; text-align: center; }");
                    out.println("table { width: 70%; margin: 0 auto; border-collapse: collapse; }");
                    out.println("table, th, td { border: 1px solid #2c3e50; }");
                    out.println("th, td { padding: 10px; text-align: left; }");
                    out.println("th { background-color: #34495e; color: #ecf0f1; }");
                    out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
                    out.println("tr:hover { background-color: #dcdde1; }");
                    out.println("</style>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h2>Product List for Category " + categoryId + "</h2>");
                    out.println("<table>");
                    out.println("<tr><th>Product Name</th><th>Price</th></tr>");

                    while (result.next()) {
                    	String productName = result.getString("productname");
                        String price = result.getString("productprice");

                        out.println("<tr>");
                        out.println("<td>" + productName + "</td>");
                        out.println("<td>" + price + "</td>");
                        out.println("<td><a href='addCart?categoryId=" + categoryId + "&productName=" + productName + "&price=" + price + "'>Add to Cart</a></td>");

                        out.println("</tr>");
                    }

                    out.println("</table>");
                    out.println("</body>");
                    out.println("</html>");

                } catch (SQLException e) {
                    out.println("<p>Error retrieving products: " + e.getMessage() + "</p>");
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                out.println("<p>Error connecting to database: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            out.println("<html><body><h2>Invalid category ID. Please go back and select a valid category.</h2></body></html>");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            out.println("<html><body><h2>Database driver not found. Please try again later.</h2></body></html>");
            e.printStackTrace();
        }
    }
}
