<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Treatment for Case</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            color: #333;
        }

        header {
            background-color: #f2f2f2;
            padding: 20px 0;
        }

        .container {
            background-color: rgba(255, 255, 255, 0.8);
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

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        select, input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
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
            transition: background-color 0.2s ease-in-out;
        }

        button[type="submit"]:hover {
            background-color: #004494;
        }
    </style>
</head>
<body>
    <header></header>

    <main class="container">
        <h1>Treatment for Case #<%= request.getAttribute("caseNumber") %></h1>

        <form action="manageNurse" method="post">
            <input type="hidden" name="action" value="submitTreatment">
            <input type="hidden" name="caseNumber" value="<%= request.getAttribute("caseNumber") %>">

            <div class="form-group">
                <label for="treatment">Select Treatment:</label>
                <select id="treatment" name="treatment">
                    <option value="1">Heart</option>
                    <option value="2">Teeth</option>
                    <option value="3">Diabetes</option>
                    <option value="4">Eye</option>
                </select>
            </div>

            <div class="form-group">
                <label for="doctorName">Doctor Name:</label>
                <select id="doctorName" name="doctorName">
                    <!-- Embedding Java code to fetch doctor names directly from the database -->
                    <%
                    Connection conn = null;
                    Statement st = null;
                    ResultSet rs = null;

                    try {
                        // Database connection
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin");
                        st = conn.createStatement();

                        // SQL query to fetch doctor names from the doctors table
                        String qry = "SELECT doctor_name FROM doctors";
                        rs = st.executeQuery(qry);

                        // Loop through the result set and populate the dropdown options
                        while (rs.next()) {
                    %>
                    <option value="<%= rs.getString("doctor_name") %>"><%= rs.getString("doctor_name") %></option>
                    <%
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        // Close the database connections
                        try {
                            if (rs != null) rs.close();
                            if (st != null) st.close();
                            if (conn != null) conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    %>
                </select>
            </div>

            <button type="submit">Submit Treatment</button>
        </form>
         <form action="nurseDashboard.jsp" method="get">
        <button type="submit">Go Back to Nurse Dashboard</button>
        </form>
    </main>
</body>
</html>
