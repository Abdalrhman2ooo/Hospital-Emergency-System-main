<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Initial Assessment</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        select, textarea, input[type="text"] {
            width: 100%;
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
            cursor: pointer;
            border-radius: 4px;
            margin-top: 10px;
        }
        button[type="submit"]:hover {
            background-color: #004494;
        }
        .priority-1 {
            color: red;
        }
        .priority-2 {
            color: orange;
        }
        .priority-3 {
            color: yellow;
        }
        .priority-4 {
            color: green;
        }
        .priority-5 {
            color: blue;
        }
    </style>
    <script>
        function updateSelectColor(selectElement) {
            const priorityClasses = ['priority-1', 'priority-2', 'priority-3', 'priority-4', 'priority-5'];
            const selectedIndex = selectElement.selectedIndex;
            
            // Remove all priority classes
            priorityClasses.forEach(priorityClass => {
                selectElement.classList.remove(priorityClass);
            });

            // Add the class for the selected priority
            if (selectedIndex >= 0) {
                selectElement.classList.add(priorityClasses[selectedIndex]);
            }
        }

        document.addEventListener('DOMContentLoaded', function() {
            const selectElement = document.getElementById('priority');
            updateSelectColor(selectElement);

            selectElement.addEventListener('change', function() {
                updateSelectColor(selectElement);
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h2>Initial Assessment</h2>

    <form method="post" action="manageNurse">
        <input type="hidden" name="action" value="submitInitialAssessment">
        <input type="hidden" name="caseNumber" value="<%= request.getParameter("caseNumber") %>">

        <div class="form-group">
            <label for="caseNumber">Case Number:</label>
            <input type="text" id="caseNumber" name="caseNumber" value="<%= request.getParameter("caseNumber") %>" readonly>
        </div>

        <div class="form-group">
            <label for="priority">Select Priority:</label>
            <select id="priority" name="priority">
                <option value="1" class="priority-1"><%= application.getAttribute("priority1").toString().split(":")[0] %> (<%= application.getAttribute("priority1").toString().split(":")[1] %>) - <%= application.getAttribute("priority1").toString().split(":")[2] %></option>
                <option value="2" class="priority-2"><%= application.getAttribute("priority2").toString().split(":")[0] %> (<%= application.getAttribute("priority2").toString().split(":")[1] %>) - <%= application.getAttribute("priority2").toString().split(":")[2] %></option>
                <option value="3" class="priority-3"><%= application.getAttribute("priority3").toString().split(":")[0] %> (<%= application.getAttribute("priority3").toString().split(":")[1] %>) - <%= application.getAttribute("priority3").toString().split(":")[2] %></option>
                <option value="4" class="priority-4"><%= application.getAttribute("priority4").toString().split(":")[0] %> (<%= application.getAttribute("priority4").toString().split(":")[1] %>) - <%= application.getAttribute("priority4").toString().split(":")[2] %></option>
                <option value="5" class="priority-5"><%= application.getAttribute("priority5").toString().split(":")[0] %> (<%= application.getAttribute("priority5").toString().split(":")[1] %>) - <%= application.getAttribute("priority5").toString().split(":")[2] %></option>
            </select>
        </div>

        <div class="form-group">
            <label for="comments">Comments:</label>
            <textarea id="comments" name="comments" rows="5"></textarea>
        </div>

        <div class="form-group">
            <button type="submit">Submit Assessment</button>
        </div>
    </form>

    <!-- Button to go back to nurseDashboard.jsp -->
    <form action="nurseDashboard.jsp" method="get">
        <button type="submit">Go Back to Nurse Dashboard</button>
    </form>
</div>
</body>
</html>
