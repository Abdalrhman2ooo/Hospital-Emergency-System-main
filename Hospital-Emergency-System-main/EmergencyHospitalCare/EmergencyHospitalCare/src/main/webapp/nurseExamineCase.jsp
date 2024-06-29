<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %> <!-- Importing the java.sql package for database operations -->


<!DOCTYPE html>
<html>
<head>
    <title>Examine Case</title>
    <style>
        body {
            background-image: url('hospital.jpg');
            background-size: cover;
            background-position: center;
            font-family: 'Arial', sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .container {
            background-color: rgba(255, 255, 255, 0.9);
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1, h2, h3 {
            text-align: center;
            color: #0056b3;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            color: #0056b3;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        input[type="text"],
        select {
            width: calc(100% - 22px);
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button[type="submit"] {
            background-color: #0056b3;
            color: white;
            border: none;
            padding: 10px 15px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
            margin-top: 10px;
        }
        button[type="submit"]:hover {
            background-color: #004494;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Examine Case</h2>
        <form action="manageNurse" method="post">
            <input type="hidden" name="action" value="assignToDepartment">
            <input type="hidden" name="caseNumber" value="<%= request.getAttribute("caseNumber") %>">
            
            <div class="form-group">
                <label for="department">Assign to Department:</label>
                <select name="department" id="department">
                    <option value="Triage">Triage</option>
                    <option value="Treatment Rooms">Treatment Rooms</option>
                    <option value="Resuscitation Area">Resuscitation Area</option>
                    <option value="Diagnostic Imaging">Diagnostic Imaging</option>
                    <option value="Laboratory">Laboratory</option>
                    <option value="Observation Area">Observation Area</option>
                    <option value="Psychiatric Emergency Services">Psychiatric Emergency Services</option>
                </select>
            </div>

            <div class="form-group">
                <label for="nurse">Assign to Nurse:</label>
                <select name="nurseName" id="nurse">
                    <% 
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin");
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT nurse_name FROM nurses");
                            
                            while (rs.next()) {
                                String nurseName = rs.getString("nurse_name");
                    %>
                                <option value="<%= nurseName %>"><%= nurseName %></option>
                    <%
                            }
                            rs.close();
                            stmt.close();
                            con.close();
                        } catch (Exception e) {
                            out.println("Exception: " + e);
                        }
                    %>
                </select>
            </div>
            
            <button type="submit">Assign</button>
            
        </form>
         <!-- Button to go back to nurseDashboard.jsp -->
    <form action="nurseDashboard.jsp" method="get">
        <button type="submit">Go Back to Nurse Dashboard</button>
    </form>
    </div>
</body>
</html>
