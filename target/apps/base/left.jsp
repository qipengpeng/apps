<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar" id="left">
            <li id="products"><a href="${ctx}/PC/getProductsList">商品管理 <span class="sr-only">(current)</span></a></li>
            <li id="vendors"><a href="${ctx}/PC/getVendorsList">设备管理</a></li>
            <li id="productTemp"><a href="${ctx}/PC/getProductTempList">商品模板</a></li>
            <li id="nodes"><a href="${ctx}/PC/getNodesList">点位管理</a></li>
            <li id="routeDetails"><a href="${ctx}/PC/getRouteDetailsList">配送管理</a></li>
            <li id="demands"><a href="${ctx}/PC/getDemandsList">需求记录</a></li>
            <li id="deliveries"><a href="${ctx}/PC/getDeliveriesList">交割记录</a></li>
            <li id="replenishmentlogs"><a href="${ctx}/PC/getReplenishmentLogsList">补货记录</a></li>
            <li id="inventory"><a href="${ctx}/PC/getInventoryList">清货报损</a></li>
            <li id="orders"><a href="${ctx}/PC/getOrdersList?pageNow=1">订单管理</a></li>
            <li><a href="${ctx}/PC/activityList?pageNow=1">活动管理</a></li>
            <li><a href="${ctx}/outLogin">注销</a></li>
          </ul>
</div>
	<script type="text/javascript">
		$(function(){
		    $("#left li").click(function() {
		        $(this).siblings('li').removeClass('active');  // 删除其他兄弟元素的样式
		        $(this).addClass('active');                            // 添加当前元素的样式
		    });
		
		}); 
	</script>
        