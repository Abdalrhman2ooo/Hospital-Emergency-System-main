package hospital.patient.registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "admin";

    private static final Object lock = new Object();

    public boolean registerPatient(Patient patient) {
        synchronized(lock) {
            String sql = "INSERT INTO patients (patient_id, first_name, last_name, dob, address, medical_history, chronic_diseases, allergies, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int patientId = generatePatientId();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
                     PreparedStatement pst = conn.prepareStatement(sql)) {

                    pst.setInt(1, patientId);
                    pst.setString(2, patient.getFirstName());
                    pst.setString(3, patient.getLastName());
                    pst.setString(4, patient.getDob());
                    pst.setString(5, patient.getAddress());
                    pst.setString(6, patient.getMedicalHistory());
                    pst.setString(7, patient.getChronicDiseases());
                    pst.setString(8, patient.getAllergies());
                    pst.setString(9, patient.getEmail());
                    pst.setString(10, patient.getPhoneNumber());

                    int rowCount = pst.executeUpdate();
                    if (rowCount > 0) {
                        patient.setPatientId(String.valueOf(patientId));
                    }
                    return rowCount > 0;
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private synchronized int generatePatientId() {
        String sql = "SELECT MAX(patient_id) AS max_id FROM patients";
        int lastId = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
                 PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    lastId = rs.getInt("max_id");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return lastId + 1;
    }
}
