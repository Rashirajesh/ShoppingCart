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
import java.util.UUID;
import java.util.Vector;

@WebServlet("/paymentprocess")
public class paymentprocess extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("cart") == null) {
            out.println("<html><head><title>Session Expired</title></head><body>");
            out.println("<h2>Your session has expired or the cart is empty. Please start again.</h2>");
            out.println("<a href='category'>Go to Categories</a>");
            out.println("</body></html>");
            return;
        }

        Vector<cartItem> objCart = (Vector<cartItem>) session.getAttribute("cart");

        int total = 0;
        for (cartItem cartItem : objCart) {
            total += Integer.parseInt(cartItem.getPrice());
        }

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acts", "root", "1234");
                 PreparedStatement psCheckCard = connection.prepareStatement(
                         "SELECT * FROM cards WHERE cardno = ? AND expiry = ?");
                 PreparedStatement psUpdateBalance = connection.prepareStatement(
                         "UPDATE cards SET balance = balance - ? WHERE cardno = ?");
                 PreparedStatement psInsertTransaction = connection.prepareStatement(
                         "INSERT INTO transactions (cardno, amount, status) VALUES (?, ?, ?)")) {

                // Validate card
                psCheckCard.setString(1, cardNumber);
                psCheckCard.setString(2, expiryDate);

                try (ResultSet result = psCheckCard.executeQuery()) {
                    if (result.next()) {
                        int currentBalance = result.getInt("balance");

                        if (currentBalance >= total) {
                            psUpdateBalance.setInt(1, total);
                            psUpdateBalance.setString(2, cardNumber);
                            psUpdateBalance.executeUpdate();

                            // Insert transaction
                            psInsertTransaction.setString(1, cardNumber);
                            psInsertTransaction.setInt(2, total);
                            psInsertTransaction.setInt(3, 1);
                            psInsertTransaction.executeUpdate();

                            // Clear cart
                            session.removeAttribute("cart");

                            // Success message
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Order Placed</title>");
                            out.println("<style>");
                            out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f8f9fa; }");
                            out.println(".message { text-align: center; margin-top: 100px; }");
                            out.println(".message h2 { color: #28a745; font-size: 24px; }");
                            out.println(".message p { font-size: 18px; color: #6c757d; }");
                            out.println(".message a { text-decoration: none; color: white; background-color: #007bff; padding: 10px 20px; border-radius: 5px; }");
                            out.println(".message a:hover { background-color: #0056b3; }");
                            out.println("</style>");
                            out.println("</head>");
                            out.println("<body>");
                            out.println("<div class='message'>");
                            out.println("<h2>Order Placed Successfully!</h2>");
                            out.println("<p>Your transaction was successful. Thank you for shopping with us!</p>");
                            out.println("<a href='category'>Continue Shopping</a>");
                            out.println("</div>");
                            out.println("</body>");
                            out.println("</html>");
                        } else {
                            // Insufficient balance
                            out.println("<html><head><title>Payment Failed</title></head><body>");
                            out.println("<h2>Insufficient balance. Please try again with another card.</h2>");
                            out.println("<a href='payment.html'>Retry Payment</a>");
                            out.println("</body></html>");
                        }
                    } else {
                        // Invalid card details
                        out.println("<html><head><title>Payment Failed</title></head><body>");
                        out.println("<h2>Invalid card details. Please try again.</h2>");
                        out.println("<a href='payment.html'>Retry Payment</a>");
                        out.println("</body></html>");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h2>An error occurred while processing your payment. Please try again later.</h2>");
            out.println("<a href='category'>Go to Categories</a>");
            out.println("</body></html>");
        }
    }
}
