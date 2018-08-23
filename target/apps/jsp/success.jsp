<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>登录成功</title>  
</head>  
<body>  
    <%String msg = (String)request.getAttribute("msg1"); %>
    <h5>${msg }</h5>
	<input type="text" name="msg"><br>
    <a href="jsp/login.jsp">去登录</a>  
</body>  
</html>