<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="hospital.patient.registration.Patient" %>
<!DOCTYPE html>
<html>
<head>
    <title>Patient Profile</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <style>
        body {
            background-image: url('hospital.jpg');
            background-size: cover;
            background-position: center;
            font-family: Arial, sans-serif;
            color: #333;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #0056b3;
        }

        #profile {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 10px;
            max-width: 600px;
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="submit"] {
            background-color: #0056b3;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #004494;
        }
    </style>
</head>
<body>
    <h2>Patient Profile</h2>
    <div id="profile">
        <%
            Patient patient = (Patient) request.getAttribute("patient");
            String status = (String) request.getAttribute("status");

            if (status != null && status.equals("success")) {
        %>
            <p>First Name: <%= patient.getFirstName() %></p>
            <p>Last Name: <%= patient.getLastName() %></p>
            <p>Date of Birth: <%= patient.getDob() %></p>
            <p>Address: <%= patient.getAddress() %></p>
            <p>Medical History: <%= patient.getMedicalHistory() %></p>
            <p>Chronic Diseases: <%= patient.getChronicDiseases() %></p>
            <p>Allergies: <%= patient.getAllergies() %></p>
            <p>Email: <%= patient.getEmail() %></p>
            <p>Phone Number: <%= patient.getPhoneNumber() %></p>
            <form action="case.jsp" method="post">
                <input type="submit" value="Submit a Case">
            </form>
        <%
            } else {
                out.println("<p>There was an error displaying your profile. Please try again.</p>");
            }
        %>
    </div>
</body>
</html>
