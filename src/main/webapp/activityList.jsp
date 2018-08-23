<%--
  Created by IntelliJ IDEA.
  User: jack
  Date: 2018/8/13
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@ include file="base/top.jsp" %>
    <style>
        .switch{
            width: 55px;
            margin: 5px 0px 0 5px;
        }
        .btn_fath{
            margin-top: 5px;
            position: relative;
            border-radius: 10px;
        }
        .btn1{
            float: left;
        }
        .btn2{
            float: right;
        }
        .btnSwitch{
            height: 20px;
            width: 15px;
            border:none;
            color: #fff;
            line-height: 20px;
            font-size: 10px;
            text-align: center;
            z-index: 1;
        }
        .move{
            z-index: 20;
            width: 20px;
            border-radius: 20px;
            height: 20px;
            position: absolute;
            cursor: pointer;
            border: 1px solid #828282;
            background-color: #f1eff0;
            box-shadow: 1px 2px 2px 1px #fff inset,0 0 5px 1px #999;
        }
        .on .move{
            left: 40px;
        }
        .on.btn_fath{
            background-color: #5281cd;
        }
        .off.btn_fath{
            background-color: #828282;
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
        <div id="add" style="padding-bottom: 10px"><a class="btn btn-primary" >新增</a></div>
        <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>活动名称</th>
            <th>活动类型</th>
            <th>适用对象</th>
            <th>活动状态</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>操作</th>
            <th>启用状态</th>
        </tr>
        </thead>

        <tbody>
            <tr>
                <th>3</th>
                <td>55</td>
                <td>满单返券</td>
                <td>所有人</td>
                <td>
                    进行中
                </td>
                <td>yyyy-mm-dd hh:mm:ss</td>
                <td>yyyy-mm-dd hh:mm:ss</td>
                <td>编辑</td>
                <td>
                    <div class="switch">
                        <div class="btn_fath clearfix on" onclick="toogle(this,123)">
                            <div class="move" data-state="on"></div>
                            <div class="btnSwitch btn1">ON</div>
                            <div class="btnSwitch btn2 ">OFF</div> </div>
                    </div>
                </td>
            </tr>
        </tbody>
        </table>
    </div>

    <!-- 活动添加单 start -->
    <div  id="addOrderModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        活动添加单
                    </h4>
                </div>
                <div class="modal-body">
                    <span>单品活动：</span>
                    <button id="addDiscount" type="button" class="btn btn-primary">添加折扣 </button>
                    <button id="addFixedPrice" type="button" class="btn btn-primary">添加一口价 </button>
                </div>

                <div class="modal-body">
                    <span>订单活动：</span>
                    <button id="addReturnTicket" type="button" class="btn btn-primary">添加满单返券</button>
                </div>

                <div class="modal-body">
                    <span>特殊活动：</span>
                    <button id="addBanner" type="button" class="btn btn-primary">添加Banner活动</button>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <!-- 活动添加单 end -->

    <script type="text/javascript">

        var add = $("#add");
        var addDiscount = $("#addDiscount");
        var addFixedPrice = $("#addFixedPrice");
        var addReturnTicket = $("#addReturnTicket");
        var addBanner =$("#addBanner");

        function toogle(th,nodeId){
            var ele = $(th).children(".move");
            if(ele.attr("data-state") == "on"){
                ele.animate({left: "0"}, 100, function(){
                    ele.attr("data-state", "off");
                    //alert("关！");
                });
                $.ajax({
                    type : "GET",
                    url : ctx+"/PC/updateNodeState",
                    data : {
                        id : nodeId,
                        state : 2
                    },
                    async : false,
                    cache : false,
                    datatype : "JSON",
                    success : function() {
                        alert("关闭点位:"+nodeId);
                    },
                    //调用出错执行的函数
                    error : function() {
                        alert("异常");
                    }
                });
                $(th).removeClass("on").addClass("off");
            }else if(ele.attr("data-state") == "off"){
                ele.animate({left: '40px'}, 100, function(){
                    $(this).attr("data-state", "on");
                    //alert("开！");
                });
                $.ajax({
                    type : "GET",
                    url : ctx+"/PC/updateNodeState",
                    data : {
                        id : nodeId,
                        state : 1
                    },
                    async : false,
                    cache : false,
                    datatype : "JSON",
                    success : function() {
                        alert("开启点位:"+nodeId);
                    },
                    //调用出错执行的函数
                    error : function() {
                        alert("异常");
                    }
                });
                $(th).removeClass("off").addClass("on");
            }
        }

        /*二级页面的添加跳转操作 start*/
        add.click(function(){
            $('#addOrderModal').modal('show');
        })

        addDiscount.click(function(){
            window.location.href="${ctx}/PC/addDiscount"
        })

        addFixedPrice.click(function(){
            window.location.href="www.baidu.com"
        })

        addReturnTicket.click(function(){
            window.location.href="www.baidu.com"
        })

        addBanner.click(function(){
            window.location.href="www.baidu.com"
        })

        /*二级页面的添加跳转操作 end*/

    </script>
</body>
</html>
