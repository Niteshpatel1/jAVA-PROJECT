import java.io.IOException;
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
@WebServlet("/ragistration")
public class RegistrationServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("pass");
		String contact = req.getParameter("contact");
		String confirmPassword = req.getParameter("re_pass");
		String uname = "root"; // Your database username
        String pass = "root"; // Your database password
        String URL = "jdbc:mysql://localhost:3306/servlet";
		String i = "INSERT INTO users(name, email, password, contact) VALUES(?,?,?,?);";

	    if (!password.equals(confirmPassword)) {
	        resp.sendRedirect("error.jsp"); // Redirect to an error page
	        return;
	    }
	    try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection to the database
            Connection con = DriverManager.getConnection(URL, uname, pass);
            // Prepare the SQL statement
            PreparedStatement s = con.prepareStatement(i);
            
            // Set the parameters in the prepared statement
            s.setString(1, name);
            s.setString(2, email);
            s.setString(3, password);
            s.setString(4, contact);
            int rs = s.executeUpdate();
            if (rs > 0) {
                resp.sendRedirect("index.jsp"); // Redirect to a success page
            } else {
                resp.sendRedirect("failure.jsp"); // Redirect to a failure page
            }
	    } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Send an internal server error response if there's a problem
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } 
	    
	}

}
