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
              <th scope="row">需求单ID</th>
              <td id="demandId">${demand.id }</td>
            </tr>
            <tr>
              <th scope="row">需求时间</th>
              <td>${demand.demandDate }</td>
            </tr>
            
          </tbody>
        </table>

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
	            <th style="width:200px">交割数量</th>
	            <th>生产日期</th>
	          </tr>
	        </thead>
	        
        	<tbody class="product">
        	
       		<c:forEach  var="product" items="${nodesVms.demandTemplateDetailsList }">
	          <tr>
	            <th class="pid">${product.productId }</th>
	            <td class="pname">${product.productName }</td>
	            <td><input type="number" name="num" value="${product.num }" class="form-control"></td>
	          	<td><script>document.write(new Date().toLocaleString());</script></td>
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
		<div class="col-sm-12" style="text-align:center;">
             <button id="btnSave" type="button" class="btn btn-primary btn-lg margin-20">保 存</button>
             <button id="btnCancel" type="button" class="btn btn-default btn-lg margin-20">取 消</button>
        </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
	    $(function(){
			$("#deliveries").addClass('active'); 
		})
    
    $('#btnSave').click(function(){
    	if(window.confirm('你确定提交吗？')){
            //alert("确定");
         }else{
            //alert("取消");
			window.location.href=ctx+'/PC/getProductTempList';
            return false;
        }
		var deliveries = {};
		deliveries.demandId = $("#demandId").text();
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
		deliveries.nodesList = nodesList;
		console.log(deliveries);
		
		 $.ajax({
			url:ctx+"/PC/addDeliveries",
			data:JSON.stringify(deliveries),
			type: "POST",
			datType: "JSON",
			contentType: "application/json",
			success:function(data){
				console.log("SUCCESS:"+data);
				window.alert(data.msg+"提交成功");
				window.location.href=ctx+'/PC/getDeliveriesList';
			},
		 	error:function(data){
				console.log("error:"+data);
				window.alert("交割异常");
				//window.location.href=ctx+'/PC/getDeliveriesList';
			}
		})
    }); 
    	
	    $('#btnCancel').click(function(){
	    	window.location.href=ctx+'/PC/getDeliveriesList';
			alert('取消');
		})
    </script>
  </body>
</html>