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

         <table class="table">
          <caption>设备信息</caption>
          <tbody>
            <tr>
              <th scope="row" style="width: 100px">铭牌号</th>
              <td>${vendors.nameplate }</td>
            </tr>
             <tr>
              <th scope="row">设备ID</th>
              <td id="vendorId">${vendors.id }</td>
            </tr>
            <tr>
              <th scope="row">激活密码</th>
              <td>${vendors.password }</td>
            </tr>
            <tr>
              <th scope="row">设备类型</th>
              <td>
				<c:if test="${vendors.type == 1}">32格子柜</c:if>
                <c:if test="${vendors.type == 2}">微波加热柜</c:if>
			  </td>
            </tr>
            <tr>
              <th scope="row">设备厂商</th>
              <td>
              	<c:if test="${vendors.producer == 1}">俊鹏</c:if>
                <c:if test="${vendors.producer == 2}">冰山</c:if>
              </td>
            </tr>
            <tr>
              <th scope="row">所属点位</th>
              <td>
              <c:if test="${vendors.nodeId == 0}">设备不在点位</c:if>
              <c:if test="${vendors.nodeId != 0}">${vendors.nodeName}</c:if>
              </td>
            </tr>
            <tr>
              <th scope="row">在线状态</th>
              <td>
              <c:if test="${vendors.state == 1}">离线</c:if>
              <c:if test="${vendors.state == 2}">在线</c:if>
			  </td>
            </tr>

            <tr>
              <th scope="row">程序版本</th>
              <td>16</td>
            </tr>
          </tbody>
        </table>
       
