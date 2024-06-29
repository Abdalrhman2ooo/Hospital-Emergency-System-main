<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="hospital.model.Case" %>
<%
    Case caseObj = (Case) request.getAttribute("case");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Case</title>
</head>
<body>
    <h1>Update Case</h1>
    <form action="manageCase" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="submitUpdate">
        <input type="hidden" name="caseNumber" value="<%= caseObj.getCaseNumber() %>">
        <label for="submittedBy">Submitted By:</label>
        <input type="text" id="submittedBy" name="submittedBy" value="<%= caseObj.getSubmittedBy() %>"><br>
        <label for="relationshipToPatient">Relationship to Patient:</label>
        <input type="text" id="relationshipToPatient" name="relationshipToPatient" value="<%= caseObj.getRelationshipToPatient() %>"><br>
        <label for="symptoms">Symptoms:</label>
        <input type="text" id="symptoms" name="symptoms" value="<%= caseObj.getSymptoms() %>"><br>
        <label for="startDate">Start Date:</label>
        <input type="text" id="startDate" name="startDate" value="<%= caseObj.getStartDate() %>"><br>
        <label for="isInjured">Is Injured:</label>
        <input type="checkbox" id="isInjured" name="isInjured" <%= caseObj.isInjured() ? "checked" : "" %>><br>
        <label for="injuryDetails">Injury Details:</label>
        <input type="text" id="injuryDetails" name="injuryDetails" value="<%= caseObj.getInjuryDetails() %>"><br>
        <label for="injuryImage">Injury Image:</label>
        <input type="file" id="injuryImage" name="injuryImage"><br>
        <label for="status">Status:</label>
        <input type="text" id="status" name="status" value="<%= caseObj.getStatus() %>"><br>
        <label for="priority">Priority:</label>
        <input type="text" id="priority" name="priority" value="<%= caseObj.getPriority() %>"><br>
        <label for="nurseId">Nurse ID:</label>
        <input type="text" id="nurseId" name="nurseId" value="<%= caseObj.getNurseId() %>"><br>
        <label for="department">Department:</label>
        <input type="text" id="department" name="department" value="<%= caseObj.getDepartment() %>"><br>
        <label for="treatment">Treatment:</label>
        <input type="text" id="treatment" name="treatment" value="<%= caseObj.getTreatment() %>"><br>
        <label for="transferredToHospital">Transferred to Hospital:</label>
        <input type="text" id="transferredToHospital" name="transferredToHospital" value="<%= caseObj.getTransferredToHospital() %>"><br>
        <label for="comments">Comments:</label>
        <textarea id="comments" name="comments"><%= caseObj.getComments() %></textarea><br>
        <button type="submit">Update Case</button>
    </form>
</body>
</html>
