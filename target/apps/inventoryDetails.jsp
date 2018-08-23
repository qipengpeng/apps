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
      
       <h3>清货单详情</h3>
      <table class="table" style="margin-top: 20px">
          <tbody>
            <tr>
              <th scope="row" style="width: 100px">清货单ID</th>
              <td>${inventory.id }</td>
            </tr>
             <tr>
              <th scope="row">补货任务ID</th>
              <td>${inventory.taskId }</td>
            </tr>
            <tr>
              <th scope="row">需求单ID</th>
              <td>${inventory.demandId }</td>
            </tr>
            <tr>
              <th scope="row">最后更新时间</th>
              <td>${inventory.routeId}</td>
            </tr>
            <tr>
              <th scope="row">补货员</th>
              <td>${inventory.operatorId }</td>
            </tr>
 
          </tbody>
        </table>

      <c:forEach var="nodesList" items="${inventory.nodesList }">
       <H3 ><span>${nodesList.id}</span></H3><br>
       <c:forEach var="nodesVms" items="${nodesList.nodeVmsList }">
       <span>${nodesVms.vendorId}&nbsp;${nodesVms.nodeVmName}</span><br>
       <table class="table table-bordered">
        <thead>
          <tr>
            <th>商品ID</th>
            <th>商品名称</th>
            <th style="width:200px">清货数量</th>
          </tr>
        </thead>
        <tbody>
       	<c:forEach var="product" items="${nodesVms.demandTemplateDetailsList }">
          <tr>
            <th>${product.productId }</th>
            <td>${product.productName }</td>
            <td>${product.num }</td>
          </tr>
      	</c:forEach>
        </tbody>
      </table>
      </c:forEach>
    </c:forEach>

<!-- ----------------------------------------------------------------------- -->

        </div>
        <div class="col-sm-12" style="text-align:center;">
             <button id="btnCancel" type="button" class="btn btn-primary btn-lg margin-20">关闭</button>
        </div>
      </div>
    </div>
	<script type="text/javascript">
		$(function(){
			$("#inventory").addClass('active'); 
		})
		
		$('#btnCancel').click(function(){
			window.location.href=ctx+'/PC/getInventoryList';
		})
	</script>
  </body>
</html>