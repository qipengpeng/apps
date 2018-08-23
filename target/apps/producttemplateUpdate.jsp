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

         <h2>编辑商品模板</h2>
         <form class="form-horizontal margin-20" action="" method="post">
         <div class="form-group">
            <label class="col-sm-2 control-label">模板ID</label>
            <div class="col-sm-6">
              <input  type="text" class="form-control" name="id" value="${productTemplate.id }" disabled="disabled">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">模板名称</label>
            <div class="col-sm-6">
              <input  type="text" class="form-control" name="templateName" value="${productTemplate.name }" placeholder="请填写商品名称">
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">货道类型</label>
            <div class="col-sm-6">
              <select id="typeSelect" class="form-control" name="channelsType"  disabled="disabled">
                <c:if test="${productTemplate.channelsType ==1 }">
                <option value="${productTemplate.channelsType }">俊鹏32格子柜</option>
                </c:if>
                <c:if test="${productTemplate.channelsType ==2 }">
                <option value="${productTemplate.channelsType }">冰山4货道微波加热</option>
                </c:if>
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
                  <c:if test="${productTemplate.channelsType ==2 }">
                  <th id="time">加热时间(秒)</th>
                  </c:if>
                  <th colspan="2" style="text-align: center;">操作</th>
                </tr>
              </thead>
              <tbody id="details">
              <c:forEach var="tetails" items="${productTemplate.productList }">
              <tr>
              	  <th class="seq">${tetails.channelsId }</th>
                  <td class="pid">${tetails.productsId }</td>
                  <td class="pname">${tetails.productsName }</td>
                  <td class="provide">
                  <c:if test="${tetails.brandId == 0}">&nbsp;</c:if>
                  </td>
                  <td class="pic">${tetails.brandPrice }</td>
                  <td class="pnum">${tetails.num }</td>
                  <c:if test="${productTemplate.channelsType ==2 }">
                  <td class="heatup">${tetails.heatUpTime }</td>
                  </c:if>
                  <td class="clear"><a>清空</a></td>
                  <td class="copy"><a>复制</a></td>
              </tr>
              </c:forEach>
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
    <script src="../js/jquery.autocomplete.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    var PageData = {

    		device:[{
    			type:1,
    			name:'俊鹏32格子柜',
    			num:32
    		},{
    			type:2,
    			name:'冰山微波加热（4货道）',
    			num:4
    		}],
    		product:[
    		    { value: '1001 宫爆鸡丁', data: {id:'1001',name:'宫爆鸡丁',provide:'供应1',pic:'1.3',num:'1'}},
    		    { value: '1002 鱼香肉丝', data: {id:'1002',name:'鱼香肉丝',provide:'供应2',pic:'1.5',num:'1'}}
    		]
    	}
    
    $(function(){
		$("#productTemp").addClass('active'); 
    	$.ajax({  
            type : "GET",  
            url : ctx+"/PC/findJson",  
            data : {  
            },  
            async : false,  
            cache : false,  
            datatype : "JSON",  
            success : function(data) {  
            	var datas = [];
            	$.each(data, function(index, obj) {//拼接成json数据
            		var o = {};
            		o.value = ''+obj.id+obj.name;
            		o.data = obj;
            		datas.push(o);
                });
            	PageData.product = datas;
            	console.log(PageData.product);
            },  
            //调用出错执行的函数  
            error : function() {  
                alert("异常");  
            }  

        }); 
    	
    	// 设备种类选项框
    	var typeSelectEl = $('#typeSelect');
    	// 货道模板表格
    	var tplTableEl = $('#tplTable tbody');
    	// 补全输入框
    	var autocompleteEl = $('#autocomplete');
    	// 搜索商品框
    	var searchModalEl1 = $('#searchModal');
    	// 复制商品框
    	var copyModalEl = $('#copyModal');
    	// 更新商品数量
    	var updateNumModalEl = $('#updateNumModal');
    	//add   加热时间    20180612
    	var addHeatUpModalEl = $('#addHeatUpModal');
    	
    	// 当前设备货道数量
    	var seqMax = 0;
    	// 当前选择添加商品的货道
    	var selectedAddRow1 = 0;
    	// 当前选择复制商品的货道
    	var selectedCopyRow = 0;
    	// 当前匹配到的搜索数据
    	var selectedData = null;
    	
    	
    	$(".clear").click(function(){
    		var s = $(this).find(".pid").text();
    		$(this).parent("tr").find(".pid").text("");
			$(this).parent("tr").find(".pname").text("");
			$(this).parent("tr").find(".provide").text("");
			$(this).parent("tr").find(".pic").text("");
			$(this).parent("tr").find(".pnum").text("");
    	});
    	
    	$('.copy').click(function(){
			var rowNum = $(this).parent("tr").prevAll().length;  
			console.log("rowNum------"+rowNum);
			selectedCopyRow = rowNum;
			$("#startInput").val(rowNum+1);//默认复制下一行；
			copyModalEl.modal('show');
		});
    	
    	copyModalEl.find('.save').click(function(){
   		 
   		 $startInput = $("#startInput");
   		 $endInput = $("#endInput");
		 
   		 var st = parseInt($startInput.val());
   		 var ed = parseInt($endInput.val()); 
		console.log("st------"+st);
		console.log("ed------"+ed);
   		 if (st == 0 && ed == 0) {
   		 	return;
   		 }

   		 var $tr = tplTableEl.find("tr:eq("+selectedCopyRow+")");
   		 var pid = $tr.find(".pid").text();
   		console.log("pid---"+pid);
   		 var pname = $tr.find(".pname").text();
   		 var provide = $tr.find(".provide").text();
   		 var pic = $tr.find(".pic").text();
   		 var pnum = $tr.find(".pnum").text();
   		 var heatup = $tr.find(".heatup").text();

   		 if (st > 0 && ed > 0) {
   		 	for (var i = st; st < ed; st++) {
   		 		copyRow(i);
   		 	}
   		 }else{
   		 	copyRow(st);
   		 }
   		 
   		 copyModalEl.modal('hide');

   		 function copyRow(row){
   		 	$desTR = tplTableEl.find("tr:eq("+st+")");
   		    console.log($desTR);
   		 	 $desTR.find(".pid").text(pid);
   			 $desTR.find(".pname").text(pname);
   			 $desTR.find(".provide").text(provide);
   			 $desTR.find(".pic").text(pic);
   			 $desTR.find(".pnum").text(pnum);
   			 //add   加热时间    20180612
   			 $desTR.find(".heatup").text(heatup);
   		 }
   	})
    	
    	$(".pid").click(function(){
    		var rowNum = $(this).parent("tr").prevAll().length; 
    		console.log("rowNum---"+rowNum);
			var seq = $(this).parent("tr").find("th.seq").text();
			selectedAddRow1 = rowNum;
			$('#searchModal .modal-title').text("货道 "+seq+" 添加商品");
			autocompleteEl.val($(this).val());
			searchModalEl1.modal('show');
    	});
    	
    	searchModalEl1.find('.save').click(function(){
   		 if (autocompleteEl.val() == '') {
   		 	return;
   		 }
   		 var $tr = tplTableEl.find("tr:eq("+selectedAddRow1+")");
   		 console.log($tr);
   		 $tr.find(".pid").text(selectedData.id);
   		 $tr.find(".pname").text(selectedData.name);
   		// $tr.find(".provide").text(selectedData.brandId);
   		 $tr.find(".provide").text();
   		 $tr.find(".pic").text(selectedData.price);
   		 $tr.find(".pnum").text(selectedData.num);
   		 searchModalEl1.modal('hide');
   		});
    	
    	
    	$('.pnum').click(function(){
			var text = $(this).text();
			updateNumModalEl.find('#num').val(text);
			updateNumModalEl.modal('show');
			updateNumModalEl.data('td',$(this));
		});
    	
    	updateNumModalEl.find('.save').click(function(){
    		var num = updateNumModalEl.find('#num').val();
    		updateNumModalEl.data('td').text(num);
    		updateNumModalEl.modal('hide');
    	});
    	
    	$('.heatup').click(function(){
			var text = $(this).text();
			addHeatUpModalEl.find('#heatup').val(text);
			addHeatUpModalEl.modal('show');
			addHeatUpModalEl.data('td',$(this));
		});
    	
    	addHeatUpModalEl.find('.save').click(function(){
    		var num = addHeatUpModalEl.find('#heatup').val();
    		addHeatUpModalEl.data('td').text(num);
    		addHeatUpModalEl.modal('hide');
    	});
    	
    	autocompleteEl.autocomplete({
    	    lookup : PageData.product,
    	    onSelect: function (suggestion) {
    	    	selectedData = suggestion.data;
    	       // alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
    	    }
    	});
    	
    	$('#btnSave').click(function(){
    		var productTemplate = {};
    		productTemplate.id = $("input[name=id]").val();
    		productTemplate.name = $("input[name=templateName]").val();
    		if(productTemplate.name == ""){
    			window.alert("请填写模板名称!");
    			return;
    		}
    		productTemplate.channelsType = $('#typeSelect option:selected').val();//选中的值
    		console.log("eq"+productTemplate.channelsType);
    		//type
    		
    		var productList = [];
    		var trs = $("#details").find("tr");
    		for (var i = 0; i < trs.length; i++) {
    			var tr = trs[i];
    			var pl = {};
    			pl.channelsId = $(tr).find("th.seq").text();
    		    var id = $(tr).find("td.pid").text();
    		    pl.productsId = id;
    		    if(pl.productsId == ""){
    				window.alert("请填写商品信息!");
    				return;
    			}
    		    pl.productsName = $(tr).find("td.pname").text();
    		    pl.brandPrice = $(tr).find("td.pic").text();
    		    pl.num = $(tr).find("td.pnum").text();
    		    if(pl.num == ""){
    		    	
    				window.alert("请填写商品数量!");
    				return;
    			}else if(pl.num == '0'){
    				window.alert("商品数量不能为0!");
    				return;
    			}
    		    pl.heatUpTime = $(tr).find("td.heatup").text();
    		    if(productTemplate.channelsType != 1 && pl.heatUpTime == ""){
    		    
    				window.alert("请填写加热时间!");
    				return;
    			}else if(productTemplate.channelsType != 1 && pl.heatUpTime == 0){
		    		window.alert("加热时间不能为0!");
    				return;
		    	}
    		    productList.push(pl);
    		}
    		productTemplate.productList  = productList;
    		console.log(productTemplate);
    		$.ajax({
    			url:ctx+"/PC/updateProductTemplateTetails",
    			data:JSON.stringify(productTemplate),
    			type: "POST",
    			datType: "JSON",
    			contentType: "application/json",
    			success:function(data){
    				console.log(data);
    				window.location.href=ctx+'/PC/getProductTempList';
    			}
    		})
    		
    	
    	})

    	$('#btnCancel').click(function(){
    		if(window.confirm('你确定要取消本次操作吗？')){
                //alert("确定");
    			window.location.href=ctx+'/PC/getProductTempList';
             }else{
                //alert("取消");
                return false;
            }
    	})
 }); 
    </script>
  </body>
</html>