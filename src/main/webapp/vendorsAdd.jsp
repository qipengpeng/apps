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

      <h2 style="margin-bottom: 60px">添加设备</h2>
       <form class="form-horizontal" action="${ctx}/PC/addVendors" method="post">
        <div class="form-group">
          <label class="col-sm-2 control-label">铭牌号</label>
          <div class="col-sm-5">
            <textarea class="form-control" id="nameplate" name="nameplate" rows="3" placeholder="请填写铭牌号，多个逗号分隔"></textarea>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label" >设备类型</label>
          <div class="col-sm-5">
            <select class="form-control" name="type">
              <option value="1">32格子柜</option>
              <option value="2">微波加热</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">设备厂商</label>
          <div class="col-sm-5">
            <select class="form-control" name="producer">
              <option value="1">俊鹏</option>
              <option value="2">冰山</option>
            </select>
          </div>
        </div>
        <div class="col-sm-8" style="text-align:center;">
           <input type="submit" class="btn btn-primary btn-lg margin-20" value="保 存" onclick="return save(this.form)">
           <button type="button" id="btnCancel" class="btn btn-default btn-lg margin-20">取 消</button>
        </div>
      </form>
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>

    <%@ include file="base/top.jsp" %>
    <script type="text/javascript">
	    $(function(){
			$("#vendors").addClass('active'); 
		})
	    function save(form) {
	        var nameplate =  document.getElementById("nameplate").value;
	        if (nameplate==""){
	 			alert("请填写铭牌号!")
	 			return false;
	  		}
		   return true;
		}
	    
	    $('#btnCancel').click(function(){
			window.location.href=ctx+'/PC/getVendorsList';
		})
    </script>
  </body>
</html>