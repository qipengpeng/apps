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
    
      <div style="padding-bottom: 10px"><a class="btn btn-primary" href="${ctx}/producttemplateAdd.jsp">新增</a></div>
  <table class="table table-bordered">
        <thead>
          <tr>
              <th>模板ID</th>
	          <th>模板名称</th>
	          <th>货道类型</th>
	          <th>商品内容</th>
	          <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="productTemp" items="${productTempList }">
          <tr>
            <th>${productTemp.id }</th>
           	<td>${productTemp.name }</td>
           	<td>
           	<c:if test="${productTemp.channelsType == 1}">俊鹏32格子柜</c:if>
            <c:if test="${productTemp.channelsType == 2}">冰山微波加热（4货道）</c:if>
           	</td>
           	<td><span>${productTemp.variety }</span>种-
           		<span>${productTemp.amount }</span>件
           	</td>
            <td><a href="${ctx}/PC/getProductTempTetailsList?templateId=${productTemp.id }">编辑</a></td>
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
			$("#productTemp").addClass('active'); 
		});
    </script>
  </body>
</html>