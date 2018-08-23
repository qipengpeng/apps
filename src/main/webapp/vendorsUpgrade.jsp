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

        <h3>程序升级</h3>
        <h5><span style="color: red;">${msg1 }</span></h5>
        <form class="form-inline" style="margin-top: 40px;" action="${ctx}/PC/updateVersion" method="post">
          <div class="form-group">
            <select class="VersionSelect form-control" name="version">
              <option value="0">选择升级版本</option>
            </select>
          </div>
          <div class="form-group">
           <div class="col-sm-10">
            <input  type="text"  name="updateTime" id="updateTime" readonly class="form-control" placeholder="选择升级时间">
           </div>
          </div>
          <br>
          <div class="form-group">
            <label for="vmCode">机器编号</label>
            <input type="text" class="form-control" id="vmCode" name="vmCode" placeholder="逗号分隔机器编号，全部升级输入 all">
          </div>
          <input type="submit" class="btn btn-primary" onclick="return save2(this.form)" value="提交">
        </form>

      <h3 style="margin-top: 60px;">添加安装包</h3>
      <h5><span style="color: red;">${msg }</span></h5>
      <form class="form-horizontal col-sm-5" style="margin-top: 20px;" action="${ctx}/PC/apkUpload" method="post" enctype="multipart/form-data">
        <div class="form-group">
          <label for="packageName" class="col-sm-2 control-label">程序包名</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="packageName" name="packageName" value="com.ugo.fan">
          </div>
        </div>
        <div class="form-group">
          <label for="apkVer" class="col-sm-2 control-label">版本编号</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" id="apkVer" name="apkVer" placeholder="输入一个版本编号 ，数字类型">
          </div>
        </div>
        <div class="form-group">
          <label for="descArea" class="col-sm-2 control-label">说明备注</label>
          <div class="col-sm-10">
            <textarea id="descArea" name="descArea" class="form-control" rows="3"></textarea>
          </div>
        </div>
        <div class="form-group">
          <input class="col-sm-offset-1" type="file" id="apkFile" name="apkFile">
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <input type="submit" class="btn btn-primary" value="上传保存" onclick="return save(this.form)">
          </div>
        </div>
      </form>			
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>
    <script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
			$.ajax({
				type : "GET",
				url : ctx + "/PC/getVersionCode",
				data : {  
		        },  
		        async : false,  
		        cache : false,  
		        datatype : "JSON",  
		        success : function(date) { 
		        	var versionList = $('.VersionSelect');
		        	for(var i=0;i<date.length;i++){
		        		 $(".VersionSelect").append("<option value="+date[i]+">"+ date[i] + "</option>");
		        	}
		        },  
		        //调用出错执行的函数  
		        error : function() {  
		            alert("异常");  
		        }  
			});
	  </script>
	  <script type="text/javascript">
		  $(function(){
				$("#vendors").addClass('active'); 
			});
		  function save(form) {
		        var apkVer =  $("#apkVer").val();
		        console.log("apk=="+apkVer);
		        var apkFile = $("#apkFile").val();    
		        console.log("apkFile=="+apkFile);
		        if (apkVer == ""){
		 			alert("请填写版本号!")
		 			return false;
		  		}
		         else if (apkFile == "" || apkFile.length==0){
		 			alert("请选择上传!")
		 			return false;
		  		} 
			   return true;
			}
			
			function save2(form) {
		        var VersionSelect =  $(".VersionSelect").val();
		        console.log("VersionSelect=="+VersionSelect);
		        var vmCode = $("#vmCode").val();    
		        console.log("vmCode=="+vmCode);
		        var demandTime = $("#updateTime").val();
		    	
		        if (VersionSelect ==0){
		 			alert("请选择版本号!")
		 			return false;
		  		}else if(demandTime == ""){
	    		    alert("请填写升级时间!");
	    		    return false;
		    	}else if (vmCode == ""){
		 			alert("请选择升级的设备!")
		 			return false;
		  		} 
			   return true;
			}
			//需求时间
	          $("#updateTime").datetimepicker({
	              language: 'zh-CN',
	              format: 'yyyy-mm-dd hh:ii',
	              startDate:new Date()
	          });
	  </script>

  </body>
</html>