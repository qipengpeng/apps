<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
<script  src="./resources/js/jquery-1.10.2.js"></script>
</head>
<body>
	 <table border="1">
	 		<tr>
                                            <th>商品ID</th>
                                            <th>商品名称</th>
                                            <th>供应商</th>
                                            <th>保质期</th>
                                            <th>零售价（元）</th>
                                            <th>采购价（元）</th>
                                            <th>操作${hello} </th>
                                        </tr>
                                         <c:forEach var="list" items="${productList }">
                                        <tr>
                                        	<td>${list.id }</td>
                                        	<td>${list.name }</td>
                                        	<td>${list.id }</td>
                                        	<td>${list.id }</td>
                                        	<td>${list.id }</td>
                                        	<td>${list.id }</td>
                                        </tr>
                                        </c:forEach>
                                        
	 </table>
</body>
</html>