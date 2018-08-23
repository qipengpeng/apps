<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>Login</title>

    <link href="/js/other_css/style.css" rel="stylesheet">
    <link href="/js/other_css/style-responsive.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="/js/other_js/html5shiv.js"></script>
    <script src="/js/other_js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-body">

<div class="container">

    <form class="form-signin" action="${ctx}/login" method="post">
        <div class="form-signin-heading text-center">
            <h1 class="sign-title">Sign In</h1>
            <img src="/js/other_images/login-logo.png" alt=""/>
        </div>
        <div class="login-wrap">
            <input type="text"  name="userName" class="form-control" placeholder="User ID" autofocus>
            <input type="password" name="password" class="form-control" placeholder="Password">
			<span>${msg }</span>
            <button class="btn btn-lg btn-login btn-block" type="submit">
                <i class="fa fa-check">登录</i>
            </button>

           
        </div>
    </form>

</div>



<!-- Placed js at the end of the document so the pages load faster -->

<!-- Placed js at the end of the document so the pages load faster -->
<script src="/js/other_js/jquery-1.10.2.min.js"></script>
<script src="/js/other_js/bootstrap.min.js"></script>
<script src="/js/other_js/modernizr.min.js"></script>

</body>
<script type="text/javascript">
var user_json = {        
        "userName": userName,  
        "password":password  
    }      

//js对象转换成JSON字符串  
    var jason_str = JSON.stringify(user_json);  

        //Ajax发送数据给后台  
        $.ajax({  
            url :"${ctx}/checkUserLogin",   
            cache : true,  
            type : "post",  
            datatype : "json",  
            contentType : "application/json; charset=utf-8",  
            data : jason_str,  
            success : function (data){  
                //返回空值  
                if(data==null){  
                    alert("不存在该用户！");  
                    window.location.href = "login.jsp";  
                } else{  //存在该用户  
                    if(password == data.password){  //密码正确  
                        window.location.href = "index.jsp?user_name=" + data.user_name;  
                    }else{  //密码不正确  
                        alert("密码错误!");  
                        window.location.href = "login.jsp";  
                    }  
                      
                }  
            }  
              
        });  
</script>
</html>
