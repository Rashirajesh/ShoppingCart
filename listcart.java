package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

@WebServlet("/listcart")
public class listcart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("cart") == null) {
            out.println("<html><head><title>Shopping Cart</title></head><body>");
            out.println("<h2>Your cart is empty. Start shopping!</h2>");
            out.println("<a href='category'>Go to Categories</a>");
            out.println("</body></html>");
            return;
        }

        Vector<cartItem> objCart = (Vector<cartItem>) session.getAttribute("cart");

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Shopping Cart</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f8f9fa; }");
        out.println("h2 { color: #343a40; text-align: center; }");
        out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; background-color: #ffffff; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
        out.println("table, th, td { border: 1px solid #dee2e6; }");
        out.println("th, td { padding: 10px; text-align: center; }");
        out.println("th { background-color: #6c757d; color: white; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("tr:hover { background-color: #e9ecef; }");
        out.println(".actions { text-align: center; margin: 20px; }");
        out.println(".actions a { text-decoration: none; padding: 10px 15px; color: white; background-color: #007bff; border-radius: 5px; margin: 0 10px; }");
        out.println(".actions a:hover { background-color: #0056b3; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>Your Shopping Cart</h2>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Category ID</th>");
        out.println("<th>Product Name</th>");
        out.println("<th>Price</th>");
        out.println("</tr>");

        int total = 0;

        for (cartItem cartItem : objCart) {
            out.println("<tr>");
            out.println("<td>" + cartItem.getCategoryid() + "</td>");
            out.println("<td>" + cartItem.getProductname() + "</td>");
            out.println("<td>₹" + cartItem.getPrice() + "</td>");
            out.println("</tr>");
            total += Integer.parseInt(cartItem.getPrice());
        }

        out.println("<tr>");
        out.println("<td colspan='2'><strong>Total</strong></td>");
        out.println("<td><strong>₹" + total + "</strong></td>");
        out.println("</tr>");
        out.println("</table>");

        out.println("<div class='actions'>");
        out.println("<a href='category'>Continue Shopping</a>");
        out.println("<a href='payment.html'>make payment</a>");
        out.println("<a href='logout'>Logout</a>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
