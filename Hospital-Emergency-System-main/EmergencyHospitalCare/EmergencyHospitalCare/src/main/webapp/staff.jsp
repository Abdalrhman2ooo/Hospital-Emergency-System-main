<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="hospital.staff.StaffServlet.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Hospital Data Management</title>
    <style>
      body {
    background-image: url('hospital.jpg');
    background-size: cover;
    background-position: center;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #333;
    padding: 20px;
    margin: 0;
}

h1 {
    text-align: center;
    color: #0056b3;
    margin-bottom: 20px;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

h2 {
    color: #0056b3;
    margin-top: 30px;
    margin-bottom: 10px;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

table {
    width: 100%;
    border-collapse: collapse;
    background-color: rgba(255, 255, 255, 0.9);
    margin: 0 auto;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

th, td {
    border: 1px solid #ccc;
    padding: 10px;
    text-align: left;
}

th {
    background-color: #0056b3;
    color: white;
    text-transform: uppercase;
}

tr:nth-child(even) {
    background-color: #f9f9f9;
}

.actions {
    text-align: center;
}

.actions button {
    background-color: #0056b3;
    color: white;
    border: none;
    padding: 5px 10px;
    margin: 2px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s;
}

.actions button:hover {
    background-color: #004494;
}

.form-container {
    background-color: rgba(255, 255, 255, 0.9);
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
    max-width: 600px;
    margin: 20px auto;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
    color: #333;
    font-weight: bold;
}

.form-group input[type="text"] {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    transition: border-color 0.3s;
}

.form-group input[type="text"]:focus {
    border-color: #0056b3;
    outline: none;
}

.form-group input[type="submit"] {
    background-color: #0056b3;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s;
}

.form-group input[type="submit"]:hover {
    background-color: #004494;
}

form {
    padding: 10px 20px;
    flex-direction: column;
    gap: 10px;
}
    </style>
</head>
<body>
    <h1>Hospital Management System</h1>
    <h2>Nurses</h2>
    <table>
        <thead>
            <tr>
                <th>Nurse ID</th>
                <th>Nurse Name</th>
                <th>Nurse Department</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                String url = "jdbc:mysql://localhost:3306/hospital";
                String username = "root";
                String password = "admin";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM nurses");

                    while (resultSet.next()) {
                        int nurseId = resultSet.getInt("nurse_id");
                        String nurseName = resultSet.getString("nurse_name");
                        String nurseSpecialty = resultSet.getString("nurse_specialty");

                        out.println("<tr>");
                        out.println("<td>" + nurseId + "</td>");
                        out.println("<td>" + nurseName + "</td>");
                        out.println("<td>" + nurseSpecialty + "</td>");
                        out.println("<td class='actions'>");
                        out.println("<form action='StaffServlet' method='post' style='display:inline;'>");
                        out.println("<input type='hidden' name='table' value='nurses'>");
                        out.println("<input type='hidden' name='id' value='" + nurseId + "'>");
                        out.println("<input type='hidden' name='action' value='edit'>");
                        out.println("<input type='text' name='name' value='" + nurseName + "'>");
                        out.println("<input type='text' name='specialty' value='" + nurseSpecialty + "'>");
                        out.println("<input type='submit' value='Edit'>");
                        out.println("</form>");
                        out.println("<form action='StaffServlet' method='post' style='display:inline;'>");
                        out.println("<input type='hidden' name='table' value='nurses'>");
                        out.println("<input type='hidden' name='id' value='" + nurseId + "'>");
                        out.println("<input type='hidden' name='action' value='delete'>");
                        out.println("<input type='submit' value='Delete'>");
                        out.println("</form>");
                        out.println("</td>");
                        out.println("</tr>");
                    }

                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (Exception e) {
                    out.println("<p>Error during database connection: " + e.getMessage() + "</p>");
                }
            %>
        </tbody>
    </table>

    <h2>Add Nurse</h2>
<form action="StaffServlet" method="post" class="form-container">
    <input type="hidden" name="table" value="nurses">
    <input type="hidden" name="action" value="add">
    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div class="form-group">
        <label for="specialty">Specialty:</label>
        <input type="text" id="specialty" name="specialty" required>
    </div>
    <div class="form-group">
        <label for="contact">Contact:</label>
        <input type="text" id="contact" name="contact" required>
    </div>
    <div class="form-group">
        <input type="submit" value="Add Nurse">
    </div>
</form>

    <h2>Doctors</h2>
    <table>
        <thead>
            <tr>
                <th>Doctor ID</th>
                <th>Doctor Name</th>
                <th>Doctor Department</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, username, password);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM doctors");

                    while (resultSet.next()) {
                        int doctorId = resultSet.getInt("doctor_id");
                        String doctorName = resultSet.getString("doctor_name");
                        String doctorSpecialty = resultSet.getString("doctor_specialty");

                        out.println("<tr>");
                        out.println("<td>" + doctorId + "</td>");
                        out.println("<td>" + doctorName + "</td>");
                        out.println("<td>" + doctorSpecialty + "</td>");
                        out.println("<td class='actions'>");
                        out.println("<form action='StaffServlet' method='post' style='display:inline;'>");
                        out.println("<input type='hidden' name='table' value='doctors'>");
                        out.println("<input type='hidden' name='id' value='" + doctorId + "'>");
                        out.println("<input type='hidden' name='action' value='edit'>");
                        out.println("<input type='text' name='name' value='" + doctorName + "'>");
                        out.println("<input type='text' name='specialty' value='" + doctorSpecialty + "'>");
                        out.println("<input type='submit' value='Edit'>");
                        out.println("</form>");
                        out.println("<form action='StaffServlet' method='post' style='display:inline;'>");
                        out.println("<input type='hidden' name='table' value='doctors'>");
                        out.println("<input type='hidden' name='id' value='" + doctorId + "'>");
                        out.println("<input type='hidden' name='action' value='delete'>");
                        out.println("<input type='submit' value='Delete'>");
                        out.println("</form>");
                        out.println("</td>");
                        out.println("</tr>");
                    }

                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (Exception e) {
                    out.println("<p>Error during database connection: " + e.getMessage() + "</p>");
                }
            %>
        </tbody>
    </table>

   <h2>Add Doctor</h2>
<form action="StaffServlet" method="post" class="form-container">
    <input type="hidden" name="table" value="doctors">
    <input type="hidden" name="action" value="add">
    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div class="form-group">
        <label for="specialty">Specialty:</label>
        <input type="text" id="specialty" name="specialty" required>
    </div>
    <div class="form-group">
        <label for="contact">Contact:</label>
        <input type="text" id="contact" name="contact" required>
    </div>
    <div class="form-group">
        <input type="submit" value="Add Doctor">
    </div>
</form>
</body>
</html>