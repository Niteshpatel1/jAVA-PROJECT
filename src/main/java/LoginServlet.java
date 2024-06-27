import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String URL = "jdbc:mysql://localhost:3306/servlet"; // Your database URL
        String uname = "root"; // Your database username
        String pass = "root"; // Your database password
        String q = ("SELECT COUNT(*) AS count FROM users WHERE email =? and password=?"); // SQL query
        
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection to the database
            Connection con = DriverManager.getConnection(URL, uname, pass);
            // Prepare the SQL statement
            PreparedStatement s = con.prepareStatement(q);
            
            // Set the parameters in the prepared statement
            s.setString(1, email);
            s.setString(2, password);
            // Execute the query
            ResultSet rs = s.executeQuery();
            
            // Check if the result set has a next row
            if(rs.next()) {
                // If the user is found, forward to the login page
                RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
                rd.forward(req, resp);
            } else {
                // If the user is not found, redirect to the registration page
                resp.sendRedirect("index.jsp"); // Corrected typo from "ragistration.jsp"
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Send an internal server error response if there's a problem
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
