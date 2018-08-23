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

      <h3>需求详情单</h3>
       <div class="row">
          <div class="col-sm-2">需求ID</div>
          <div class="demandId col-sm-4">${demand.id }</div>
          <div class="col-sm-2">需求类型</div>
          <div class="col-sm-4">
          <c:if test="${demand.type == 1}">日常需求</c:if>
          </div>
          <div class="col-sm-2">需求状态</div>
          <div class="col-sm-4">
          <c:if test="${demand.state == 3}">已取消</c:if>
          <c:if test="${demand.state == 1}">未执行</c:if>
          <c:if test="${demand.state == 2}">已执行</c:if>
          </div>
          <div class="col-sm-2">创建时间</div>
          <div class="col-sm-4">${demand.createdAt}</div>
          <div class="col-sm-2">需求时间</div>
          <div class="col-sm-4">${demand.demandDate}</div>
       </div>
       <hr/>
       <h3>需求明细：</h3>
       <button id="btnCheck" type="button" class="btn btn-primary">按商品查看</button>
     <c:forEach var="nodesList" items="${demand.nodesList }">
       <H3 ><span>${nodesList.id}</span></H3><br>
       <c:forEach var="nodesVms" items="${nodesList.nodeVmsList }">
       <span>${nodesVms.vendorId}&nbsp;${nodesVms.nodeVmName}</span><br>
       <table class="table table-bordered">
        <thead>
          <tr>
            <th>商品ID</th>
            <th>商品名称</th>
            <th style="width:200px">需求数量</th>
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
    <div class="col-sm-12" style="text-align:center;">
       <button id="btnCancel" type="button" class="btn btn-default btn-lg margin-20">关闭</button>
   </div>
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>
    
   <!-- 保按商品查 -->
    <div  id="CheckModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
             需求单汇总
            </h4>
          </div>
          <div class="modal-body">
            <table class="table table-bordered">
              <thead>
		          <tr>
		            <th>商品ID</th>
		            <th>商品名称</th>
		            <th style="width:200px">需求数量</th>
		          </tr>
		      </thead>
		      <tbody id="checkList">
		    
		      </tbody>
            </table>
          </div>
          <div class="modal-footer" style="text-align:center;">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>
    
	<script type="text/javascript">
		$(function(){
			$("#demands").addClass('active'); 
		})
		$('#btnCancel').click(function(){
			window.location.href=ctx+'/PC/getDemandsList';
			alert('取消');
		})
		
	    $('#btnCheck').click(function(){
	    	var demandId = $(".demandId").text();
	        $('#CheckModal').modal('show');
	        
	        $.ajax({
    			url: ctx + "/PC/checkList",
    			data:{
    				demandId : demandId
    				},
    			type: "POST",
    			async : false,  
	  	        cache : false,  
	  	        datatype : "JSON",  
    			success:function(data){
    				$('#checkList').text('');
    				var check = $('#checkList');
    			    for(var i=0;i<data.list.length;i++){
    					console.log(data.list[i]);
    					//动态创建一个tr行标签,并且转换成jQuery对象
    	                var $trTemp = $("<tr></tr>");

    	                //往行里面追加 td单元格
    	                $trTemp.append("<td>"+ data.list[i].productId +"</td>");
    	                $trTemp.append("<td>"+ data.list[i].productName +"</td>");
    	                $trTemp.append("<td>"+ data.list[i].num +"</td>");
    	                $trTemp.appendTo(check);
    				}
    				
    			}

    		})
	    });
	    
	</script>
  </body>
</html>