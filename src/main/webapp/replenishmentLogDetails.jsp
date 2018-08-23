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
      <h3>补货详情</h3>
       <table class="table">
        <tbody>
          <tr>
            <th scope="row" style="width: 100px">补货单ID</th>
            <td>${replenishmentLogs.id }</td>
          </tr>
           <tr>
            <th scope="row">补货任务ID</th>
            <td>${replenishmentLogs.replenishmentTaskId }</td>
          </tr>
          <tr>
            <th scope="row">需求单ID</th>
            <td></td>
          </tr>
          <tr>
            <th scope="row">点位ID</th>
            <td>${replenishmentLogs.nodeId }</td>
          </tr>
          <tr>
            <th scope="row">点位名称</th>
            <td>${replenishmentLogs.nodeName }</td>
          </tr>
          <tr>
            <th scope="row">设备ID</th>
            <td>${replenishmentLogs.vendorId }</td>
          </tr>
          <tr>
            <th scope="row">设备名称</th>
            <td>${replenishmentLogs.vmNodeSeqName }</td>
          </tr>
           <tr>
            <th scope="row">补货时间</th>
            <td>${replenishmentLogs.createdAt }</td>
          </tr>
        </tbody>
      </table>

      <table class="table table-bordered">
        <thead>
          <tr>
            <th>货道编号</th>
            <th>商品ID</th>
            <th>商品名称</th>
            <th>需求数量</th>
            <th>补货数量</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="logDetails" items="${replenishmentLogs.replenishmentLogDetailsList }">
          <tr>
            <th>${logDetails.channelId }</th>
            <td>${logDetails.productId }</td>
            <td>${logDetails.productName }</td>
            <td>${logDetails.demandNum }</td>
            <td>${logDetails.num }</td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
       <h4>备注：</h4>
       <p>${replenishmentLogs.remark }</p>
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>

    <script type="text/javascript">
	    $(function(){
			$("#replenishmentlogs").addClass('active'); 
		})
    </script>
  </body>
</html>