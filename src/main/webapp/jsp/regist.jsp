<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>用户注册</title>  
</head>  
<body>  
    <form action="/apps/regist" method="post">  
        <table border="1">  
            <tr>  
                <td>用户名</td>  
                <td><input type="text" name="userName"></td>  
            </tr>  
            <tr>  
                <td>密码</td>  
                <td><input type="text" name="password"></td>  
            </tr>  
            <tr>  
                <td><input type="submit" value="注册"></td>  
            </tr>  
        </table>  
    </form>  
</body>  
</html>  