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

        <div style="padding-bottom: 10px"><a id="addOrderBtn" class="btn btn-primary" href="#">添加需求</a></div>
         <table class="table table-bordered">
            <thead>
              <tr>
                <th>需求单ID</th>
                <th>需求类型</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>需求时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="demands" items="${demandsList }">
              <tr>
                <th scope="row">${demands.id }</th>
                <td><c:if test="${demands.type == 1}">日常需求</c:if></td>
                <td>
                <c:if test="${demands.state == 3}">已取消</c:if>
	            <c:if test="${demands.state == 1}">未执行</c:if>
	            <c:if test="${demands.state == 2}">已执行</c:if>
				</td>
                <td>${demands.createdAt }</td>
                <td>${demands.demandDate }</td>
                <td><a href="${ctx}/PC/getDemandDetailsList?id=${demands.id }" >详情</a>&nbsp;&nbsp;
                <c:if test="${demands.state == 1}">
                	<a href="${ctx}/PC/updateDemandStatus?id=${demands.id }" >取消需求</a>
                </c:if>
                </td>
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
             添加需求单
            </h4>
          </div>
          <div class="modal-body">
              <form id="form" action="${ctx}/PC/getDemands" method="post">
                  <div class="form-group">
                    <label for="demandTime">需求时间</label>
                    <input  type="text"  name="demandDate" id="demandTime" readonly class="form-control" placeholder="选择时间">
                  </div>
                  <div class="form-group">
                    <label for="lineSelect">需求点位</label>
                     <select id="lineSelect" class="form-control">
                        <option value="0">全部点位</option>
                      <!--   <option>东一线</option>
                        <option>东二线</option> -->
                      </select>
                  </div>
                    <label>
                      <input id="selectAll" type="checkbox"> 全选
                    </label>
                   <div id="noddList" >
                  </div>
              </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
            </button>
            <button type="button" id="btnSave"   class="btn btn-primary save">
          		    保存
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>
    <script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
      var PageData = {
          node:[{
              id:'1',//点位id
              name:'点位1',//点位名字
              lineId:'1000',
          },{
              id:'2',//点位id
              name:'点位2',//点位名字
              lineId:'1000',
          },{
              id:'3',//点位id
              name:'点位3',//点位名字
              lineId:'1000',
          },{
              id:'4',//点位id
              name:'点位4',//点位名字
              lineId:'1001',
          },{
              id:'5',//点位id
              name:'点位5',//点位名字
              lineId:'1001',
          }],
          line:[{
             id:'1000',//线路id
             name:'东一线'//线路名字
          },{
             id:'1001',//线路id
             name:'东二线'//线路名字
          }]
      }
      
	      
	   $(function(){
		  $("#demands").addClass('active'); 
		 //获取点位信息和配送线路
	    	  $.ajax({  
	  	        type : "GET",  
	  	        url : "${ctx}/PC/getNodeDelivers",  
	  	        data : {  
	  	        },  
	  	        async : false,  
	  	        cache : false,  
	  	        datatype : "JSON",  
	  	        success : function(data) {  
	  	        	PageData.node = data.node;
	  	        	PageData.line = data.line;
	  	        	console.log(PageData.node);
	  	        },  
	  	        //调用出错执行的函数  
	  	        error : function() {  
	  	            alert("异常");  
	  	        }  
	  	    });

          // 填充线路数据
          function setLineData(){
            for(var i=0;i<PageData.line.length;i++){
               var opt = '<option value="'+PageData.line[i].id+'">'+ PageData.line[i].name+'</option>';
               $("#lineSelect").append(opt);
            }
          };

          // 填充点位数据 lineId 空全部点位 
          function setNodeData(lineId){
            $("#noddList").empty();
            for(var i=0;i<PageData.node.length;i++){
               var checkbox = '<label class="margin-20" ><input type="checkbox" name="nodesId" value="'+PageData.node[i].id+'">'+PageData.node[i].name+'</label>';
               if (lineId != '0' && lineId != PageData.node[i].lineId){
                   continue;
               }
               $("#noddList").append(checkbox);
            }
          }
          
          setLineData();
          setNodeData('0');

           $("#lineSelect").change(function(){
                var lineId = $(this).find("option:selected").val();
                $("#noddList").empty();
                setNodeData(lineId);
           });
          
          //添加需求
          $('#addOrderBtn').click(function(){
              $('#addOrderModal').modal('show');
          });

          //需求时间
          $("#demandTime").datetimepicker({
              language: 'zh-CN',
              format: 'yyyy-mm-dd hh:ii',
              startDate:new Date()
          });

          //全选点位
          $('#selectAll').click(function(){
            var checked = $(this).is(':checked');
            $('#noddList input[type="checkbox"]').each(function(){
                  $(this).prop("checked",checked); 
            });
          });
      });
	      
      $('#btnSave').click(function(){
    	  var demandTime = $("#demandTime").val();
    	  if(demandTime == ""){
    		  window.alert("请填写需求时间!");
    		  return;
    	  }
    	  
    	  var form = document.getElementById('form');
		  form.submit();
    		/* var demands = {};
    		demands.demandDate = $("input[name=demandDate]").val();
    		//demands.principal = $('#userSelect option:selected').val();
    		console.log(demands);
    		var nodeIdList = [];
    		$("input[name='nodeId']:checked").each(function(index,element){
    			var node = {};
    			node.id = Number(element.value);
    			nodeIdList.push(node);
  		 });
    		demands.nodesList = nodeIdList;
    		console.info(JSON.stringify(demands));
    		 $.ajax({
    			url: ctx + "/getDemands",
    			data:JSON.stringify(demands),
    			type: "POST",
    			datType: "JSON",
    			contentType: "application/json",
    			success:function(data){
    				console.log(data);
    				$('#addOrderModal').modal('hide');
    				window.location.reload();
    			}
    		})  */
    	})
    	
    </script>
  </body>
</html>