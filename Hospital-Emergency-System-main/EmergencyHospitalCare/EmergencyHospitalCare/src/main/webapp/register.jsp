<!DOCTYPE html>
<html>
<head>
    <title>Patient Registration</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Patient Registration</h2>
    <form action="registerpatient" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required><br>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required><br>

        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" required><br>

        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required><br>

        <label for="medicalHistory">Medical History:</label>
        <input type="text" id="medicalHistory" name="medicalHistory" required><br>

        <label for="chronicDiseases">Chronic Diseases:</label>
        <input type="text" id="chronicDiseases" name="chronicDiseases" required><br>

        <label for="allergies">Allergies:</label>
        <input type="text" id="allergies" name="allergies" required><br>

        <label for="mail">Email:</label>
        <input type="email" id="mail" name="mail" required><br>

        <label for="phoneNumber">Phone Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" required><br>

        <input type="submit" value="Register">
    </form>

    <div>
        <% 
            String status = (String) request.getAttribute("status");
            String patientId = (String) request.getAttribute("patientId");
            if (status != null) {
                if (status.equals("success")) {
                    out.println("<p>Registration successful! Your Patient ID is " + patientId + "</p>");
                } else {
                    out.println("<p>Registration failed. Please try again.</p>");
                }
            }
        %>
    </div>
</body>
</html>
