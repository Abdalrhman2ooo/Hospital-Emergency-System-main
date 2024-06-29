<!DOCTYPE html>
<html>
<head>
    <title>Edit Case</title>
      <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Edit Case</h2>
    <form action="manageCase;jsessionid=${pageContext.session.id}" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="caseNumber" value="${caseNumber}">
        
        <label for="symptoms">Symptoms:</label>
        <textarea name="symptoms" id="symptoms" required>${symptoms}</textarea><br/>
        
        <label for="startDate">When did it start:</label>
        <input type="date" name="startDate" id="startDate" value="${startDate}" required/><br/>
        
        <label>Is the patient injured?</label>
        <label for="injuredYes">Yes</label>
        <input type="radio" name="isInjured" id="injuredYes" value="true" ${isInjured ? 'checked' : ''} required/>
        <label for="injuredNo">No</label>
        <input type="radio" name="isInjured" id="injuredNo" value="false" ${!isInjured ? 'checked' : ''} required/><br/>
        
        <div id="injuryDetails" style="display: ${isInjured ? 'block' : 'none'};">
            <label for="injuryDetailsInput">Type of injury:</label>
            <input type="text" name="injuryDetails" id="injuryDetailsInput" value="${injuryDetails}"/><br/>
            <label for="injuryImageInput">Upload an image:</label>
            <input type="file" name="injuryImage" id="injuryImageInput"/><br/>
        </div>
        
        <input type="submit" value="Update Case"/>
    </form>

    <form action="manageCase;jsessionid=${pageContext.session.id}" method="post">
        <input type="hidden" name="action" value="close">
        <input type="hidden" name="caseNumber" value="${caseNumber}">
        <input type="submit" value="Close Case"/>
    </form>
    
    <script>
        document.querySelectorAll('input[name="isInjured"]').forEach(function(elem) {
            elem.addEventListener('change', function() {
                document.getElementById('injuryDetails').style.display = this.value === 'true' ? 'block' : 'none';
            });
        });
    </script>
</body>
</html>
