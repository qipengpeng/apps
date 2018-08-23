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

         <table class="table table-bordered">
            <thead>
              <tr>
                <th>清货单ID</th>
                <th>补货任务ID</th>
                <th>需求单ID</th>
                <th>线路</th>
                <th>补货人员</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="inventory" items="${inventory }">
              <tr>
                <th scope="row">${inventory.id }</th>
                <td>${inventory.taskId }</td>
                <td>${inventory.demandId }</td>
                <td>
                	<c:if test="${inventory.routeId ==0}">&nbsp;</c:if>
                </td>
                <td>${inventory.operatorId }</td>
                <td><a href="${ctx}/PC/getInventoryDetails?id=${inventory.id }" >详情</a></td>
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
			$("#inventory").addClass('active'); 
		})
    </script>
  </body>
</html>