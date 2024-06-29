package hospital.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.tomcat.jdbc.pool.DataSource;

public class Case {
    private String caseNumber;
    private String submittedBy;
    private String relationshipToPatient;
    private String symptoms;
    private LocalDateTime startDate;
    private boolean isInjured;
    private String injuryDetails;
    private String injuryImagePath;
    private String status;
    private String priority;
    private String nurseId;
    private String department;
    private String treatment;
    private String transferredToHospital;
    private String comments;

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getRelationshipToPatient() {
        return relationshipToPatient;
    }

    public void setRelationshipToPatient(String relationshipToPatient) {
        this.relationshipToPatient = relationshipToPatient;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public boolean isInjured() {
        return isInjured;
    }

    public void setInjured(boolean isInjured) {
        this.isInjured = isInjured;
    }

    public String getInjuryDetails() {
        return injuryDetails;
    }

    public void setInjuryDetails(String injuryDetails) {
        this.injuryDetails = injuryDetails;
    }

    public String getInjuryImagePath() {
        return injuryImagePath;
    }

    public void setInjuryImagePath(String injuryImagePath) {
        this.injuryImagePath = injuryImagePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getTransferredToHospital() {
        return transferredToHospital;
    }

    public void setTransferredToHospital(String transferredToHospital) {
        this.transferredToHospital = transferredToHospital;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    private Case getCaseDetails(String caseNumber) throws SQLException, NamingException {
        Case caseDetails = null;
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/YourDataSource");
            conn = ds.getConnection();
            
            String query = "SELECT case_number, submitted_by, relationship_to_patient, symptoms, start_date, is_injured, " +
                           "injury_details, injury_image_path, status, priority, nurse_id, department, treatment, " +
                           "transferred_to_hospital, comments FROM your_table WHERE case_number = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, caseNumber);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                caseDetails = new Case();
                caseDetails.setCaseNumber(rs.getString("case_number"));
                caseDetails.setSubmittedBy(rs.getString("submitted_by"));
                caseDetails.setRelationshipToPatient(rs.getString("relationship_to_patient"));
                caseDetails.setSymptoms(rs.getString("symptoms"));
                caseDetails.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
                caseDetails.setInjured(rs.getBoolean("is_injured"));
                caseDetails.setInjuryDetails(rs.getString("injury_details"));
                caseDetails.setInjuryImagePath(rs.getString("injury_image_path"));
                caseDetails.setStatus(rs.getString("status"));
                caseDetails.setPriority(rs.getString("priority"));
                caseDetails.setNurseId(rs.getString("nurse_id"));
                caseDetails.setDepartment(rs.getString("department"));
                caseDetails.setTreatment(rs.getString("treatment"));
                caseDetails.setTransferredToHospital(rs.getString("transferred_to_hospital"));
                caseDetails.setComments(rs.getString("comments"));
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        
        return caseDetails;
    }
}

