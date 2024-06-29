<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Select Doctor for Follow-up</title>
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
        h2 {
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
        <h2>Select Doctor for Follow-up</h2>
        <form action="manageAdmin" method="post">
            <input type="hidden" name="action" value="createFollowup">
            <input type="hidden" name="caseNumber" value="${param.caseNumber}">
            <div class="form-group">
                <label for="doctor">Select Doctor:</label>
                <select name="doctor" id="doctor" required>
                    <option value="">Select...</option>
                    <option value="Dr.suzan">Dr.suzan</option>
                    <option value="Dr. louay">Dr. louay</option>
                    <option value="Dr. ahmad">Dr. ahmad</option>
                    <!-- Add more options for other doctors -->
                </select>
            </div>
            <button type="submit">Assign Doctor</button>
        </form>
    </div>
</body>
</html>
