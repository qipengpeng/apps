<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <a class="navbar-brand" id="up" href="#">UGO 管理后台</a>
</nav>

<script>
    var hostport=document.location.host;
    if(hostport!="f.ugobao.cn"){
         $("#up").text("UGO 管理后台(测试服务器)");
    }
</script>