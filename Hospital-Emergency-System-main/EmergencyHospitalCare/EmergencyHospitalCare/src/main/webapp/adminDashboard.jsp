<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %> <!-- Importing the java.sql package for database operations -->
<%@ page import="hospital.CaseManagement.CaseManagementServlet" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admission Office Dashboard</title>
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
</head>
<body>
    <h1>Admission Office Dashboard</h1>
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
                <th>Patient Present</th>
                <th>Status</th>
                <th>Priority</th>
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
                	 String status = rs.getString("status");
            %>
            <tr>
                <td><%=rs.getString("case_number") %></td>
                <td><%=rs.getString("patient_id") %></td>
                <td><%=rs.getString("submitted_by") %></td>
                <td><%=rs.getString("relationship_to_patient") %></td>
                <td><%=rs.getString("symptoms") %></td>
                <td><%=rs.getString("start_date") %></td>
                <td><%=rs.getString("is_injured") %></td>
                <td><%=rs.getString("injury_image_path") %></td>
                 <td>
                    <input type="checkbox" name="patientPresent" value="yes" 
                       <%= ("Admitted".equals(status) || "Initial Assessment".equals(status)) ? "checked disabled" : "" %> > 
                </td>
                <td><%=status %></td>
                <td><%=rs.getString("priority") %></td>
                <td class="actions">
                    <form method="post" action="manageAdmin">
                        <input type="hidden" name="caseNumber" value="<%= rs.getString("case_number") %>">
                        <button type="submit" name="action" value="reopen">Reopen</button>
                         <button type="submit" name="action" value="admit">Admit</button>
                         <button type="submit" name="action" value="close">Cancel</button>
                        <button type="submit" name="action" value="assignToNurse">Assign to Nurse</button>
                        <button type="submit" name="action" value="reject">Reject</button>
                    </form>
                    <form method="post" action="createFollowUp.jsp">
                        <input type="hidden" name="caseNumber" value="<%= rs.getString("case_number") %>">
                        <button type="submit">Create Follow-up</button>
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
