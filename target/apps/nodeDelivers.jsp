<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <link href="../css/bootstrap-select.min.css" rel="stylesheet">
    <%@ include file="base/top.jsp" %>
    <script type="text/javascript" src="../js/bootstrap-select.min.js"></script>
  </head>
  <body>
  <%@ include file="base/up.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="base/left.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<!-- ----------------------------------------------------------------------- -->

      <div style="padding-bottom: 10px"><a id="addLineBtn" class="btn btn-primary" href="#">添加线路</a></div>
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>线路ID</th>
            <th>线路名称</th>
            <th>负责人</th>
            <th>包含点位</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="details" items="${detailsList }">
          <tr>
            <th>${details.id }</th>
            <td>${details.name }</td>
            <td>${details.principal }</td>
            <td>${details.includeSite }</td>
            <td><a class="updateLineBtn">编辑</a></td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>
	 <!-- 添加配送 -->
    <div  id="addLineModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
            	 添加配送
            </h4>
          </div>
          <div class="modal-body">
              <form>
                  <div class="form-group">
                    <label for="">线路名称</label>
                    <input type="text" class="form-control" name="name"  placeholder="填写配送线路名称">
                  </div>
                  <div class="form-group">
                    <label for="userSelect">配送人员</label>
                      <select  id="userSelect" class="form-control selectpicker" title="请选择负责人" multiple>
                      </select>
                  </div>
                    <label>
                      <input id="selectAll" type="checkbox"> 全选
                    </label>
                   <div id="noddList">
                  </div>
              </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
            </button>
            <button type="button" id="btnSave" class="btn btn-primary save">
            	  保存
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>
    <!-- 编辑配送 -->
    <div  id="updateLineModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
            	 编辑配送
            </h4>
          </div>
          <div class="modal-body">
              <form>
                  <div class="form-group">
                    <label for="">线路名称</label>
                    <input type="text" class="form-control" name="name"  placeholder="填写配送线路名称">
                  </div>
                  <div class="form-group">
                    <label for="userSelect">配送人员</label>
                      <select  id="userSelect" class="form-control selectpicker" title="请选择负责人" multiple>
                      </select>
                  </div>
                    <label>
                      <input id="selectAll" type="checkbox"> 全选
                    </label>
                   <div id="noddList">
                  </div>
              </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
            </button>
            <button type="button" id="btnSave" class="btn btn-primary save">
            	  保存
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>
    
    <script type="text/javascript">
      var PageData = {
        // 还未被添加到线路上的点位
        node:[{
            id:'1001',
            name:'点位1'
        },{
            id:'1002',
            name:'点位2'
        },{
            id:'1002',
            name:'点位2'
        },{
            id:'1002',
            name:'点位2'
        },{
            id:'1002',
            name:'点位2'
        }],
        // 配送员
        user:[{
           id:'1',
           name:'张三'
        },{
           id:'1001',
           name:'李四'
        },{
           id:'1002',
           name:'高五'
        }]
      };
      console.log(PageData.user);
      $(function(){
  			$("#routeDetails").addClass('active'); 
    	  //获取未分配点位
    	  $.ajax({  
  	        type : "GET",  
  	        url : "${ctx}/PC/getNodesId",  
  	        data : {  
  	        },  
  	        async : false,  
  	        cache : false,  
  	        datatype : "JSON",  
  	        success : function(data) {  
  	        	PageData.node = data;
  	        	console.log(PageData.node);
  	        },  
  	        //调用出错执行的函数  
  	        error : function() {  
  	            alert("异常");  
  	        }  
  	    });
    	  
    	//获取未分配点位
    	  $.ajax({  
  	        type : "GET",  
  	        url : "${ctx}/PC/getUser",  
  	        data : {  
  	        },  
  	        async : false,  
  	        cache : false,  
  	        datatype : "JSON",  
  	        success : function(data) {  
  	        	PageData.user = data;
  	        	console.log(PageData.user);
  	        },  
  	        //调用出错执行的函数  
  	        error : function() {  
  	            alert("异常");  
  	        }  
  	    });
    	
           // 填充补货人员
          function setUserData(){
            for(var i=0;i<PageData.user.length;i++){
               var opt = '<option value="'+PageData.user[i].id+'">'+ PageData.user[i].userName+'</option>';
               $("#userSelect").append(opt);
            }
          };

           // 填充点位数据 lineId 空全部点位 
          function setNodeData(){
            $("#noddList").empty();
            for(var i=0;i<PageData.node.length;i++){
               var checkbox = '<label class="margin-20" ><input id="nodeId" name="nodeId" type="checkbox" value="'+PageData.node[i].id+'">'+PageData.node[i].name+'</label>';
               $("#noddList").append(checkbox);
            }
          }

          setUserData();
          setNodeData();

          $('#addLineBtn').click(function(){
              $('#addLineModal').modal('show');
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
  		var route = {};
  		route.name = $("input[name=name]").val();
  		route.principalId = $('#userSelect option:selected').val();
  		route.principal = $('#userSelect option:selected').text();
  		//多个人负责一条线路
  		/* route.principals = [];
  		$('#userSelect option:selected').each(function(){
  			route.principals.push($(this).val());
  		});
  			console.log(route.principals); */
  		
  		
  		var nodeIdList = [];
  		$("input[name='nodeId']:checked").each(function(index,element){
  			var node = {};
  			node.nodeId = Number(element.value);
  			node.nodeName = element.nextSibling.nodeValue;
  			nodeIdList.push(node);
		 });
  		route.nodeIdList = nodeIdList;
  		console.info(JSON.stringify(route));
  		 $.ajax({
  			url: ctx + "/PC/addRouteDetails",
  			data:JSON.stringify(route),
  			type: "POST",
  			datType: "JSON",
  			contentType: "application/json",
  			success:function(data){
  				console.log(data);
  				$('#addLineModal').modal('hide');
  				window.location.href=ctx+'/PC/getRouteDetailsList';
  			}
  		}) 
  	})
    </script>
    <script type="text/javascript">
	    $('.updateLineBtn').click(function(){
	        $('#updateLineModal').modal('show');
			/* var id = $(this).parent('td').parent('tr').find('th').text();
			console.log("id=="+id);
	        $.ajax({  
		        type : "GET",  
		        url : ctx+"/PC/findProductTemp",  
		        data : {  
		        	id : id
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
		    }); */
	    });
    </script>
  </body>
</html>