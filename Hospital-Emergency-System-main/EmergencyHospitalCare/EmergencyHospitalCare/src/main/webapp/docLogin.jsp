<!DOCTYPE html>
<html>
<head>
    <title>Doctor Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4; /* Light gray background */
            padding: 20px;
            margin: 0;
        }
        h1 {
            text-align: center;
            color: #0056b3; /* Dark blue color */
        }
        form {
            width: 300px;
            margin: 0 auto;
            background-color: #fff; /* White background */
            padding: 50px;
            border-radius: 8px; /* Rounded corners */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Shadow effect */
        }
        label {
            display: block;
            margin-bottom: 10px;
            color: #333; /* Dark gray color */
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc; /* Light gray border */
            border-radius: 5px; /* Rounded corners */
        }
        input[type="submit"] {
            background-color: #0056b3; /* Dark blue color */
            color: #fff; /* White color */
            border: none;
            padding: 10px 20px;
            border-radius: 5px; /* Rounded corners */
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #004494; /* Darker blue color on hover */
        }
    </style>
</head>
<body>
    <h1>Doctor Login</h1>
    <form action="DocLoginServlet" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <br>
        <input type="submit" value="Login">
    </form>
</body>
</html>
