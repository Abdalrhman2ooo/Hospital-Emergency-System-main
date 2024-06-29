<!DOCTYPE html>
<html>
<head>
    <title>Update Case</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Update Case Details</h2>
    <form action="manageCase" method="post" enctype="multipart/form-data">
        <input type="hidden" name="caseNumber" value="${caseNumber}">
        <input type="hidden" name="action" value="submitUpdate">

        <label for="submittedBy">Submitted By:</label>
        <input type="text" id="submittedBy" name="submittedBy" value="${submittedBy}" readonly><br>

        <label for="relationshipToPatient">Relationship to Patient:</label>
        <input type="text" id="relationshipToPatient" name="relationshipToPatient" value="${relationshipToPatient}" readonly><br>

        <label for="symptoms">Symptoms:</label>
        <input type="text" id="symptoms" name="symptoms" value="${symptoms}"><br>

        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate" value="${startDate}"><br>

        <label for="isInjured">Is Injured:</label>
        <input type="checkbox" id="isInjured" name="isInjured" ${isInjured ? 'checked' : ''}><br>

        <label for="injuryDetails">Injury Details:</label>
        <textarea id="injuryDetails" name="injuryDetails">${injuryDetails}</textarea><br>

        <label for="injuryImage">Injury Image:</label>
        <input type="file" id="injuryImage" name="injuryImage"><br>
        <img src="/path/to/uploads/directory/${injuryImagePath}" alt="Injury Image" width="100"><br>
        <input type="hidden" name="existingInjuryImagePath" value="${injuryImagePath}">

        <input type="submit" value="Update Case">
    </form>
</body>
</html>
