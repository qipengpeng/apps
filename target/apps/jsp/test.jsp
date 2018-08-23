<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<%@ include file="../base/top.jsp" %>
</head>  
<body>  
	<form action="${ctx}/front/preBuy" method="post">  
         <input type="text" name="openId" value="oXFA95exxPbLwf20UoyX00OKtrqg">
          <input type="text" name="nodeId" value="1059">
          <input type="text" name="productId" value="1061">
        <input type="submit">
    </form>


	故障测试
    <form action="${ctx}/vms/setVersion" method="post"> 
     	 <input type="text" name="vmCode" value="1066">
     	 <input type="text" name="apkVer" value="5">
        <input type="submit">
    </form>
    <br>
    库存修改
    <form action="${ctx}/api/replenish/manumotive" method="post">  
        <input type="text" name="token">
        <input type="text" name="device_id">
        <input type="text" name="data">
        <input type="submit">
    </form>
    测试点位
    <form action="${ctx}/data/queryNodes" method="post">  
        <input type="submit">
    </form>
  测试解码Base64
    <form action="${ctx}/front/bindUboxPay" method="post">  
    	
        <input type="submit">
    </form>
    测试库存报警
    <form action="${ctx}/vms/setStockState" method="post">  
    	<input type="text" name="vmCode">
        <input type="text" name="state">
        <input type="submit">
    </form>
    测试企业微信图片推送
    <form action="${ctx}/vms/sendScreen" method="post" enctype="multipart/form-data">
        <input type="text" name="vmCode">
        <input type="file" name="file">
        <input type="submit">
    </form>
</body>  
</html>  