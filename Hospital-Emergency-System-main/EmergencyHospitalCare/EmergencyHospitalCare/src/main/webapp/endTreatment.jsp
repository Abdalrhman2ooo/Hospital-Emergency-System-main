<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Enter Treatment</title>
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
    <h2>Enter Treatment</h2>
   <form action="manageNurse" method="post">
        <input type="hidden" name="action" value="submitTreatment">
        <input type="hidden" name="caseNumber" value="${caseNumber}">
        <label for="treatmentType">Select Treatment Type:</label>
        <select name="treatmentType" id="treatmentType" required>
            <option value="">Select...</option>
            <option value="Discharge home with instructions for follow-up care">Discharge home with instructions for follow-up care</option>
            <option value="Admission to the hospital for further observation or treatment">Admission to the hospital for further observation or treatment</option>
            <option value="Transfer to another hospital for specialized care">Transfer to another hospital for specialized care</option>
            <option value="Referral to outpatient services">Referral to outpatient services</option>
        </select><br>
        <button type="submit">Submit</button>
    </form>
    <p><a href="nurseDashboard.jsp">Back to Dashboard</a></p>
</body>
</html>
