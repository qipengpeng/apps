<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="base/top.jsp" %>
  </head>
  <body>
    <%@ include file="base/up.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="base/left.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<!-- ----------------------------------------------------------------------- -->

      <div style="padding-bottom: 10px"><a class="btn btn-primary" href="${ctx}/productsAdd.jsp">新增</a></div>
      <table class="table table-bordered">
        <thead>
          <tr>
              <th>ID</th>
	          <th>商品名称</th>
	          <th>供应商</th>
	          <th>保质期</th>
	          <th>零售价（元）</th>
	          <th>采购价（元）</th>
	          <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="list" items="${productList }">
          <tr>
            <th>${list.id }</th>
           	<td>${list.name }</td>
           	<td>
           	<c:if test="${list.brandId == 0 }">&nbsp;</c:if>
           	</td>
           	<td>${list.durationPeriod }</td>
           	<td>${list.price/100 } </td>
           	<td>${list.purchasePrice/100 }</td>
            <td><a href="${ctx}/PC/getProduct?id=${list.id }">编辑</a></td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#products").addClass('active'); 
		})
    </script>
  </body>
</html>