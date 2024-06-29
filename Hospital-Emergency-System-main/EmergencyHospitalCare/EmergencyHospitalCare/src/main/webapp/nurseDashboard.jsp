<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Nurse Dashboard</title>
    <style>
        body {
            background-image: url('hospital.jpg');
            background-size: cover;
            background-position: center;
            font-family: Arial, sans-serif;
            color: #333;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #0056b3;
            margin-bottom: 20px;
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
            background-color: #f2f2f2;
            color: #0056b3;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
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
        }
        .actions button:hover {
            background-color: #004494;
        }
    </style>
    <script>
        function handleAction(action, formId) {
            const form = document.getElementById(formId);
            if (action === 'transferToAnotherHospital') {
                form.action = 'transferCase.jsp';
            } else {
                form.action = 'manageNurse';
            }
            form.querySelector('input[name="action"]').value = action;
            form.submit();
        }
    </script>
</head>
<body>
    <h1>Nurse Dashboard</h1>
    <div>
        <p><%= request.getAttribute("statusMessage") != null ? request.getAttribute("statusMessage") : "" %></p>
        <table>
            <tr>
                <th>Case Number</th>
                <th>Patient ID</th>
                <th>Submitted By</th>
                <th>Relationship to Patient</th>
                <th>Symptoms</th>
                <th>Start Date</th>
                <th>Injured</th>
                <th>Injury Image</th>
                <th>Status</th>
                <th>Priority</th>
                <th>Nurse</th>
                <th>Department</th>
                <th>Actions</th>
            </tr>
            <% 
            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin");
                st = conn.createStatement();

                String qry = "SELECT * FROM hospital.cases";
                rs = st.executeQuery(qry);

                while (rs.next()) {
                    String caseNumber = rs.getString("case_number");
            %>
            <tr>
                <td><%= caseNumber %></td>
                <td><%= rs.getString("patient_id") %></td>
                <td><%= rs.getString("submitted_by") %></td>
                <td><%= rs.getString("relationship_to_patient") %></td>
                <td><%= rs.getString("symptoms") %></td>
                <td><%= rs.getString("start_date") %></td>
                <td><%= rs.getBoolean("is_injured") ? "Yes" : "No" %></td>
                <td><%= rs.getString("injury_image_path") %></td>
                <td><%= rs.getString("status") %></td>
                <td><%= rs.getString("priority") %></td>
                <td><%= rs.getString("nurse_name") %></td>
                <td><%= rs.getString("department") %></td>
                <td class="actions">
                    <form id="form_<%= caseNumber %>" method="post" action="manageNurse">
                        <input type="hidden" name="caseNumber" value="<%= caseNumber %>">
                        <input type="hidden" name="action" value="">
                        <button type="button" onclick="handleAction('initialAssessment', 'form_<%= caseNumber %>')">Initial Assessment</button>
                        <button type="button" onclick="handleAction('assignToDepartment', 'form_<%= caseNumber %>')">Assign to Department</button>
                        <button type="button" onclick="handleAction('enterTreatment', 'form_<%= caseNumber %>')">Enter Treatment</button>
                        <button type="button" onclick="handleAction('TransferToHospital', 'form_<%= caseNumber %>')">Transfer to Another Hospital</button>
                        <button type="button" onclick="handleAction('endTreatment', 'form_<%= caseNumber %>')">End Treatment</button>
                        <button type="button" onclick="handleAction('close', 'form_<%= caseNumber %>')">Close</button>
                    </form>
                </td>
            </tr>
            <% 
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (st != null) try { st.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
            %>
        </table>
    </div>
</body>
</html>
