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

        <div style="padding-bottom: 10px"><a id="addOrderBtn" class="btn btn-primary" href="#">新增</a></div>
         <table class="table table-bordered">
            <thead>
              <tr>
                <th>交割单ID</th>
                <th>需求单ID</th>
                <th>需求时间</th>
                <th>交割时间</th>
                <th>交割人</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="deliveries" items="${deliveriesList }">
              <tr>
                <th scope="row">${deliveries.id }</th>
                <td>${deliveries.demandId }</td>
                <td>${deliveries.demandDate }</td>
                <td>${deliveries.createdAt }</td>
                <td>${deliveries.operator }</td>
                <td><a href="${ctx}/PC/QueryDeliveryDetailsList?deliveryId=${deliveries.id }" >详情</a></td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>
	<!-- 添加需求单 -->
    <div  id="addOrderModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
              交割需求单
            </h4>
          </div>
          <div class="modal-body">
              <input id="demandId" type="text" name="num" class="form-control" placeholder="输入需求单ID" />
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
            </button>
            <button type="button" class="btn btn-primary save">
              保存
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>
    
    <%@ include file="base/top.jsp" %>
    <script>
        $(function(){
    		$("#deliveries").addClass('active'); 
            $('#addOrderBtn').click(function(){
                $('#addOrderModal').modal('show');
            });

            $('#addOrderModal .save').click(function(){
                // 保存
                //employeeId 作为js的参数传递进来
                var demandId = $("#demandId").val();
        		window.location.href = '${ctx}/PC/getDeliveryDetails?demandId='+demandId;
                $('#addOrderModal').modal('hide');
            });
        });
    </script>
  </body>
</html>