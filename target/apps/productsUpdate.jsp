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

       <h2>修改商品</h2>
       <form class="form-horizontal" action="${ctx}/PC/updateProduct" method="post" enctype="multipart/form-data">
       <input type="hidden" name="id" value="${product.id}">
       <h3 style="margin:30px">基础信息</h3>
        <div class="form-group">
          <label class="col-sm-2 control-label">商品名称</label>
          <div class="col-sm-10">
            <input  type="text" class="form-control" name="name" value="${product.name}" placeholder="请填写商品名称">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">供应商</label>
          <div class="col-sm-10">
            <select class="form-control">
              <option>默认供应商</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">保质期</label>
          <div class="col-sm-10">
            <input  type="text" class="form-control" name="durationPeriod" value="${product.durationPeriod}" placeholder="请填写商品保质期（天）">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">零售价</label>
          <div class="col-sm-10">
            <input  type="text" class="form-control" name="price" value="${product.price}" placeholder="请填写商品零售价（分）">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">采购价</label>
          <div class="col-sm-10">
            <input  type="text" class="form-control" name="purchasePrice" value="${product.purchasePrice}" placeholder="请填写商品采购价（分）">
          </div>
        </div>
        <h3 style="margin:30px">展示信息</h3>
        <div class="form-group">
          <label class="col-sm-2 control-label">商品描述</label>
          <div class="col-sm-10">
            <input  type="text" class="form-control" name="description" value="${product.description}" placeholder="请填写商品保质期（天）">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">净含量</label>
          <div class="col-sm-10">
            <input  type="text" class="form-control" name="netweight" value="${product.netweight}" placeholder="请填写商品保质期（天）">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">适合人群</label>
          <div class="col-sm-10">
            <input  type="text" class="form-control" name="fitPeople" value="${product.fitPeople}" placeholder="请填写商品保质期（天）">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">营养成分图</label>
          <div class="col-sm-10">
            <!-- <p class="form-control-static"><a>点击上传</a></p> -->
            <input type="file" name="file" onchange="filechange(this)">
            <img id="img" alt="" src="${product.ingredientImg }" style="width: 150px" height:"100px">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">商品列表图</label>
          <div class="col-sm-10">
          	<input type="file" name="file1" onchange="filechange1(this)">
            <img id="img1" alt="" src="${product.listImg }" style="width: 150px" height:"100px">
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">商品详情图</label>
          <div class="col-sm-10">
          	<input type="file" name="file2" onchange="filechange2(this)">
            <img id="img2" alt="" src="${product.detailsImg }" style="width: 150px" height:"100px">
          </div>
        </div>
        <div class="col-sm-12" style="text-align:center;">
           <input type="submit" class="btn btn-primary btn-lg margin-20" value="保 存">
           <button id="btnCancel" type="button" class="btn btn-default btn-lg margin-20">取 消</button>
        </div>
      </form>
       
<!-- ----------------------------------------------------------------------- -->

        </div>
      </div>
    </div>

 <%@ include file="base/top.jsp" %>
 	<script type="text/javascript">
	 	$(function(){
			$("#products").addClass('active'); 
		})	
 	
    	function save(form) {
	        var name =  document.getElementById("name").value;
	        var durationPeriod =  document.getElementById("durationPeriod").value;
	        var price =  document.getElementById("price").value;
	        var purchasePrice =  document.getElementById("purchasePrice").value;
	     
	        if (name==""){
	 			alert("请填写商品名称!")
	 			return false;
	  		}
	  		 else if (durationPeriod=="" ||durationPeriod<=0){
	  			alert("请填写天数并且为大于0的正整数!")
	  			return false;
	  		}else if (price==""||price<=0){
	  			alert("请填写零售价并且为大于0的正整数!")
	  			return false;	
	  		}else if (purchasePrice==""||purchasePrice<=0){
	  			alert("请填写采购价格并且为大于0的正整数!")
	  			return false;		
	      	}
		   return true;
		}
     
     
	    function filechange(obj){
	  		var reader = new FileReader();
	  		reader.readAsDataURL(obj.files[0]);
	  		reader.onload = function(e){
	  			var img = document.getElementById("img");
	  			img.src = e.target.result;
	  		}
	  	 }
	     function filechange1(obj){
	   		var reader = new FileReader();
	   		reader.readAsDataURL(obj.files[0]);
	   		reader.onload = function(e){
	   			var img1 = document.getElementById("img1");
	   			img1.src = e.target.result;
	   		}
	   	 }
	     function filechange2(obj){
	   		var reader = new FileReader();
	   		reader.readAsDataURL(obj.files[0]);
	   		reader.onload = function(e){
	   			var img2 = document.getElementById("img2");
	   			img2.src = e.target.result;
	   		}
	   	 } 
    
	     $('#btnCancel').click(function(){
	 		window.location.href=ctx+'/PC/getProductsList';
	 	})
    </script>
  </body>
</html>