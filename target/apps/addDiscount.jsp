<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 2018/8/13
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@ include file="base/top.jsp" %>

    <style>
        .modal-dialog {
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
        }

        .modal-content {
            /*overflow-y: scroll; */
            position: absolute;
            top: 0;
            bottom: 0;
            width: 100%;
        }

        .modal-body {
            overflow-y: scroll;
            position: absolute;
            top: 100px;
            bottom: 65px;
            width: 100%;
        }

        .modal-header .close {
            margin-right: 15px;
        }

        .modal-footer {
            position: absolute;
            width: 100%;
            bottom: 0;
        }

        td{
            max-width: 100px;
        }
    </style>
</head>
<body>
<%@ include file="base/up.jsp" %>
<div class="container-fluid">
    <div class="row">
    <%@ include file="base/left.jsp" %>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
 <!-- ----------------------------------------------------------------------- -->

        <form class="form-horizontal" role="form" method="post" action="${ctx}/PC/createDiscount">
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">活动名称</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入活动名称">
                </div>
            </div>
            <div class="form-group">
                <label for="objs" class="col-sm-2 control-label ">适用对象</label>
                <div class="radio-inline">
                    <label for="objs" style="margin-left: 15px;">
                        <input type="radio"  id="objs" name="obj" value="1" checked="checked">
                        所有人
                    </label>
                    <label for="objn" style="margin-left: 40px;">
                        <input type="radio"  id="objn" name="obj" value="2" >
                        新用户
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label for="unlimited" class="col-sm-2 control-label ">参与次数</label>
                <div class="radio-inline">
                    <label for="unlimited" style="margin-left: 15px;">
                        <input type="radio"  id="unlimited" name="num"  value="1" checked="checked">
                        无限制
                    </label>

                    <label for="num" style="margin-left: 40px;">
                        <input type="radio"  id="num" name="num" value="2">
                        <input type="text" id="numText" name="numText" size="3"/>
                        件/人
                    </label>

                    <label for="numDay" style="margin-left: 40px;">
                        <input type="radio"  id="numDay" name="num" value="3">
                        <input type="text" id="numDayText" name="numDayText" size="3"/>
                        件/人/天
                    </label>
                </div>

                <div class="form-group">
                    <label style="margin-left: 10px;" class="col-sm-2 control-label ">活动点位</label>
                    <button style="margin-top: 33px;margin-left: 10px;" id="PTEdit" type="button" class="btn btn-primary">编辑</button>
                    <table class="table table-bordered" style="width:30%;float:left;margin-top: 30px;">
                        <thead>
                        <tr>
                            <th>点位ID</th>
                            <th>点位名称</th>
                            <th>点位类型</th>
                        </tr>
                        </thead>
                        <tbody id="PTtible">

                        </tbody>
                    </table>
                </div>

                <div class="form-group">
                    <label style="margin-left: 10px;" class="col-sm-2 control-label ">活动商品</label>
                    <button style="margin-top: 33px;margin-left: 10px;" id="productEdit" type="button" class="btn btn-primary">编辑</button>
                    <table class="table table-bordered" style="width:30%;float:left;margin-top: 30px;">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>零售价</th>
                            <th>折扣（%）</th>
                        </tr>
                        </thead>
                        <tbody id="productTible">

                        </tbody>
                    </table>
                </div>

                <div class="form-group">
                    <label style="margin-left: 10px;" class="col-sm-2 control-label ">起止时间</label>
                    <div class="col-sm-2 ">
                        <input id="start" style="width: 200px;" type="text" value="" name="startDate" class="form-control" placeholder="开始时间">
                    </div>
                    <div class="col-sm-2">
                        <input id="end" style="width: 200px;margin-left: 50px;" type="text" value="" name="endDate" class="form-control" placeholder="结束时间">
                    </div>
                </div>

                <div class="form-group">
                    <label style="margin-left: 10px;" class="col-sm-2 control-label ">生效时间</label>
                    <div class="radio-inline">
                        <div>
                            <label for="allDay" style="margin-left: 15px;">
                                <input type="radio"  id="allDay" name="time" value="1" checked="checked">
                                全天
                            </label>
                        </div><br />
                        <div class="col-sm-3" style="margin-left: -15px;margin-top: 7px;">
                            <label for="timeSlice" style="margin-left: 15px;">
                                <input type="radio"  id="timeSlice" name="time" value="2">
                                指定時段
                            </label>
                        </div>

                        <div class="col-sm-4 " style="margin-left: -10px;">
                            <input id="startTime" style="width: 150px;" type="text" value="" name="startTime" class="form-control" placeholder="开始时间">
                        </div>
                        <div class="col-sm-4 " style="margin-left: 22px;">
                            <input id="endTime" style="width: 150px;" type="text" value="" name="endTime" class="form-control" placeholder="结束时间">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button id="cancel" type="button" class="btn btn-default">取消</button>
                        <button id="confirm" type="submit	" class="btn btn-success" style="margin-left: 50px;">确定</button>
                    </div>
                </div>
            </div>
        </form>

        <!-- 添加点位单  start-->
        <div id="PTEditModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:1000px">
                <div class="modal-content">
                    <div class="modal-header" >
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="PTmyModalLabel">
                            点位添加单
                        </h4>
                        <div  style="margin-top: 10px;">
                            <span>点位列表：</span>
                            <button id="allAdd" type="button" class="btn btn-default">全部添加</button>
                            <span style="margin-left: 362px;">已添加点位：</span>
                            <button id="allDel" type="button" class="btn btn-default">全部删除</button>
                        </div>

                    </div>

                    <div class="modal-body" >

                        <table class="table table-striped" style="display: inline; ">

                            <thead>
                            <tr>
                                <th>点位ID</th>
                                <th>点位名称</th>
                                <th>点位类型</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="PTadd">

                            </tbody>
                        </table>

                        <table class="table table-striped" style="display: inline;margin-left: 120px;">

                            <thead>
                            <tr>
                                <th>点位ID</th>
                                <th>点位名称</th>
                                <th>点位类型</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="PTdel">

                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button id="PTSubmitBtn" type="button" class="btn btn-primary" data-dismiss="modal">提交更改</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal -->
        </div>
        <!-- 添加点位单  end-->



        <!-- 添加商品单  start-->
        <div id="productModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width:1000px">
                <div class="modal-content">
                    <div class="modal-header" >
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">
                            商品添加单
                        </h4>
                        <div id="add" style="margin-top: 10px;">
                            <span>商品列表：</span>
                            <button id="allAddProduct" type="button" class="btn btn-default">全部添加</button>
                            <span style="margin-left: 362px;">已添加商品：</span>
                            <button id="allDelProduct" type="button" class="btn btn-default">全部删除</button>
                        </div>

                    </div>

                    <div class="modal-body" >

                        <table class="table table-striped" style="display: inline; ">

                            <thead>
                            <tr>
                                <th>商品ID</th>
                                <th>商品名称</th>
                                <th>零售价</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="productAdd">

                            </tbody>
                        </table>

                        <table class="table table-striped" style="display: inline;margin-left: 120px;">

                            <thead>
                            <tr>
                                <th>商品ID</th>
                                <th>商品名称</th>
                                <th>零售价</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="productDel">

                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button id="productSubmitBtn" type="button" class="btn btn-primary" data-dismiss="modal">提交更改</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal -->
        </div>
        <!-- 添加商品单  end-->
    </div>
