package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Vector;

@WebServlet("/addCart")
public class addcart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Retrieve existing session
        if (session == null) {
            response.sendRedirect("login.html"); // Redirect to login if no session exists
            return;
        }

        String tmp = request.getParameter("categoryId");
        int categoryid = Integer.parseInt(tmp);
        String productname = request.getParameter("productName");
        String price = request.getParameter("price");

        cartItem objItem = new cartItem(categoryid, productname, price);

        @SuppressWarnings("unchecked")
		Vector<cartItem> objCart = (Vector<cartItem>) session.getAttribute("cart");
        if (objCart == null) {
            objCart = new Vector<>(); // Create a new cart
            session.setAttribute("cart", objCart); // Store it in the session
        }

        objCart.add(objItem);

        response.sendRedirect("listcart");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
