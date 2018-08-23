
$(function(){
		$('#btnSaveUpdate').click(function(){
    		var nodes = {};
    		nodes.id = $("input[name=id]").val();
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
    		var nodeVmsList = [];
    		var trs = $("#nodeVms").find("tr");
    		console.log("lenth"+trs.length);
    		if(trs.length > 1){
    			window.alert("一个点位暂时只能对应一台设备!");
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
    			url:ctx+"/PC/updateNodeVms",
    			data:JSON.stringify(nodes),
    			type: "POST",
    			datType: "JSON",
    			contentType: "application/json",
    			success:function(data){
    				console.log(data);
    				window.alert("修改完成");
    				window.location.href=ctx+'/PC/getNodesList';
    			}
    		})
    	})

	$('#btnCancel').click(function(){
		window.location.href=ctx+'/PC/getNodesList';
	})

})