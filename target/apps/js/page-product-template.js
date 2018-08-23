/**
商品模板页面
*/

//页面上初始数据
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
	 var rowHTML = ['<tr data-id="1">',
				'	<th class="seq"></th>',
				'	<td class="pid"></td>',
				'	<td class="pname"></td>',
				'	<td class="provide"></td>',
				'	<td class="pic"></td>',
				'	<td class="pnum"></td>',
				//add   加热时间    20180612
				'	<td class="heatup"></td>',
				'	<td class="clear"><a>清空</a></td>',
				'	<td class="copy"><a>复制</a></td>',
				'</tr>'].join("");
	 //32货道使用模板
	 var rowHTML1 = ['<tr data-id="1">',
			'	<th class="seq"></th>',
			'	<td class="pid"></td>',
			'	<td class="pname"></td>',
			'	<td class="provide"></td>',
			'	<td class="pic"></td>',
			'	<td class="pnum"></td>',
			'	<td class="clear"><a>清空</a></td>',
			'	<td class="copy"><a>复制</a></td>',
			'</tr>'].join("");

	// 设备种类选项框
	var typeSelectEl = $('#typeSelect');
	// 货道模板表格
	var tplTableEl = $('#tplTable tbody');
	// 补全输入框
	var autocompleteEl = $('#autocomplete');
	// 搜索商品框
	var searchModalEl = $('#searchModal');
	// 复制商品框
	var copyModalEl = $('#copyModal');
	// 更新商品数量
	var updateNumModalEl = $('#updateNumModal');
	//add   加热时间    20180612
	var addHeatUpModalEl = $('#addHeatUpModal');
	
	// 当前设备货道数量
	var seqMax = 0;
	// 当前选择添加商品的货道
	var selectedAddRow = 0;
	// 当前选择复制商品的货道
	var selectedCopyRow = 0;
	// 当前匹配到的搜索数据
	var selectedData = null;

	// 初始化设备种类选项框
	for(var i = 0; i < PageData.device.length; i++){
		$("<option value='"+PageData.device[i].type+"'>"+PageData.device[i].name+"</option>").data('num',PageData.device[i].num).appendTo(typeSelectEl);
	}

	typeSelectEl.change(function(){
		// 获取货道数量，创建表格
		seqMax =  typeSelectEl.find("option:selected").data('num');
		createTableTpl(seqMax);
	})

	function createTableTpl( num ){
		// todo 有填写内容，判断是否切换
		tplTableEl.empty();
		for(var i = 0; i < num; i++){
			if(num == 32){
				var $row = $(rowHTML1);
			}else {
				var $row = $(rowHTML);
			}
			$row.find(".seq").html(i+1+'');
			tplTableEl.append($row);
			$row.find('.pid').click(function(){
				//$(this).html('<input type="text" />');
				var rowNum = $(this).parent("tr").prevAll().length;  
				var seq = $(this).parent("tr").find("th.seq").text();
				selectedAddRow = rowNum;
				$('#searchModal .modal-title').text("货道 "+seq+" 添加商品");
				autocompleteEl.val($(this).val());
				searchModalEl.modal('show');
			});

			$row.find('.clear').click(function(){
				$(this).parent("tr").find(".pid").text("");
				$(this).parent("tr").find(".pname").text("");
				$(this).parent("tr").find(".provide").text("");
				$(this).parent("tr").find(".pic").text("");
				$(this).parent("tr").find(".pnum").text("");
				//add   加热时间    20180612
				$(this).parent("tr").find(".heatup").text("");
			});

			$row.find('.copy').click(function(){
				var rowNum = $(this).parent("tr").prevAll().length;  
				selectedCopyRow = rowNum;
				$("#startInput").val(Math.min(rowNum+1,seqMax));//默认复制下一行；
				copyModalEl.modal('show');
			});

			$row.find('.pnum').click(function(){
				var text = $(this).text();
				updateNumModalEl.find('#num').val(text);
				updateNumModalEl.modal('show');
				updateNumModalEl.data('td',$(this));
			});
			
			$row.find('.heatup').click(function(){
				var text = $(this).text();
				addHeatUpModalEl.find('#heatup').val(text);
				addHeatUpModalEl.modal('show');
				addHeatUpModalEl.data('td',$(this));
			});
		}
	}


	searchModalEl.find('.save').click(function(){
		 if (autocompleteEl.val() == '') {
		 	return;
		 }
		 var $tr = tplTableEl.find("tr:eq("+selectedAddRow+")");
		 $tr.find(".pid").text(selectedData.id);
		 $tr.find(".pname").text(selectedData.name);
		// $tr.find(".provide").text(selectedData.brandId);
		 $tr.find(".provide").text();
		 $tr.find(".pic").text(selectedData.price);
		 $tr.find(".pnum").text(selectedData.num);
		 searchModalEl.modal('hide');
	});

	copyModalEl.find('.save').click(function(){
		 
		 $startInput = $("#startInput");
		 $endInput = $("#endInput");

		 var st = parseInt($startInput.val());
		 var ed = parseInt($endInput.val()); 

		 if (st == 0 && ed == 0) {
		 	return;
		 }

		 var $tr = tplTableEl.find("tr:eq("+selectedCopyRow+")");
		 var pid = $tr.find(".pid").text();
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
		 	 $desTR.find(".pid").text(pid);
			 $desTR.find(".pname").text(pname);
			 $desTR.find(".provide").text(provide);
			 $desTR.find(".pic").text(pic);
			 $desTR.find(".pnum").text(pnum);
			 //add   加热时间    20180612
			 $desTR.find(".heatup").text(heatup);
		 }
	})

	updateNumModalEl.find('.save').click(function(){
		var num = updateNumModalEl.find('#num').val();
		updateNumModalEl.data('td').text(num);
		updateNumModalEl.modal('hide');
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
		productTemplate.name = $("input[name=templateName]").val();
		console.log(productTemplate.name);
		if(productTemplate.name == ""){
			window.alert("请填写模板名称!");
			return;
		}
		productTemplate.channelsType = $('#typeSelect option:selected').val();//选中的值
		if(productTemplate.channelsType == "请选择货道类型"){
			window.alert("请选择货道类型!");
			return;
		}
		console.log(productTemplate.channelsType);
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
		    pl.brandId = $(tr).find("td.provide").text();
		    pl.brandPrice = $(tr).find("td.pic").text();
		    pl.num = $(tr).find("td.pnum").text();
		    
		    if(pl.num == ""){
				window.alert("请填写商品数量!");
				return;
			}
		    pl.heatUpTime = $(tr).find("td.heatup").text();
		    if(productTemplate.channelsType != 1 && pl.heatUpTime == ""){
				window.alert("请填写加热时间!");
				return;
			}
		    productList.push(pl);
		}
		productTemplate.productList  = productList;
		console.log(productTemplate);
		$.ajax({
			url:ctx+"/PC/addProductTemplateTetails",
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

})