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
      <h3>添加需求单</h3>
       <div class="row">
          <div class="col-sm-2">需求时间</div>
          <div class="d_date col-sm-4" id="d_date">${demand.demandDate }</div>
          <div class="col-sm-2">需求类型</div>
          <div class="col-sm-4">&nbsp
          <c:if test="${demand.type == 1}">日常需求</c:if>
          </div>
          <div class="col-sm-2">需求明细</div>
          <div class="col-sm-4">
          <c:if test="${demand.state == 0}">&nbsp</c:if>
          <c:if test="${demand.state == 1}">未执行</c:if>
          <c:if test="${demand.state == 2}">已执行</c:if>
          <c:if test="${demand.state == 3}">已取消</c:if>
          </div>
       </div>
       <hr/>
     <!-- 展示点位下所需要的商品   start -->  
     <c:forEach var="nodesList" items="${demand.nodesList }">
       <div class="nodelist">
       <H3>
       <span class="nodeIds">${nodesList.id}</span>
       </H3><br>
       <c:forEach var="nodesVms" items="${nodesList.nodeVmsList }">
       <div class="vmlist">
       <span class="vendorIds">${nodesVms.vendorId}</span>
       				<span class="nodeVmName">${nodesVms.nodeVmName}</span><br>
       <table class="table table-bordered">
	        <thead>
	          <tr>
	            <th>商品ID</th>
	            <th>商品名称</th>
	            <th style="width:200px">需求数量</th>
	          </tr>
	        </thead>
	        
        	<tbody class="product">
        	
       		<c:forEach  var="product" items="${nodesVms.demandTemplateDetailsList }">
	          <tr>
	            <th class="pid">${product.productId }</th>
	            <td class="pname">${product.productName }</td>
	            <td><input type="number" name="num" value="${product.num }" class="form-control"></td>
	          </tr>
	      	</c:forEach>
       	 	</tbody>
      </table>
      </div>
      </c:forEach>
      </div>
    </c:forEach>
    <!-- 展示点位下所需要的商品   end -->    
<!-- ----------------------------------------------------------------------- -->
    	</div>
		<div class="col-sm-12" style="text-align:center;">
             <button id="btnSave" type="button" class="btn btn-primary btn-lg margin-20">保 存</button>
             <button id="btnCancel" type="button" class="btn btn-default btn-lg margin-20">取 消</button>
        </div>
   </div>
 </div>

    <script type="text/javascript">
	    $(function(){
			$("#demands").addClass('active'); 
		})
    $('#btnSave').click(function(){
		var demands = {};
		demands.demandDate = $("#d_date").text();
		
		var nodesList = [];
		$("div.nodelist").each(function(i,node){
			var nodes = {};
			nodes.id=$(this).find("span.nodeIds").text(); 
			var nodeVmsList = [];
			$(node).find("div.vmlist").each(function(j,vmlist){
				var nodeVms = {};
				nodeVms.vendorId =$(vmlist).find("span.vendorIds") .html();
				nodeVms.nodeVmName =$(vmlist).find("span.nodeVmName") .html();
				var demandTemplateDetailsList = [];
				var tr = $(vmlist).find("tbody").find("tr")
				$(tr).each(function(k,r){
					var demandTemplateDetails = {};
					demandTemplateDetails.productId =$(r).find("th.pid").text();
					demandTemplateDetails.productName =$(r).find("td.pname").text();
					demandTemplateDetails.num =$(r).find("input[name=num]").val();
					demandTemplateDetailsList.push(demandTemplateDetails);
				})
				nodeVms.demandTemplateDetailsList = demandTemplateDetailsList;
				nodeVmsList.push(nodeVms);
			})
			nodes.nodeVmsList = nodeVmsList;
			nodesList.push(nodes);
		})	
		demands.nodesList = nodesList;
		console.log(demands);
		
		
		
		 $.ajax({
			url:ctx+"/PC/addDemands",
			data:JSON.stringify(demands),
			type: "POST",
			datType: "JSON",
			contentType: "application/json",
			success:function(data){
				console.log(data);
				window.location.href=ctx+'/PC/getDemandsList';
			}
		}) 
    }); 

	$('#btnCancel').click(function(){
		window.location.href=ctx+'/PC/getDemandsList';
		alert('取消');
	})
    </script>
    
  </body>
</html>