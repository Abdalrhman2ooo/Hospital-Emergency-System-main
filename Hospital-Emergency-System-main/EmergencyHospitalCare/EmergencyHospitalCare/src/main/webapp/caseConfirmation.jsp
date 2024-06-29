<!DOCTYPE html>
<html>
<head>
    <title>Case Confirmation</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div id="design">
    <h2>Case Submission Confirmation</h2>
    <p>Your case has been submitted successfully!</p>
    <p>Case Number: ${caseNumber}</p>
    <p>Status: ${status}</p>
    
    <form action="manageCase" method="post">
        <input type="hidden" name="caseNumber" value="${caseNumber}">
        <input type="hidden" name="action" value="update">
        <input type="submit" value="Update Case">
    </form>
    
    <form action="manageCase" method="post">
        <input type="hidden" name="caseNumber" value="${caseNumber}">
        <input type="hidden" name="action" value="close">
        <input type="submit" value="Cancel Case">
    </form>

    <% if (request.getAttribute("statusMessage") != null) { %>
        <p style="color: <%= request.getAttribute("statusColor") %>;">
            <%= request.getAttribute("statusMessage") %>
        </p>
    <% } %>
    
    <form action="register.jsp" method="get">
        <input type="submit" value="Register New Case">
    </form>
</div>
</body>
</html>
