package hospital.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CaseModel {

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin");
    }

    public void checkAndCancelOverdueCases() throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection()) {
            String selectSql = "SELECT case_number, start_date FROM cases WHERE status = 'NEW'";
            try (PreparedStatement selectPst = con.prepareStatement(selectSql);
                 ResultSet rs = selectPst.executeQuery()) {

                while (rs.next()) {
                    String caseNumber = rs.getString("case_number");
                    LocalDateTime startDate = rs.getTimestamp("start_date").toLocalDateTime();
                    LocalDateTime now = LocalDateTime.now();
                    
                    long hoursDifference = ChronoUnit.HOURS.between(startDate, now);
                    
                    if (hoursDifference >= 1) {
                        String updateSql = "UPDATE cases SET status = 'CANCELED' WHERE case_number = ?";
                        try (PreparedStatement updatePst = con.prepareStatement(updateSql)) {
                            updatePst.setString(1, caseNumber);
                            updatePst.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    public boolean updateCase(String caseNumber, String status, String priority, String comments) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET status = ?, priority = ?, comments = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, priority);
                pst.setString(3, comments);
                pst.setString(4, caseNumber);

                int rowCount = pst.executeUpdate();
                return rowCount > 0;
            }
        }
    }

    // Add other database operations here similar to updateCase

    public ResultSet getCaseDetails(String caseNumber) throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        String sql = "SELECT * FROM cases WHERE case_number = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, caseNumber);
        return pst.executeQuery();
    }

    public boolean changeCaseStatus(String caseNumber, String status) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET status = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, status);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();
                return rowCount > 0;
            }
        }
    }

    public boolean assignToNurse(String caseNumber, String nurseId) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET status = 'Assign to nurse', nurse_id = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, nurseId);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();
                return rowCount > 0;
            }
        }
    }

    public boolean assignToDepartment(String caseNumber, String department) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET status = 'Assigned', department = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, department);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();
                return rowCount > 0;
            }
        }
    }

    public boolean transferToHospital(String caseNumber, String hospitalName) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET status = 'Transferred', transferred_to_hospital = ? WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, hospitalName);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();
                return rowCount > 0;
            }
        }
    }

    public boolean submitTreatment(String caseNumber, String treatment) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET treatment = ?, status = 'Closed' WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, treatment);
                pst.setString(2, caseNumber);

                int rowCount = pst.executeUpdate();
                return rowCount > 0;
            }
        }
    }

    public boolean createFollowUp(String caseNumber) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE cases SET status = 'Follow-up' WHERE case_number = ?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, caseNumber);

                int rowCount = pst.executeUpdate();
                return rowCount > 0;
            }
        }
    }
}
