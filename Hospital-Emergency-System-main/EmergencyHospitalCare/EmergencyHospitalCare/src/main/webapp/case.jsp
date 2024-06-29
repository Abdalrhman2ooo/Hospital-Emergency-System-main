<!DOCTYPE html>
<html>
<head>
    <title>Submit Case</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Submit a Case</h2>
    <form action="case" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
        <label>
            Are you submitting on behalf of someone else?
            <input type="checkbox" name="onBehalf" id="onBehalf"/>
        </label><br/>
        <div id="onBehalfDetails" style="display:none;">
            <label for="patientName">Patient's Name:</label>
            <input type="text" name="patientName" id="patientName"/><br/>
            <label for="relationship">Relationship to Patient:</label>
            <select name="relationship" id="relationship">
            	<option value="Self">Self</option>
                <option value="Friend">Friend</option>
                <option value="Family">Family</option>
            </select><br/>
        </div>
        <input type="hidden" name="patientId" id="patientId" value="${patientId}"/> <!-- Ensure patientId is set correctly -->
        <label for="symptoms">Symptoms:</label>
        <textarea name="symptoms" id="symptoms" required></textarea><br/>
        <label for="startDate">When did it start:</label>
        <input type="date" name="startDate" id="startDate" required/><br/>
        <label>Is the patient injured?</label>
        <label for="injuredYes">Yes</label>
        <input type="radio" name="isInjured" id="injuredYes" value="true" required/>
        <label for="injuredNo">No</label>
        <input type="radio" name="isInjured" id="injuredNo" value="false" required/><br/>
        <div id="injuryDetails" style="display:none;">
            <label for="injuryDetailsInput">Type of injury:</label>
            <input type="text" name="injuryDetails" id="injuryDetailsInput"/><br/>
            <label for="injuryImageInput">Upload an image:</label>
            <input type="file" name="injuryImage" id="injuryImageInput"/><br/>
        </div>
        <input type="submit" value="Submit Case"/>
    </form>
    <script>
        document.getElementById('onBehalf').addEventListener('change', function() {
            document.getElementById('onBehalfDetails').style.display = this.checked ? 'block' : 'none';
        });
        document.querySelectorAll('input[name="isInjured"]').forEach(function(elem) {
            elem.addEventListener('change', function() {
                document.getElementById('injuryDetails').style.display = this.value === 'true' ? 'block' : 'none';
            });
        });

        function validateForm() {
            
            return true;
        }
    </script>
</body>
</html>
