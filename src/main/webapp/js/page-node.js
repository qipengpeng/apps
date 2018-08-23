/**
点位新增、编辑页面
*/

//页面上初始数据
var PageData = {

	device:[
		    { value: '1001 俊鹏32格子柜', data: {
			id:'1001',
			type:1,
			name:'俊鹏32格子柜',
			pwd:'xxxxx1',
			tpl:''
			}
		},
	     { value: '1002 俊鹏32格子柜', data: {
			id:'1002',
			type:1,
			name:'俊鹏32格子柜',
			pwd:'xxxxx1',
			tpl:''
		  }
		}
	],
	tpl:[
		{id:'1',name:'模板1'},
		{id:'1',name:'模板2'}
	]
}


$(function(){
		$.ajax({  
	        type : "GET",  
	        url : ctx+"/PC/findVendorList",  
	        data : {  
	        },  
	        async : false,  
	        cache : false,  
	        datatype : "JSON",  
	        success : function(data) {  
	        	var datas = [];
	        	$.each(data, function(index, obj) {//拼接成json数据
	        		var o = {};
	        		o.value = ''+obj.id+obj.producer;
	        		o.data = obj;
	        		datas.push(o);
	            });
	        	PageData.device = datas;
	        	console.log(PageData.device);
	        },  
	        //调用出错执行的函数  
	        error : function() {  
	            alert("异常");  
	        }  
	
	    }); 
		
		/*$.ajax({  
	        type : "GET",  
	        url : ctx+"/PC/findProductTemp",  
	        data : {  
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
	            alert("异常");  
	        }  
	    });*/
		
		
	 var rowHTML = ['<tr>',
				'<th ><input type="text"  value="" class="d_vm form-control" /></th>',
				'<td class="d_id"></td>',
				'<td class="d_pwd"></td>',
				'<td class="d_type"></td>',
				'<td>',
				'  <select class="tpl-select form-control">',
				'  </select>',
				'</td>',
				'<td class="del"><a>删除</a></td>',
				'</tr>'].join("");

	// 补全输入框
	var autocompleteEl = $('#autocomplete')
	var tableEl = $('#tplTable tbody');

	// 初始化设备种类选项框
	// for(var i = 0; i < PageData.device.length; i++){
	// 	$("<option value='"+PageData.device[i].id+"'>"+PageData.device[i].name+"</option>").data(PageData.device[i]).appendTo();
	// }

	autocompleteEl.autocomplete({
	    lookup: PageData.device,
	    onSelect: function (suggestion) {
	    	var data = suggestion.data;
	    	console.log('123');
	    	var $row = $(rowHTML);
	    	$row.appendTo(tableEl);
	    	$row.find(".d_id").text(data.id);
	    	$row.find(".d_pwd").text(data.password);
	    	$row.find(".d_type").text(data.type);
	    	
	    	$.ajax({  
		        type : "GET",  
		        url : ctx+"/PC/findProductTemp",  
		        data : {  
		        	channelsType : data.type
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
		    });
	    	
	    	var tplSel = $row.find('.tpl-select');
	    	for (var i = 0;i<PageData.tpl.length; i++) {
	    		$("<option value='"+PageData.tpl[i].id+"'>"+PageData.tpl[i].name+"</option>").data(PageData.tpl[i]).appendTo(tplSel);
	    	}
	    	
	    	autocompleteEl.val('');

	    	$row.find('.del').click(function(){
	    		$(this).parent("tr").remove();
	    	});

	    }
	});

	   
	$('#btnSave').click(function(){
		var nodes = {};
		nodes.name = $("input[name=name]").val();
		if(nodes.name == ""){
			window.alert("请填写点位名称!");
			return;
		}
		nodes.address = $("input[name=address]").val();
		if(nodes.address == ""){
			window.alert("请填写正确的地址信息!");
			return;
		}
		nodes.longitude=$(".lng").text();
		nodes.latitude=$(".lat").text();
		console.log(nodes.latitude);
		var nodeVmsList = [];
		var trs = $("#nodeVms").find("tr");
		
		console.log("lenth:"+trs.length);
		if(trs.length > 1){
			window.alert("一个点位暂时只能添加一台设备!");
			return;
		}
		
		reg=/^[A-Z]+$/;
		for(var i = 0; i < trs.length; i++){
			if(trs.length > 1){
				window.alert("警告:一个点位暂时只能添加一台设备!");
				return;
			}
			var tr = trs[i];
			var nds = {};
			nds.nodeVmName = $(tr).find("input.d_vm").val();
			if(nds.nodeVmName == ""){
				window.alert("请填写设备代号!");
				return;
			}else if (!reg.test(nds.nodeVmName)) {
				window.alert("请填写正确的设备代号!");
				return;
			}
			nds.vendorId = $(tr).find("td.d_id").text();
			nds.vendorPassword = $(tr).find("td.d_pwd").text();
			nds.vendorTypeId = $(tr).find("td.d_type").text();
			nds.tempId = $(tr).find("select.tpl-select").val();
			if(nds.tempId == ""){
				window.alert("请填写选择商品模板!");
				return;
			}
			nodeVmsList.push(nds);
		}
		nodes.nodeVmsList = nodeVmsList;
		console.log(nodes);
		
		$.ajax({
			url:ctx+"/PC/addNodeVms",
			data:JSON.stringify(nodes),
			type: "POST",
			datType: "JSON",
			contentType: "application/json",
			success:function(data){
				console.log(data);
				window.location.href=ctx+'/PC/getNodesList';
			}
		})
		
	})

	$('#btnCancel').click(function(){
		window.location.href=ctx+'/PC/getNodesList';
	})

})