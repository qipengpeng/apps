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

        <div style="padding-bottom: 10px"><a class="btn btn-primary" href="${ctx}/PC/api/deliveryOrder/lists/">测试</a></div>
         <table class="table table-bordered">
            <thead>
              <tr>
                <th>补货单ID</th>
                <th>补货任务ID</th>
                <th>点位ID</th>
                <th>点位名称</th>
                <th>设备ID</th>
                <th>设备名称</th>
                <th>补货时间</th>
                <th>补货人员</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="rep" items="${repList }">
              <tr>
                <th scope="row">${rep.id }</th>
                <td>${rep.replenishmentTaskId }</td>
                <td>${rep.nodeId }</td>
                <td>${rep.nodeName }</td>
                <td>${rep.vendorId }</td>
                <td>${rep.vmNodeSeqName }</td>
                <td>${rep.createdAt }</td>
                <td>${rep.operatorName }</td>
                <td><a href="${ctx}/PC/getReplenishmentLogDetails?id=${rep.id }" >详情</a></td>
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
			$("#replenishmentlogs").addClass('active'); 
		})
	</script>
  </body>
</html>