<!-- ----------------------------------------------------------------------- -->
		  <div class="form-inline">
                <a id="sda" class="btn btn-primary" href="#">打开调试</a>
                <a id="dsd" class="btn btn-primary" href="#">清除调试消息</a>
                <a id="restart" class="btn btn-danger" href="#">重启</a>
                <a id="uploadLogsBtn" class="btn btn-success" href="#">上传日志</a>
          </div> 
          <div id="bs" style="display:block">
             <div class="form-inline" style="margin-top: 10px;">
                  <a id="refProList" class="btn btn-success" href="#">更新商品列表</a> 
                  <a id="dss" class="btn btn-primary" href="#">故障状态</a>
                  <a id="AA" class="btn btn-success" href="#">自检</a>
                 <a id="screen" class="btn btn-primary" href="#">屏幕截取</a>
              </div>
          </div>
          <div id="box" style="display: none">
             <div class="form-inline" style="margin-top: 10px;">
                <a id="fdfd" class="btn btn-primary" href="#">查询设备状态</a>
                 <a id="fv" class="btn btn-primary" href="#">获取设备信息</a>
                 <a id="faa" class="btn btn-primary" href="#">查询温度</a>
                 <a id="vv" class="btn btn-primary" href="#">定时臭氧状态读取</a>
                 <a id="gre" class="btn btn-primary" href="#">定时臭氧参数设置</a>
                 <a id="rferg" class="btn btn-primary" href="#">定时臭氧参数读取</a>
                 <a id="rgre" class="btn btn-primary" href="#">加热参数读取</a>
                 <a id="frfr" class="btn btn-primary" href="#">加热参数设置</a>
             </div>
             <div class="form-inline" style="margin-top: 10px;">
                 <label> <input type="checkbox" value=""> 加热开关</label>
                 <label> <input type="checkbox" value="" style="margin-left: 20px;"> 制冷开关</label>
                 <label> <input type="checkbox" value="" style="margin-left: 20px;"> 自动锁开关</label>
                 <label> <input type="checkbox" value="" style="margin-left: 20px;"> 照明开关</label>
                 <label> <input type="checkbox" value="" style="margin-left: 20px;"> 臭氧开关</label>
                 <label> <input type="checkbox" value="" style="margin-left: 20px;"> 臭氧定时开关</label>
             </div>
          </div>
          <textarea id="log" class="form-control" readonly="readonly" rows="3" placeholder="调试信息..." style="margin-top:10px;height:300px"></textarea>

        </div>
      </div>
    </div>
  <!-- 添加需求单 -->
  <div id="uploadLogsModel" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
          <div class="modal-content">
              <div class="modal-header">
                  <h4 class="modal-title" id="myModalLabel">
                      设备日志提取
                  </h4>
              </div>
              <div class="modal-body">
                  <input  type="text"  name="date" id="date" readonly class="form-control" placeholder="选择时间">
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
    <script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
       $(function(){
    	   $("#vendors").addClass('active');
            <%-- var wsuri = "<%=socPath%>bus/<%= request.getParameter("user")%>/<%= request.getParameter("deviceId")%>";
            var ws = null;
            
            function addLog(msg){
              var logEl = $('#log');
              var txt = logEl.val();
              logEl.val(msg+"\r\n"+txt);
              logEl.scrollTop = logEl.scrollHeight;
            }
            
          
            function startWebSocket() {
                if ('WebSocket' in window){
                  ws = new WebSocket(wsuri);
                }else if ('MozWebSocket' in window){
                  ws = new MozWebSocket(wsuri);
                }else{
                  console.error("not support WebSocket!");
                }

                ws.onmessage = function(evt) {
                    addLog(evt.data);
                    console.info(evt);
                };

                ws.onclose = function(evt) {
                  
                    //alert("close");
                    console.info(evt);
                    addLog("已关闭调试！");                   
                };

                ws.onopen = function(evt) {
                    //alert("open");
                    console.info(evt);
                    addLog("已打开调试！");
                };
            };

            function closeWebSocket(){
               if(ws){
                  ws.close();
               }
            } --%>
            $("#refProList").click(function() {
            	var id = $("#vendorId").text();
            	console.log("设备id---"+id);
            	$.ajax({
            		type : "GET", 
					url:ctx+"/vms/refreshOSD",
					data:{
						id : id
					},
					async : false,  
			        cache : false,  
			        datatype : "JSON",  
			        success : function(data) {  
			        	alert(data); 
			        },  
			        //调用出错执行的函数  
			        error : function(data) {  
			            alert(data);  
			        }  
				});
			});
            
            $("#restart").click(function() {
                if(window.confirm('你确定重启吗？')){
                    //alert("确定");
                }else{
                    //alert("取消");
                    return false;
                }
            	var id = $("#vendorId").text();
            	console.log("设备id---"+id);
            	$.ajax({
            		type : "GET", 
					url:ctx+"/vms/restart",
					data:{
						id : id
					},
					async : false,  
			        cache : false,  
			        datatype : "JSON",  
			        success : function(data) {  
			        	alert(data); 
			        },  
			        //调用出错执行的函数  
			        error : function(data) {  
			            alert(data);  
			        }  
				});
			});



            $("#screen").click(function() {
            	var id = $("#vendorId").text();
            	console.log("设备id---"+id);
            	$.ajax({
            		type : "GET", 
					url:ctx+"/vms/screenShot",
					data:{
						id : id
					},
					async : false,  
			        cache : false,  
			        datatype : "JSON",  
			        success : function(data) {  
			        	alert(data); 
			        },  
			        //调用出错执行的函数  
			        error : function(data) {  
			            alert(data);  
			        }  
				});
			});
            
            $('#btn_openAll').click(function(){
         
                  $.get("openAll", { name: "John", time: "2pm" },
                    function(data){
                    addLog("Data Loaded: " + data);
                  }); 
            });

        });
    </script>
  <script>
      $(function () {
          $("#uploadLogsBtn").click(function(){
              $("#uploadLogsModel").modal("show");
          });

          $("#date").datetimepicker({
              language: 'zh-CN',
              format: 'yyyy-mm-dd hh:ii:ss'
              //startDate:new Date()
          });

          $("#uploadLogsModel .save").click(function(){
              // 保存
              //employeeId 作为js的参数传递进来
              var id = $("#vendorId").text();
              var date = $("#date").val();
              $.ajax({
                  type : "GET",
                  url:ctx+"/vms/Getlog",
                  data:{
                      id : id ,
                      date: date
                  },
                  async : false,
                  cache : false,
                  datatype : "JSON",
                  success : function(data) {
                      alert(data);
                  },
                  //调用出错执行的函数
                  error : function(data) {
                      alert(data);
                  }
              });
              $("#uploadLogsModel").modal("hide");
          });
      });
  </script>
  </body>
</html>