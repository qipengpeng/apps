<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="base/top.jsp" %>
    <link rel="stylesheet" href="../css/jquery.autocomplete.css">
  </head>
  <body>
  <%@ include file="base/up.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="base/left.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<!-- ----------------------------------------------------------------------- -->

         <h2>点位编辑</h2>
         <form class="form-horizontal margin-20">
          <div class="form-group">
            <label class="col-sm-2 control-label">点位ID</label>
            <div class="col-sm-6">
              <input type="text" class="form-control" id="id" name="id" value="${nodes.id }" placeholder="请填写点位名称" disabled="disabled">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">点位名称</label>
            <div class="col-sm-6">
              <input type="text" class="form-control" name="name" value="${nodes.name }" placeholder="请填写点位名称">
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">点位地址</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="address" value="${nodes.address }" placeholder="输入详细地址" disabled="disabled">
            </div>
          </div>

          
          <div class="form-group">
            <label class="col-sm-2 control-label">设备编号</label>
            <div class="col-sm-4">
                <input id="autocomplete" type="text"  class="form-control" placeholder="输入设备编号">
            </div>
          </div>
			
		  <div class="form-group">
            <label class="col-sm-2 control-label">折扣比例</label>
            <div class="col-sm-4">
                <input type="text" id="percentage" value="${nodes.percentage }" class="form-control">
            </div>
            <div class="col-sm-2">
                <button type="button" id="updateDiscount" class="btn btn-primary"> + </button><font color="red">点击更新折扣</font>
            </div>
		  </div>
          <div id="tplWrap" class="col-sm-12" style="margin-top: 20px">
             <table id="tplTable" class="table table-bordered" >
              <thead>
                <tr>
                  <th style="width: 100px">设备名称</th>
                  <th>设备ID</th>
                  <th>激活码</th>
                  <th>类型</th>
                  <th>商品模板</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody id ="nodeVms">
              <c:forEach var="nodeVms" items="${nodes.nodeVmsList }">
              <tr>
              		<th><input class="d_vm form-control" type="text" name="nodeVmName" value="${nodeVms.nodeVmName }"> </th>
              		<td class="d_id">${nodeVms.vendorId }</td>
              		<td>${nodeVms.vendorPassword }</td>
              		<td class="d_type">${nodeVms.vendorTypeId }</td>
              		<td>
              			<select class="tpl-select form-control" name="tempId" >
              				<option value="${nodeVms.tempId }">${nodeVms.tempName }</option>
              			</select>
              		</td>
              		<td class="del"><a>删除</a></td>
              </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>

          <div class="col-sm-12" style="text-align:center;">
             <button id="btnSaveUpdate" type="button" class="btn btn-primary btn-lg margin-20">保 存</button>
             <button id="btnCancel" type="button" class="btn btn-default btn-lg margin-20">取 消</button>
          </div>
        </form>
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>
	
    <script src="../js/jquery.autocomplete.js" type="text/javascript"></script>
    <script src="../js/page-node.js"></script>
    <script src="../js/page-node-updated.js"></script>
    <script type="text/javascript">
	    $(function(){
			$("#nodes").addClass('active'); 
		});
	    $('#updateDiscount').click(function(){
	    	var id = $("#id").val();
	        var percentage = $("#percentage").val();
	        $.ajax({  
		        type : "POST",  
		        url : ctx+"/PC/updateDiscount",  
		        data : {  
		        	id:id,
		        	percentage:percentage
		        },  
		        async : false,  
		        cache : false,  
		        datatype : "JSON",  
		        success : function(data) { 
		        	console.log(data);
		        	window.alert("折扣已更新");
		        },  
		        //调用出错执行的函数  
		        error : function() {
		        	console.log("修改折扣异常");
		        }  
		    });
	    });
    	$('.tpl-select').focus(function(){
    		$(this).text('');
    		var d_type = $(this).parent("td").parent("tr").find(".d_type").text();
    		console.log("channelsType"+d_type);
    		$.ajax({  
		        type : "GET",  
		        url : ctx+"/PC/findProductTemp",  
		        data : {  
		        	channelsType : d_type
		        },  
		        async : false,  
		        cache : false,  
		        datatype : "JSON",  
		        success : function(data) {  
		        	PageData.tpl = data;
		        	console.log(PageData.tpl);
		        },  
		        //调用出错执行的函数  
		        error : function() {  
		        	console.log("获取模板列表异常");  
		        }  
		    });
    		
    		var tplSel1 = $(this);
	    	for (var i = 0;i<PageData.tpl.length; i++) {
	    		$("<option value='"+PageData.tpl[i].id+"'>"+PageData.tpl[i].name+"</option>").data(PageData.tpl[i]).appendTo(tplSel1);
	    	}
	    	
    	})
    	
    	$('.del').click(function(){
    		$(this).parent("tr").remove();
    	});
    </script>
  </body>
</html>