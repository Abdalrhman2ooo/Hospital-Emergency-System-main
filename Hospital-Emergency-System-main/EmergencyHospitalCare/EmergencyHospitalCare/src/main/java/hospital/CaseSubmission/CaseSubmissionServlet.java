package hospital.CaseSubmission;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.InputStream;

@WebServlet("/case")
@MultipartConfig
public class CaseSubmissionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
  
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String onBehalf = request.getParameter("onBehalf");
        String patientId = request.getParameter("patientId");
        String submittedBy = null; 
        
        if ("on".equals(onBehalf)) {
            String patientName = request.getParameter("patientName"); 
            submittedBy = patientName; 
        } else {
            
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin")) {
                submittedBy = getPatientFirstName(con, patientId);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("Database error: " + e.getMessage());
            }
        }

        String relationship = request.getParameter("relationship");
        String symptoms = request.getParameter("symptoms");
        String startDate = request.getParameter("startDate");
        String isInjuredStr = request.getParameter("isInjured");
        boolean isInjured = Boolean.parseBoolean(isInjuredStr);
        String injuryDetails = request.getParameter("injuryDetails");

        Part filePart = request.getPart("injuryImage");
        String fileName = null;
        String filePath = null;

        if (filePart != null && filePart.getSize() > 0) {
            try {
                fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                
                String uploadDir = "/path/to/uploads/directory/";
                
                Files.createDirectories(Paths.get(uploadDir));
                
                filePath = uploadDir + fileName;
                
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
                
            }
        }

        RequestDispatcher dispatcher = null;
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin");

            
            String newCaseNumber = generateNewCaseNumber(con);

            String sql = "INSERT INTO cases (case_number, submitted_by, relationship_to_patient, symptoms, start_date, is_injured, injury_details, injury_image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, newCaseNumber);
            pst.setString(2, submittedBy); 
            pst.setString(3, relationship);
            pst.setString(4, symptoms);
            pst.setString(5, startDate);
            pst.setBoolean(6, isInjured);
            pst.setString(7, injuryDetails);
            pst.setString(8, filePath);

            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                dispatcher = request.getRequestDispatcher("caseConfirmation.jsp");
                request.setAttribute("status", "New");
                request.setAttribute("caseNumber", newCaseNumber);
            } else {
                dispatcher = request.getRequestDispatcher("case.jsp");
                request.setAttribute("status", "failed");
            }
            dispatcher.forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getPatientFirstName(Connection con, String patientId) throws SQLException {
        String sql = "SELECT first_name FROM patients WHERE patient_id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, patientId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("first_name");
            } else {
                throw new SQLException("Patient ID not found: " + patientId);
            }
        }
    }

    private String generateNewCaseNumber(Connection con) throws SQLException {
        String lastCaseNumber = null;
        String sql = "SELECT case_number FROM cases ORDER BY id DESC LIMIT 1";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                lastCaseNumber = rs.getString("case_number");
            }
        }

        if (lastCaseNumber == null || lastCaseNumber.isEmpty()) {
            return "C00000001";
        } else {
            try {
                int newNumber = Integer.parseInt(lastCaseNumber.substring(1)) + 1;
                return String.format("C%08d", newNumber);
            } catch (NumberFormatException e) {
                throw new SQLException("Error parsing case number: " + lastCaseNumber, e);
            }
        }
    }
}
