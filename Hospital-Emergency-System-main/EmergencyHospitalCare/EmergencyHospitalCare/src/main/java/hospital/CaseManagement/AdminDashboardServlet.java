package hospital.CaseManagement;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manageAdmin")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String caseNumber = request.getParameter("caseNumber");
        String statusMessage = "";
        
        if ("assignToNurse".equals(action)) { 
            request.getRequestDispatcher("/nurseDashboard.jsp").forward(request, response);
            return;
         }
       
        if (action != null && caseNumber != null) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String currentStatus = getCurrentCaseStatus(conn, caseNumber);
                
                if ("close".equals(action) && "Admitted".equalsIgnoreCase(currentStatus)) {
                    // Allow the Admission Officer to cancel the case even if admitted
                    updateCaseStatus(conn, caseNumber, "Canceled");
                    statusMessage = "Case " + caseNumber + " has been canceled by the Admission Officer.";
                } else if ("close".equals(action)) {
                    updateCaseStatus(conn, caseNumber, "Canceled");
                    statusMessage = "Case " + caseNumber + " has been canceled.";
                } else {
                    String newStatus = getStatusFromAction(action);
                    if (newStatus != null) {
                        updateCaseStatus(conn, caseNumber, newStatus);
                        statusMessage = "Action '" + action + "' successfully performed on case " + caseNumber;
                    } else {
                        statusMessage = "Invalid action.";
                    }
                }
            } catch (SQLException e) {
                statusMessage = "Database error: " + e.getMessage();
                e.printStackTrace();
            }
        } else {
            statusMessage = "Action or case number is missing.";
        }

        request.setAttribute("statusMessage", statusMessage);
        request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
    }

    private String getCurrentCaseStatus(Connection conn, String caseNumber) throws SQLException {
        String status = null;
        String query = "SELECT status FROM cases WHERE case_number = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, caseNumber);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        }
        return status;
    }

    private void updateCaseStatus(Connection conn, String caseNumber, String status) throws SQLException {
        String updateQuery = "UPDATE cases SET status = ? WHERE case_number = ?";
        try (PreparedStatement pst = conn.prepareStatement(updateQuery)) {
            pst.setString(1, status);
            pst.setString(2, caseNumber);
            pst.executeUpdate();
        }
    }

   
        
    
    private String getStatusFromAction(String action) {
        switch (action) {
            case "reopen":
                return "New";
            case "admit":
                return "Admitted";
            case "close":
                return "Canceled";
            case "createFollowup":
                return "FollowedUp";
            case "reject":
                return "Rejected";
            default:
                return null;
        }
    }
}
