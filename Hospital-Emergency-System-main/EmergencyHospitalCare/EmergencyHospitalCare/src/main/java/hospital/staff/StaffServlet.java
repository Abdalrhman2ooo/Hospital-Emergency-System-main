package hospital.staff;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StaffServlet")
public class StaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();

            // Retrieve nurse data
            ResultSet nurseResultSet = statement.executeQuery("SELECT * FROM nurses");
            while (nurseResultSet.next()) {
                int nurseId = nurseResultSet.getInt("nurse_id");
                String nurseName = nurseResultSet.getString("nurse_name");
                String nurseSpecialty = nurseResultSet.getString("nurse_specialty");
                String nurseContact = nurseResultSet.getString("nurse_contact");

                out.println("Nurse ID: " + nurseId + ", Name: " + nurseName + ", Specialty: " + nurseSpecialty + ", Contact: " + nurseContact + "<br>");
            }
            nurseResultSet.close();

            // Retrieve doctor data
            ResultSet doctorResultSet = statement.executeQuery("SELECT * FROM doctors");
            while (doctorResultSet.next()) {
                int doctorId = doctorResultSet.getInt("doctor_id");
                String doctorName = doctorResultSet.getString("doctor_name");
                String doctorSpecialty = doctorResultSet.getString("doctor_specialty");
                String doctorContact = doctorResultSet.getString("doctor_contact");

                out.println("Doctor ID: " + doctorId + ", Name: " + doctorName + ", Specialty: " + doctorSpecialty + ", Contact: " + doctorContact + "<br>");
            }
            doctorResultSet.close();

            statement.close();
            connection.close();
        } catch (Exception e) {
            out.println("Error during database connection: " + e.getMessage());
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String table = request.getParameter("table");
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request, response, table);
        } else if ("edit".equals(action)) {
            handleEdit(request, response, table);
        } else if ("delete".equals(action)) {
            handleDelete(request, response, table);
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response, String table) throws IOException {
        String name = request.getParameter("name");
        String specialty = request.getParameter("specialty");
        String contact = request.getParameter("contact");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "";
            if ("nurses".equals(table)) {
                sql = "INSERT INTO nurses (nurse_name, nurse_specialty, nurse_contact) VALUES (?, ?, ?)";
            } else if ("doctors".equals(table)) {
                sql = "INSERT INTO doctors (doctor_name, doctor_specialty, doctor_contact) VALUES (?, ?, ?)";
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, specialty);
                preparedStatement.setString(3, contact);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            response.getWriter().println("Error during database operation: " + e.getMessage());
        }
        response.sendRedirect("staff.jsp");
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response, String table) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String specialty = request.getParameter("specialty");
        String contact = request.getParameter("contact");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "";
            if ("nurses".equals(table)) {
                sql = "UPDATE nurses SET nurse_name = ?, nurse_specialty = ?, nurse_contact = ? WHERE nurse_id = ?";
            } else if ("doctors".equals(table)) {
                sql = "UPDATE doctors SET doctor_name = ?, doctor_specialty = ?, doctor_contact = ? WHERE doctor_id = ?";
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, specialty);
                preparedStatement.setString(3, contact);
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            response.getWriter().println("Error during database operation: " + e.getMessage());
        }
        response.sendRedirect("staff.jsp");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, String table) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "";
            if ("nurses".equals(table)) {
                sql = "DELETE FROM nurses WHERE nurse_id = ?";
            } else if ("doctors".equals(table)) {
                sql = "DELETE FROM doctors WHERE doctor_id = ?";
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            response.getWriter().println("Error during database operation: " + e.getMessage());
        }
        response.sendRedirect("staff.jsp");
    }
}