<script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
    var numText = $("#numText");
    var num = $("#num");
    var numDayText = $("#numDayText");
    var numDay = $("#numDay");
    var testNum = /^[0-9]*$/;

    /*次数输入校验 start*/
    numText.change(function() {
        inputNum(num, numText);
    })

    numDayText.change(function() {
        inputNum(numDay, numDayText);
    })
    /*次数输入校验 end*/

    /*效验输入*/
    function inputNum(numId, textId) {
        if(!testNum.test(textId.val())) {
            textId.val("")
            alert("次数只能是数字！")
            return false;
        }
        numId.attr("checked", "checked");
    }/*日期*/
    var start = $('#start')
    var end = $('#end')

    var startTime = $('#startTime')
    var endTime = $('#endTime')

    $.fn.datetimepicker.dates['zdy'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        daysMin: ["日", "一", "二", "三", "四", "五", "六"],
        months: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
        monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
        today: "今天",
        format: "yyyy-mm-dd",
        titleFormat: "yyyy-mm-",
        weekStart: 1,
        suffix: [],
        meridiem: ["上午", "下午"]
    };

    /*起止時間 start*/
    start.datetimepicker({
        format: 'yyyy-mm-dd',
        /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
        language: 'zdy',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        //startDate:sdate,
        minView: 2,
        maxView: 4
    });

    end.datetimepicker({
        format: 'yyyy-mm-dd',
        /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
        language: 'zdy',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        //startDate:sdate,
        minView: 2,
        maxView: 4
    });

    end.change(function(){
        if (start.val()!=""&&end.val()!="") {

        } else{
            alert("开始时间不能为空")
        }
    })

    /*起止時間 end*/


    /*生效時間 start*/
    startTime.datetimepicker({
        format: 'hh:ii',
        /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
        language: 'zdy',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        startView:1,
        minView: 0,
        maxView: 1
    });

    endTime.datetimepicker({
        format: 'hh:ii',
        /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
        language: 'zdy',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        startView:1,
        minView: 0,
        maxView: 1
    });

    endTime.change(function(){
        if (startTime.val()!=""&&endTime.val()!="") {

        } else{
            alert("开始时间不能为空")
        }
    })

    /*自动选择*/
    startTime.change(function(){
        $("#timeSlice").attr("checked","checked")
    })

    /*生效時間 end*/

    /*按钮控制 start*/
    var confirm = $("#confirm");
    var PTEdit = $("#PTEdit");
    var productEdit = $("#productEdit");
    var productModal = $("#productModal");

    /*防重复点确认*/
    confirm.blur(function(){
        confirm.attr("disabled","disabled")
    })


    PTEdit.click(function(){
        $('#PTEditModal').modal('show');
    })



    productEdit.click(function(){
        productModal.modal('show');
    })
    /*按钮控制 end*/


    /*点位商品控制 start*/
    var PTList =${PTList}
    var productList = ${productList}

    var addPTed = new Set(); //存放已添加的点位的set

    var addProducts = new Set(); //存放已添加的商品的set

    window.onload = function(){
        for (var i = 0;i<PTList.length;i++) {
            $("#PTadd").append(
                '<tr id="PT'+i+'">'+
                '<td>'+PTList[i].id +'</td>'+
                '<td>'+PTList[i].name+'</td>'+
                '<td>'+PTList[i].type+'</td>'+
                '<td>'+'<button onclick="addPT('+i+')" type="button" class="btn btn-default">添加</button>'+'</td>'+
                '</tr>'
            );
        }

        for (var i = 0; i < productList.length; i++) {
            $("#productAdd").append(
                '<tr id="product'+i+'">'+
                '<td>'+productList[i].id +'</td>'+
                '<td>'+productList[i].name+'</td>'+
                '<td>'+productList[i].price+'</td>'+
                '<td>'+'<button onclick="addProduct('+i+')" type="button" class="btn btn-default">添加</button>'+'</td>'+
                '</tr>'
            );
        }

    }


    /*商品控制============start==============*/
    /*商品添加按钮控制*/
    function addProduct(i){
        addProducts.add(productList[i])
        var productId = "product"+i;
        document.getElementById(productId).remove();
        productDelAdd(productList[i],i)
        console.log(addProducts)

    }

    /*已删除商品添加操作*/
    function productDelAdd(product,i){
        $("#productDel").append(
            '<tr id="proDel'+i+'">'+
            '<td>'+product.id +'</td>'+
            '<td>'+product.name+'</td>'+
            '<td>'+product.price+'</td>'+
            '<td>'+'<button onclick="delProduct('+i+')" type="button" class="btn btn-default">删除</button>'+'</td>'+
            '</tr>'
        );
    }

    /*商品删除按钮控制*/
    function delProduct(i){
        addProducts.delete(productList[i]);
        var productId = "proDel"+i;
        document.getElementById(productId).remove();
        productAdd(productList[i],i)
        console.log(addProducts)
    }

    /*商品列表添加操作*/
    function productAdd(product,i){
        $("#productAdd").append(
            '<tr id="product'+i+'">'+
            '<td>'+product.id +'</td>'+
            '<td>'+product.name+'</td>'+
            '<td>'+product.price+'</td>'+
            '<td>'+'<button onclick="addProduct('+i+')" type="button" class="btn btn-default">添加</button>'+'</td>'+
            '</tr>'
        );
    }

    /*商品全部添加按钮控制*/
    $("#allAddProduct").click(function(){
        $("#productAdd").children('tr').remove();
        $("#productDel").children('tr').remove();

        addProducts = new Set(productList);
        productAllAdd(addProducts)

    })

    /*商品全部删除按钮控制*/
    $("#allDelProduct").click(function(){
        $("#productAdd").children('tr').remove();
        $("#productDel").children('tr').remove();
        productAll(addProducts)
        addProducts = new Set();
        console.log(addProducts);
    })

    /*给商品列表批量添加*/
    function productAll(products){
        var arr = Array.from(products)
        for (var i = 0; i < arr.length; i++) {
            $("#productAdd").append(
                '<tr id="product'+i+'">'+
                '<td>'+arr[i].id +'</td>'+
                '<td>'+arr[i].name+'</td>'+
                '<td>'+arr[i].price+'</td>'+
                '<td>'+'<button onclick="addProduct('+i+')" type="button" class="btn btn-default">添加</button>'+'</td>'+
                '</tr>'
            );
        }
    }


    /*给已添加商品批量添加*/
    function productAllAdd(products){
        var arr = Array.from(products)
        for (var i = 0; i < arr.length; i++) {
            $("#productDel").append(
                '<tr id="proDel'+i+'">'+
                '<td>'+arr[i].id +'</td>'+
                '<td>'+arr[i].name+'</td>'+
                '<td>'+arr[i].price+'</td>'+
                '<td>'+'<button onclick="delProduct('+i+')" type="button" class="btn btn-default">删除</button>'+'</td>'+
                '</tr>'
            );
        }
    }

    /*商品提交按钮控制*/
    var productTible = $("#productTible")
    $("#productSubmitBtn").click(function(){
        productTible.children('tr').remove();
        productTibleAdd();
    })

    /*主表商品添加*/
    function productTibleAdd(){
        var arr = Array.from(addProducts)
        for (var i = 0;i<arr.length;i++) {
            productTible.append(
                '<tr id="productTible'+i+'">'+
                '<td>'+arr[i].id +'</td>'+
                '<input type="hidden" name="activityProducts['+i+'].id" value="'+arr[i].id+'" />'+
                '<td>'+arr[i].name+'</td>'+
                '<input type="hidden" name="activityProducts['+i+'].name" value="'+arr[i].name+'" />'+
                '<td>'+arr[i].price+'</td>'+
                '<input type="hidden" name="activityProducts['+i+'].priceDTO" value="'+arr[i].price+'" />'+
                '<td>'+'<input type="text" name="activityProducts['+i+'].discount" class="form-control">'+'</td>'+
                '</tr>'
            );
        }
    }
    /*商品控制============end==============*/


    /*点位控制============start==============*/
    /*点位添加按钮控制*/
    function addPT(i){
        addPTed.add(PTList[i]);
        var PTid = "PT"+i;
        PTAddDel(PTid)
        PTDelAdd(PTList[i],i)
        console.log(addPTed);
    }
    /*删除点位列表里的元素*/
    function PTAddDel(PTid){
        document.getElementById(PTid).remove();
    }

    /*已添加点位添加操作*/
    function PTDelAdd(PT,i){
        $("#PTdel").append(
            '<tr id="PTDel'+i+'">'+
            '<td>'+PT.id +'</td>'+
            '<td>'+PT.name+'</td>'+
            '<td>'+PT.type+'</td>'+
            '<td>'+'<button onclick="PTDel('+i+')" type="button" class="btn btn-default">删除</button>'+'</td>'+
            '</tr>'
        )
    }

    /*已添加点位批量添加操作*/
    function PTDelAddAll(PTList){
        var arr = Array.from(PTList);
        for (var i = 0;i<arr.length;i++) {
            $("#PTdel").append(
                '<tr id="PTDel'+i+'">'+
                '<td>'+arr[i].id +'</td>'+
                '<td>'+arr[i].name+'</td>'+
                '<td>'+arr[i].type+'</td>'+
                '<td>'+'<button onclick="PTDel('+i+')" type="button" class="btn btn-default">删除</button>'+'</td>'+
                '</tr>'
            )
        }

    }

    /*删除按钮控制*/
    function PTDel(i){
        var PTid = "PTDel"+i;
        addPTed.delete(PTList[i])
        PTAddDel(PTid)
        PTAdd(PTList[i],i)
        console.log(addPTed);
    }

    /*点位列表添加操作*/
    function PTAdd(PT,i){
        $("#PTadd").append(
            '<tr id="PT'+i+'">'+
            '<td>'+PT.id +'</td>'+
            '<td>'+PT.name+'</td>'+
            '<td>'+PT.type+'</td>'+
            '<td>'+'<button onclick="addPT('+i+')" type="button" class="btn btn-default">添加</button>'+'</td>'+
            '</tr>'
        )
    }

    /*点位列表批量添加操作*/
    function PTAddAll(PTList){
        var arr = Array.from(PTList)
        for (var i = 0;i<arr.length;i++) {
            $("#PTadd").append(
                '<tr id="PT'+i+'">'+
                '<td>'+arr[i].id +'</td>'+
                '<td>'+arr[i].name+'</td>'+
                '<td>'+arr[i].type+'</td>'+
                '<td>'+'<button onclick="addPT('+i+')" type="button" class="btn btn-default">添加</button>'+'</td>'+
                '</tr>'
            )
        }
    }

    /*全部添加按钮控制*/
    $("#allAdd").click(function(){

        $("#PTadd").children('tr').remove();
        $("#PTdel").children('tr').remove();

        addPTed = new Set(PTList);
        PTDelAddAll(addPTed)

    })


    /*全部删除按钮控制*/
    $("#allDel").click(function(){
        $("#PTadd").children('tr').remove();
        $("#PTdel").children('tr').remove();

        PTAddAll(PTList);
        addPTed =new Set();

    })


    /*提交更改按钮控制*/
    var PTtible = $("#PTtible");
    $("#PTSubmitBtn").click(function(){
        PTtible.children('tr').remove();
        PTTibleAdd();
    })

    /*主表点位添加*/
    function PTTibleAdd(){
        var arr = Array.from(addPTed)
        for (var i = 0;i<arr.length;i++) {
            PTtible.append(
                '<tr id="PTTible'+i+'">'+
                '<td>'+arr[i].id +'</td>'+
                '<input type="hidden" name="activityPTs['+i+'].id" value="'+arr[i].id+'" />'+
                '<td>'+arr[i].name+'</td>'+
                '<input type="hidden" name="activityPTs['+i+'].name" value="'+arr[i].name+'" />'+
                '<td>'+arr[i].type+'</td>'+
                '<input type="hidden" name="activityPTs['+i+'].type" value="'+arr[i].type+'" />'+
                '</tr>'
            );
        }
    }
    /*点位控制============start==============*/
    /*点位商品控制 end*/
</script>
</body>
</html>
