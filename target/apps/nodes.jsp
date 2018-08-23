<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="base/top.jsp" %>
  </head>
  <style> 
.switch{ 
width: 55px; 
margin: 5px 0px 0 5px; 
} 
.btn_fath{ 
margin-top: 5px; 
position: relative; 
border-radius: 10px; 
} 
.btn1{ 
float: left; 
} 
.btn2{ 
float: right; 
} 
.btnSwitch{ 
height: 20px; 
width: 15px; 
border:none; 
color: #fff; 
line-height: 20px; 
font-size: 10px; 
text-align: center; 
z-index: 1; 
} 
.move{ 
z-index: 20; 
width: 20px; 
border-radius: 20px; 
height: 20px; 
position: absolute; 
cursor: pointer; 
border: 1px solid #828282; 
background-color: #f1eff0; 
box-shadow: 1px 2px 2px 1px #fff inset,0 0 5px 1px #999; 
} 
.on .move{ 
left: 40px; 
} 
.on.btn_fath{ 
background-color: #5281cd; 
} 
.off.btn_fath{ 
background-color: #828282; 
} 
</style> 
  <body>
  <%@ include file="base/up.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="base/left.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<!-- ----------------------------------------------------------------------- -->

      <div style="padding-bottom: 10px"><a class="btn btn-primary" href="${ctx}/nodesAdd.jsp">新增</a></div>
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>点位名称</th>
            <th>点位类型</th>
            <th>点位位置</th>
            <th>设备ID</th>
            <th>操作</th>
            <th>点位设备状态</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="nodes" items="${nodesList }">
          <tr>
            <th>${nodes.id }</th>
            <td>${nodes.name }</td>
            <td>
            <c:if test="${nodes.type ==0 }">&nbsp;</c:if>
            </td>
            <td>${nodes.address }</td>
            <td>
            <c:forEach var="nodeVms" items="${nodes.nodeVmsList }">
            <a href="${ctx}/PC/getVendors?id=${nodeVms.vendorId }" >${nodeVms.vendorId }</a>&nbsp;
            </c:forEach>
           	</td>
            <td><a href="${ctx}/PC/getNodes?id=${nodes.id}">编辑</a></td>
            <td>
            	<div class="switch"> 
            	<c:if test="${nodes.state == 1 }">
            		<div class="btn_fath clearfix on" onclick="toogle(this,${nodes.id })">
            		<div class="move" data-state="on"></div> 
					<div class="btnSwitch btn1">ON</div> 
					<div class="btnSwitch btn2 ">OFF</div> </div>
            	</c:if>
            	<c:if test="${nodes.state == 2 }">
					<div class="btn_fath clearfix off" onclick="toogle(this,${nodes.id })"> 
					<div class="move" data-state="off"></div> 
					<div class="btnSwitch btn1">ON</div> 
					<div class="btnSwitch btn2 ">OFF</div> </div>
            	</c:if>
				</div> 
            </td>
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
			$("#nodes").addClass('active'); 
		});
		function toogle(th,nodeId){ 
			var ele = $(th).children(".move"); 
			if(ele.attr("data-state") == "on"){ 
				ele.animate({left: "0"}, 100, function(){ 
					ele.attr("data-state", "off"); 
					//alert("关！"); 
				}); 
					$.ajax({  
				        type : "GET",  
				        url : ctx+"/PC/updateNodeState",  
				        data : {  
				        	id : nodeId,
				        	state : 2
				        },  
				        async : false,  
				        cache : false,  
				        datatype : "JSON",  
				        success : function() {  
				        	alert("关闭点位:"+nodeId); 
				        },  
				        //调用出错执行的函数  
				        error : function() {  
				            alert("异常");  
				        }  
				    }); 
				$(th).removeClass("on").addClass("off"); 
			}else if(ele.attr("data-state") == "off"){ 
				ele.animate({left: '40px'}, 100, function(){ 
					$(this).attr("data-state", "on"); 
					//alert("开！");
				}); 
					$.ajax({  
				        type : "GET",  
				        url : ctx+"/PC/updateNodeState",  
				        data : {  
				        	id : nodeId,
				        	state : 1
				        },  
				        async : false,  
				        cache : false,  
				        datatype : "JSON",  
				        success : function() {  
				        	alert("开启点位:"+nodeId); 
				        },  
				        //调用出错执行的函数  
				        error : function() {  
				            alert("异常");  
				        }  
				    }); 
			$(th).removeClass("off").addClass("on"); 
			} 
		} 
	</script> 
  </body>
</html>