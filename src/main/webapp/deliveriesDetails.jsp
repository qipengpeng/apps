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
      
       <h3>交割单详情</h3>
      <table class="table" style="margin-top: 20px">
          <tbody>
            <tr>
              <th scope="row" style="width: 100px">交割单ID</th>
              <td>${deliveries.id }</td>
            </tr>
             <tr>
              <th scope="row">需求单ID</th>
              <td>${deliveries.demandId }</td>
            </tr>
            <tr>
              <th scope="row">需求时间</th>
              <td>${deliveries.demandDate }</td>
            </tr>
            <tr>
              <th scope="row">交割时间</th>
              <td>${deliveries.createdAt}</td>
            </tr>
            <tr>
              <th scope="row">采购总价</th>
              <td>${deliveries.total/100 }</td>
            </tr>
            <tr>
              <th scope="row">交割人</th>
              <td>${deliveries.operator }</td>
            </tr>
          </tbody>
        </table>

      <table class="table table-bordered">
        <thead>
          <tr>
            <th>商品ID</th>
            <th>商品名称</th>
            <th>采购价格（元）</th>
            <th>实际交割</th>
            <th>生产日期</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="deliveryDetails" items="${deliveries.deliveryDetailsList }">
          <tr>
            <th>${deliveryDetails.productId }</th>
            <td>${deliveryDetails.productName }</td>
            <td>${deliveryDetails.unitPrice/100 }</td>
            <td>${deliveryDetails.deliveryNum }</td>
            <td>${deliveryDetails.produceDate }</td>
          </tr>
          </c:forEach>
        </tbody>
      </table>

<!-- ----------------------------------------------------------------------- -->

        </div>
        <div class="col-sm-12" style="text-align:center;">
             <button id="btnCancel" type="button" class="btn btn-primary btn-lg margin-20">关闭</button>
        </div>
      </div>
    </div>
	<script type="text/javascript">
		$(function(){
			$("#deliveries").addClass('active'); 
		})
		
		$('#btnCancel').click(function(){
			window.location.href=ctx+'/PC/getDeliveriesList';
		})
	</script>
  </body>
</html>