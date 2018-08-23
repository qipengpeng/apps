<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@ include file="base/top.jsp" %>
    <link rel="stylesheet" href="../css/jquery.autocomplete.css">
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=Yif0wdX6sMHxFppp2EkSRt7YSfHAmztA"></script>
  </head>
  <body>
  <%@ include file="base/up.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="base/left.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<!-- ----------------------------------------------------------------------- -->

         <h2>添加点位</h2>
         <form class="form-horizontal margin-20">
          
          <div class="form-group">
            <label class="col-sm-2 control-label">点位名称</label>
            <div class="col-sm-6">
              <input type="text" class="form-control" name="name" placeholder="请填写点位名称">
            </div>
          </div>

          <div class="form-group">
            <label class="col-sm-2 control-label">点位地址</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="address" name="address" placeholder="输入详细地址">
            </div>
          </div>
          <div class="form-group">
          <label class="col-sm-2 control-label"></label>
          	<div  class="col-sm-2 control-label">
		    <span class="lng"></span>&nbsp;&nbsp;&nbsp;<span class="lat"></span>
		    </div>
		   </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">设备编号</label>
            <div class="col-sm-4">
                <input id="autocomplete" type="text" class="form-control" placeholder="输入设备编号">
            </div>
            <!-- <div class="col-sm-2">
                <button type="button" class="btn btn-primary"> + </button>
            </div> -->
          </div>

          <div id="tplWrap" class="col-sm-12" style="margin-top: 20px">
             <table id="tplTable" class="table table-bordered">
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
                <!-- <tr>
                  <th><input type="text" value="" class="form-control" /></th>
                  <td></td>
                  <td></td>
                  <td>
                    <select class="form-control">
                      <option>俊鹏32格子柜</option>
                      <option>冰山4货道微波加热</option>
                    </select>
                  </td>
                  <td>
                    <select id="typeSelect" class="form-control">
                      <option>商品模板1</option>
                      <option>商品模板2</option>
                    </select>
                  </td>
                  <td><a>删除</a></td>
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
	
    <%@ include file="base/top.jsp" %>
    <script src="../js/jquery.autocomplete.js" type="text/javascript"></script>
    <script src="../js/page-node.js">
    </script>
    <script type="text/javascript">
	    $(function(){
			$("#nodes").addClass('active'); 
	    /**
	     * 点击获取坐标
	     */
	    $('#address').change(function(){
	        if($('#').val() == ''){
	            alert('请填写一个地址');
	        }
	        var adds = $('#address').val();
	        getPoint(adds);
		    })
		})
		function getPoint(adds){
		    // 创建地址解析器实例
		    var myGeo = new BMap.Geocoder();
		    // 将地址解析结果显示在地图上,并调整地图视野
		    myGeo.getPoint(adds, function(point){
		    	$(".lng").text(point.lng);
		    	$(".lat").text(point.lat);
		    	console.log("sss---"+point.lat);
		        console.dir("sss---"+point.lng);
		    }, "北京市");
		}
    </script>
  </body>
</html>