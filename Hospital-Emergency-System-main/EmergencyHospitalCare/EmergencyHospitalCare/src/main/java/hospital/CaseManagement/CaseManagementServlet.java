package hospital.CaseManagement;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/manageCase")
@MultipartConfig
public class CaseManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String STATUS_FOLLOW_UP = "Follow-up";
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("sessionExpired.jsp"); // Redirect to a session expired page
            return;
        }

        String action = request.getParameter("action");
        String caseNumber = request.getParameter("caseNumber");

        // Check case status before allowing update or cancel
        try (Connection con = getConnection()) {
            String statusCheckSql = "SELECT status FROM cases WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(statusCheckSql)) {
                pst.setString(1, caseNumber);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        String status = rs.getString("status");
                        if ("Admitted".equalsIgnoreCase(status)) {
                            request.setAttribute("statusMessage", "Case cannot be updated or canceled as the patient is already admitted.");
                            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
                            return;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }

        switch (action) {
            case "update":
                displayCaseUpdateForm(request, response);
                break;
            case "submitUpdate":
                handleCaseUpdate(request, response);
                break;
            case "close":
                handleCaseStatusChange(request, response, caseNumber, "Canceled");
                break;
            case "reopen":
                handleCaseStatusChange(request, response, caseNumber, "New");
                break;
            case "admit":
                redirectToNurseExamine(request, response, caseNumber);
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
            case "transferToHospital":
                handleTransferToHospital(request, response, caseNumber);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
            case "enterTreatment":
                displayTreatmentForm(request, response, caseNumber);
                break;
            case "submitTreatment":
                handleTreatmentSubmission(request, response, caseNumber);
                break;
            case "createFollowup":
                handleCreateFollowUp(request, response, caseNumber);
                break;
        }
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
                    request.setAttribute("statusMessage", "Failed to update case status.");
                }
                forwardToDashboard(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void redirectToInitialAssessment(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        request.setAttribute("caseNumber", caseNumber);
        request.getRequestDispatcher("initialAssessment.jsp").forward(request, response);
    }

    private void displayCaseUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String caseNumber = request.getParameter("caseNumber");

        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM cases WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, caseNumber);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("caseNumber", rs.getString("case_number"));
                        request.setAttribute("submittedBy", rs.getString("submitted_by"));
                        request.setAttribute("relationshipToPatient", rs.getString("relationship_to_patient"));
                        request.setAttribute("symptoms", rs.getString("symptoms"));
                        request.setAttribute("startDate", rs.getString("start_date"));
                        request.setAttribute("isInjured", rs.getBoolean("is_injured"));
                        request.setAttribute("injuryDetails", rs.getString("injury_details"));
                        request.setAttribute("injuryImagePath", rs.getString("injury_image_path"));

                        request.getRequestDispatcher("caseUpdateForm.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Case not found");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void handleCaseUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String caseNumber = request.getParameter("caseNumber");

        try (Connection con = getConnection()) {
            String submittedBy = request.getParameter("submittedBy");
            String symptoms = request.getParameter("symptoms");
            String startDate = request.getParameter("startDate");
            boolean isInjured = "on".equals(request.getParameter("isInjured"));
            String injuryDetails = request.getParameter("injuryDetails");

            Part filePart = request.getPart("injuryImage");
            String fileName = null;
            if (filePart != null && filePart.getSize() > 0) {
                fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                filePart.write("/path/to/uploads/directory/" + fileName);
            } else {
                fileName = request.getParameter("existingInjuryImagePath");
            }

            String sql = "UPDATE cases SET submitted_by = ?, symptoms = ?, start_date = ?, is_injured = ?, injury_details = ?, injury_image_path = ?, status = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, submittedBy);
                pst.setString(2, symptoms);
                pst.setString(3, startDate);
                pst.setBoolean(4, isInjured);
                pst.setString(5, injuryDetails);
                pst.setString(6, fileName);
                pst.setString(7, "Updated by patient"); 
                pst.setString(8, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case updated successfully.");
                    
                    request.getRequestDispatcher("caseConfirmation.jsp").forward(request, response);
                } else {
                    request.setAttribute("statusMessage", "Failed to update case.");
                    forwardToDashboard(request, response);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }


    private void handleCaseStatusChange(HttpServletRequest request, HttpServletResponse response, String caseNumber, String status) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userRole = (String) session.getAttribute("role"); // Assuming the user role is stored in session

        try (Connection con = getConnection()) {
            // Check the current status of the case
            String checkStatusSql = "SELECT status FROM cases WHERE case_number = ?";
            String currentStatus;
            try (PreparedStatement checkPst = con.prepareStatement(checkStatusSql)) {
                checkPst.setString(1, caseNumber);
                try (ResultSet rs = checkPst.executeQuery()) {
                    if (rs.next()) {
                        currentStatus = rs.getString("status");
                    } else {
                        request.setAttribute("statusMessage", "Case not found.");
                        request.setAttribute("statusColor", "red");
                        request.getRequestDispatcher("caseConfirmation.jsp").forward(request, response);
                        return;
                    }
                }
            }

            // Check if the case is already admitted
            if ("Admitted".equalsIgnoreCase(currentStatus) && !"AdmissionOfficer".equalsIgnoreCase(userRole)) {
                request.setAttribute("statusMessage", "Only the Admission Officer can cancel an admitted case.");
                request.setAttribute("statusColor", "red");
                request.getRequestDispatcher("caseConfirmation.jsp").forward(request, response);
                return;
            }

            // Update the case status
            String sql = "UPDATE cases SET status = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case status updated successfully.");
                    request.setAttribute("statusColor", "green");
                } else {
                    request.setAttribute("statusMessage", "Failed to update case status.");
                    request.setAttribute("statusColor", "red");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("statusMessage", "Error: " + e.getMessage());
            request.setAttribute("statusColor", "red");
        }
        request.setAttribute("caseNumber", caseNumber); // Retain the caseNumber
        request.setAttribute("status", status); // Retain the status
        request.getRequestDispatcher("caseConfirmation.jsp").forward(request, response);
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

        try (Connection con = getConnection()) {
            String status = "Assigned";

            String sql = "UPDATE cases SET status = ?, department = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, department);
                pst.setString(3, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Case assigned to " + department + " department successfully.");
                } else {
                    request.setAttribute("statusMessage", "Failed to assign case to department.");
                }
                request.setAttribute("caseNumber", caseNumber);  // Set caseNumber attribute for the redirected page
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

            String sql = "UPDATE cases SET status = ?, transferred_to_hospital = ? WHERE case_number = ?";
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
                request.setAttribute("caseNumber", caseNumber);  // Set caseNumber attribute for the redirected page
                request.getRequestDispatcher("nurseExamineCase.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }
    
    private void displayTreatmentForm(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        request.setAttribute("caseNumber", caseNumber);
        request.getRequestDispatcher("treatmentForm.jsp").forward(request, response);
    }

    private void handleTreatmentSubmission(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        String treatment = request.getParameter("treatment");

        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET treatment = ?, status = 'Closed' WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, treatment);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Treatment entered successfully. Case closed.");
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
    
    private void handleCreateFollowUp(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        try (Connection con = getConnection()) {
            // Update the case status to follow-up
            String sql = "UPDATE cases SET status = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, STATUS_FOLLOW_UP);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();

                if (rowCount > 0) {
                    request.setAttribute("statusMessage", "Follow-up created successfully.");
                } else {
                    request.setAttribute("statusMessage", "Failed to create follow-up.");
                }
                forwardToDashboard(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        }
    }
    

    private void redirectToNurseExamine(HttpServletRequest request, HttpServletResponse response, String caseNumber) throws ServletException, IOException {
        request.setAttribute("caseNumber", caseNumber);
        request.getRequestDispatcher("nurseExamineCase.jsp").forward(request, response);
    }

    private void forwardToDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin");
    }
}
