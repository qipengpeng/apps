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

        <div style="padding-bottom: 10px">
        	<a class="btn btn-primary" href="${ctx}/vendorsAdd.jsp">添加设备</a>
        	<a class="btn btn-primary" href="${ctx}/vendorsUpgrade.jsp"">程序升级</a>
        </div>
         <table class="table table-bordered">
            <thead>
              <tr>
                <th>ID</th>
                <th>铭牌号</th>
                <th>设备类型</th>
                <th>设备厂商</th>
                <th>设备状态</th>
                <th>网络</th>
                <th>当前版本号</th>
                <th>最新版本号</th>
                <th>上次调整时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="vendors" items="${vendorsList }">
              <tr>
                <th scope="row">${vendors.id }</th>
                <td>${vendors.nameplate }</td>
                <td>
                <c:if test="${vendors.type == 1}">32格子柜</c:if>
                <c:if test="${vendors.type == 2}">微波加热柜</c:if>
                </td>
                <td>
                <c:if test="${vendors.producer == 1}">俊鹏</c:if>
                <c:if test="${vendors.producer == 2}">冰山</c:if>
                </td>
                <td>
				<c:if test="${vendors.nodeState == 0}">未投放</c:if>
              	<c:if test="${vendors.nodeState == 1}">在点位</c:if>
              	<c:if test="${vendors.nodeState == 2}">停售中</c:if>
				</td>
			<c:if test="${vendors.state == 2}">
				<td class="text-success">在线</td>
			</c:if>
            <c:if test="${vendors.state == 1}">
				 <td class="text-danger">离线</td>
			</c:if>
				<td>${vendors.versionCode}</td>
				<td>${vendors.version}</td>
				<td>${vendors.updatedAt}</td>
                <td><a href="${ctx}/PC/getVendors?id=${vendors.id }" >详情</a></td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>

    <%@ include file="base/top.jsp" %>
    <script type="text/javascript">
	    $(function(){
			$("#vendors").addClass('active'); 
		})
    </script>
  </body>
</html>