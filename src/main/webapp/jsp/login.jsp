<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="controller" method="post">
    <input type="hidden" name="command" value="log_in" />
    Hello!<br/>
    Email:<br/>
    <input type="email" name="email" value="" /><br/>
    Password:<br/>
    <input type="password" name="password" value="" /><br/>
    <br/>
        ${invalidLoginOrPassMessage}
    <input type="submit" value="login" />
</form>
</body>
</html>
