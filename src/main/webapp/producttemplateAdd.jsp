<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="base/top.jsp" %>
    <link rel="stylesheet" href="css/jquery.autocomplete.css">
  </head>
  <body>
  <%@ include file="base/up.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="base/left.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<!-- ----------------------------------------------------------------------- -->

         <h2>添加商品模板</h2>
         <form class="form-horizontal margin-20" action="" method="post">
          <div class="form-group">
            <label class="col-sm-2 control-label">模板名称</label>
            <div class="col-sm-6">
              <input  type="text" class="form-control" name="templateName" placeholder="请填写模板名称">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">货道类型</label>
            <div class="col-sm-6">
              <select id="typeSelect" class="form-control" name="channelsType">
               	<option>请选择货道类型</option>
              </select>
            </div>
          </div>
          <div id="tplWrap">
           <h4>模板详情：</h4> 
             <table id="tplTable" class="table table-bordered">
              <thead>
                <tr>
                  <th>货道编号</th>
                  <th>商品ID</th>
                  <th>商品名称</th>
                  <th>供应商</th>
                  <th>销售价格（分）</th>
                  <th>数量</th>
                  <th id="time">加热时间(秒)</th>
                  <th colspan="2" style="text-align: center;">操作</th>
                </tr>
              </thead>
              <tbody id="details">
               <!--  <tr>
                  <th>1</th>
                  <td>ABCD1234</td>
                  <td>格子柜</td>
                  <td>俊鹏</td>
                  <td>未投放</td>
                  <td>未投放</td>
                  <td><a>编辑</a></td>
                </tr> -->
              </tbody>
            </table>
          </div>

          <div class="col-sm-12" style="text-align:center;">
             <button id="btnSave" type="button" class="btn btn-primary btn-lg margin-20">保 存</button>
             <button id="btnCancel" type="button" class="btn btn-default btn-lg margin-20">取 消</button>
          </div>
        </form>
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>

    <!-- 搜索添加商品 -->
    <div  id="searchModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
              搜索商品
            </h4>
          </div>
          <div class="modal-body">
              <input type="text" name="search" id="autocomplete" class="form-control" placeholder="按 商品ID 或 名称 匹配搜索"/>
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

    <!-- 复制商品框 -->
    <div  id="copyModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
              复制商品
            </h4>
          </div>
          <div class="modal-body">
              <form class="form-inline">
                <div class="form-group">
                  <label for="startInput">开始</label>
                  <input type="number" class="form-control" id="startInput" placeholder="货道号">
                </div>
                <div class="form-group">
                  <label for="endInput">结束</label>
                  <input type="number" class="form-control" id="endInput" placeholder="货道号">
                </div>
              </form>
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

    <!-- 更新数量 -->
    <div  id="updateNumModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
             更新商品数量
            </h4>
          </div>
          <div class="modal-body">
              <input id="num" type="text" name="num" class="form-control"/>
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
    
    <!-- 设置加热时间 -->
    <div  id="addHeatUpModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
             设置加热时间(秒)
            </h4>
          </div>
          <div class="modal-body">
              <input id="heatup" type="text" name="heatup" class="form-control"/>
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
    <script src="js/jquery.autocomplete.js" type="text/javascript"></script>
    <script src="js/page-product-template.js"></script>
    <script type="text/javascript">
		    $(function(){
				$("#productTemp").addClass('active'); 
			});
			$("#typeSelect").change(function () {
				var option = $("#typeSelect").find("option:selected").data('num');
				if(option==32){
					$("#time").hide();
				}else {
					$("#time").show();
				}
			});
    </script>
  </body>
</html>