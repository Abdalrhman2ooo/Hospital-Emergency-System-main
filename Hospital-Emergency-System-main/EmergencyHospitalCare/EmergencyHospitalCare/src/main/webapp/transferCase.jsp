<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transfer case</title>
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
        h1, h2 {
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
        input[type="text"],
        select {
            width: calc(100% - 20px);
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
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
        .statusMessage {
            text-align: center;
            margin: 20px 0;
            font-size: 18px;
        }
        .back-link {
            text-align: center;
            margin-top: 20px;
        }
        .back-link a {
            color: #0056b3;
            text-decoration: none;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Transfer Case</h2>
        <p>Case Number: <%= request.getAttribute("caseNumber") %></p>

        <!-- Form to assign the case to a department -->
        <form action="manageNurse" method="post">
            <input type="hidden" name="action" value="assignToDepartment">
            <input type="hidden" name="caseNumber" value="<%= request.getAttribute("caseNumber") %>">
            <div class="form-group">
                <label for="department">Assign to Department:</label>
                <select name="department" id="department" required>
                    <option value="Emergency">Emergency</option>
                    <option value="Cardiology">Cardiology</option>
                    <option value="Neurology">Neurology</option>
                    <!-- Add more departments as needed -->
                </select>
            </div>
            
        </form>

        <!-- Form to transfer the case to another hospital -->
        <form action="manageNurse" method="post">
            <input type="hidden" name="action" value="TransferToHospital">
            <input type="hidden" name="caseNumber" value="<%= request.getAttribute("caseNumber") %>">
            <div class="form-group">
                <label for="hospitalName">Transfer to Hospital:</label>
                <input type="text" name="hospitalName" id="hospitalName" required>
            </div>
            <button type="submit">Transfer</button>
        </form>

        <!-- Display status message if any -->
     

        <div class="back-link">
            <p><a href="nurseDashboard.jsp">Back to Dashboard</a></p>
        </div>
        <form action="nurseDashboard.jsp" method="get">
            <button type="submit">Go Back to Nurse Dashboard</button>
        </form>
    </div>
</body>
</html>
