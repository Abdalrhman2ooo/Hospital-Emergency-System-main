package hospital.patient.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registerpatient")
public class PatientRegistryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fName = request.getParameter("firstName");
        String lName = request.getParameter("lastName");
        String birth = request.getParameter("dob");
        String add = request.getParameter("address");
        String mHistory = request.getParameter("medicalHistory");
        String cDiseases = request.getParameter("chronicDiseases");
        String allergy = request.getParameter("allergies");
        String mail = request.getParameter("mail");
        String pNumber = request.getParameter("phoneNumber");

        Patient patient = new Patient();
        patient.setFirstName(fName);
        patient.setLastName(lName);
        patient.setDob(birth);
        patient.setAddress(add);
        patient.setMedicalHistory(mHistory);
        patient.setChronicDiseases(cDiseases);
        patient.setAllergies(allergy);
        patient.setEmail(mail);
        patient.setPhoneNumber(pNumber);

        PatientDAO patientDAO = new PatientDAO();
        boolean isRegistered = patientDAO.registerPatient(patient);

        RequestDispatcher dispatcher;
        if (isRegistered) {
            dispatcher = request.getRequestDispatcher("patientProfile.jsp");
            request.setAttribute("status", "success");
            request.setAttribute("patient", patient);
        } else {
            dispatcher = request.getRequestDispatcher("register.jsp");
            request.setAttribute("status", "failed");
        }
        dispatcher.forward(request, response);
    }
}
