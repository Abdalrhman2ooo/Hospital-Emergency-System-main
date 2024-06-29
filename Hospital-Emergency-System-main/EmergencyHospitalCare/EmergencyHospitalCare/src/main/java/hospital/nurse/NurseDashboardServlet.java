package hospital.nurse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/manageNurse")
public class NurseDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    
    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        context.setAttribute("priority1", context.getInitParameter("priority1"));
        context.setAttribute("priority2", context.getInitParameter("priority2"));
        context.setAttribute("priority3", context.getInitParameter("priority3"));
        context.setAttribute("priority4", context.getInitParameter("priority4"));
        context.setAttribute("priority5", context.getInitParameter("priority5"));
    }

    
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String caseNumber = request.getParameter("caseNumber");

        switch (action) {
            case "close":
                handleCaseStatusChange(request, response, caseNumber, "CLOSED");
                break;
            case "initialAssessment":
                redirectToInitialAssessment(request, response, caseNumber);
                break;
            case "submitInitialAssessment":
                handleInitialAssessment(request, response, caseNumber);
                break;
            case "assignToNurse":
                handleAssignToNurse(request, response, caseNumber);
                break;
            case "assignToDepartment":
                handleAssignToDepartment(request, response, caseNumber);
                break;
            case "TransferToHospital":
                handleTransferToHospital(request, response, caseNumber);
                break;
            case "enterTreatment":
                displayTreatmentForm(request, response, caseNumber);
                break;
            case "submitTreatment":
                handleTreatmentSubmission(request, response, caseNumber);
                break;
            case "endTreatment":
                handleEndTreatment(request, response, caseNumber);
                break;
            case "updateAssessment":
                handleUpdateAssessment(request, response, caseNumber);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    private void handleCaseStatusChange(HttpServletRequest request, HttpServletResponse response, String caseNumber, String status) throws ServletException, IOException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET status = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case closed successfully.");
                } else {
                    request.setAttribute("statusMessage", "Failed to close case.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("statusMessage", "Error: " + e.getMessage());
        }
        request.setAttribute("caseNumber", caseNumber);
        request.setAttribute("status", status);
       request.getRequestDispatcher("/nurseDashboard.jsp").forward(request, response);
    }

    private void handleInitialAssessment(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        String priority = request.getParameter("priority");
        String comments = request.getParameter("comments");

        try (Connection con = getConnection()) {
            String status = "Initial Assessment";

            String sql = "UPDATE cases SET status = ?, priority = ?, comments = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, priority);
                pst.setString(3, comments);
                pst.setString(4, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case status updated to " + status + " with priority " + priority + ".");
                } else {
                    request.setAttribute("statusMessage", "Failed to update case status: Case not found.");
                }
                forwardToDashboard(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void handleUpdateAssessment(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        int priority = Integer.parseInt(request.getParameter("priority"));
        String comments = request.getParameter("comments");

        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET priority = ?, comments = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, priority);
                pst.setString(2, comments);
                pst.setString(3, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case updated successfully.");
                } else {
                    request.setAttribute("statusMessage", "Failed to update case: Case not found.");
                }
                request.setAttribute("caseNumber", caseNumber);
                request.getRequestDispatcher("initialAssessment.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("statusMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("updateAssessment.jsp").forward(request, response);
        }
    }

    private void redirectToInitialAssessment(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        request.setAttribute("caseNumber", caseNumber);
        request.getRequestDispatcher("initialAssessment.jsp").forward(request, response);
    }

    private void handleAssignToNurse(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        String nurseId = request.getParameter("nurseId");

        try (Connection con = getConnection()) {
            String status = "Assign to nurse";

            String sql = "UPDATE cases SET status = ?, nurse_id = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, nurseId);
                pst.setString(3, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case assigned to nurse successfully.");
                } else {
                    request.setAttribute("statusMessage", "Failed to assign case to nurse.");
                }
                response.sendRedirect("nurseDashboard.jsp"); 
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void handleAssignToDepartment(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        String department = request.getParameter("department");
        String nurseName = request.getParameter("nurseName");

        try (Connection con = getConnection()) {
            String status = "Assigned";

            String sql = "UPDATE cases SET status = ?, department = ?, nurse_name = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, department);
                pst.setString(3, nurseName);
                pst.setString(4, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case assigned to " + department + " department with nurse " + nurseName + " successfully.");
                } else {
                    request.setAttribute("statusMessage", "Failed to assign case to department.");
                }
                request.setAttribute("caseNumber", caseNumber);
                request.getRequestDispatcher("nurseExamineCase.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void handleTransferToHospital(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        String hospitalName = request.getParameter("hospitalName");

        try (Connection con = getConnection()) {
        	String status = "Transferred";

            String sql = "UPDATE cases SET status = ?, hospital_name = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, hospitalName);
                pst.setString(3, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case transferred to " + hospitalName + " successfully.");
                } else {
                    request.setAttribute("statusMessage", "Failed to transfer case.");
                }
                request.setAttribute("caseNumber", caseNumber);
                request.getRequestDispatcher("transferCase.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void handleTreatmentSubmission(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        String treatment = request.getParameter("treatment");
        String doctorName = request.getParameter("doctorName");

        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET treatment = ?, doctor_name = ?, status = 'UnderTreatment' WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, treatment);
                pst.setString(2, doctorName);
                pst.setString(3, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Treatment entered successfully. Case UnderTreatment.");
                } else {
                    request.setAttribute("statusMessage", "Failed to enter treatment.");
                }
                forwardToDashboard(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void handleEndTreatment(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET status = 'Completed' WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Treatment ended successfully. Case marked as Completed.");
                } else {
                    request.setAttribute("statusMessage", "Failed to end treatment.");
                }
                forwardToDashboard(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void displayTreatmentForm(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        request.setAttribute("caseNumber", caseNumber);
        request.getRequestDispatcher("endTreatment.jsp").forward(request, response);
    }

    private void forwardToDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("nurseDashboard.jsp").forward(request, response);
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin");
    }
}
