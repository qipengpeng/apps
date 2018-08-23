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
       <form class="form-horizontal" action="${ctx}/PC/findOrders" method="post">
<!-- ----------------------------------------------------------------------- -->
       <h3 style="margin:30px">订单查询</h3>
        <div class="form-group">
          <div class="col-sm-3">
            <input  type="text" name="nodeId" class="form-control" placeholder="点位ID">
          </div>
           <div class="col-sm-3">
            <input  type="text" name="nodeName" class="form-control" placeholder="点位名称">
          </div>
           <div class="col-sm-3">
            <input  type="text" name="productId" class="form-control" placeholder="商品ID">
          </div>
           <div class="col-sm-3">
            <input  type="text" name="productName" class="form-control" placeholder="商品名称">
          </div>
        </div>

       <div class="form-group">
           <div class="col-sm-3 ">
               <input id="start"  type="text"  name="startDate" class="form-control" placeholder="开始时间">
           </div>
           <div class="col-sm-3">
               <input id="end" type="text"  name="endDate" class="form-control" placeholder="结束时间">
           </div>
           <div class="col-sm-3">
               <select id="payStatus" class="form-control" name="shippingState" title="请选择出货状态">
                   <option value="0">全部</option>
                   <option value="1">未出货</option>
                   <option value="2">出货成功</option>
                   <option value="3">出货失败</option>
                   <option value="4">超时取消</option>
                   <option value="5">商品制作中</option>
               </select>
           </div>

           <div class="col-sm-3">
               <select  class="form-control" name="payStatus" title="支付状态">
                   <option value="0">全部</option>
                   <option value="1">待支付</option>
                   <option value="2">支付成功</option>
                   <option value="3">支付失败</option>
                   <option value="4">退款</option>
               </select>
           </div>
       </div>

        <div class="form-group">
          <div class="col-sm-3">
            <input  type="text" name="id" class="form-control" placeholder="支付单号">
          </div>
           <div class="col-sm-3">
            <input type="submit" class="btn btn-primary btn-lg" value="查询" onclick="return find(this.form)">
            <button id="js-export" type="button" class="btn btn-primary btn-lg" value=""  >导出Excel</button>
            <!-- <button type="button" class="btn btn-primary btn-lg" onclick="getOrder()">查询</button> -->
          </div>
        </div>
      </form>

      <table class="table table-bordered">
        <thead>
          <tr>
            <th>ID</th>
            <th>用户ID</th>
            <th>点位名称</th>
            <th>设备ID</th>
            <th>设备类型</th>
            <th>商品名称</th>
            <th>实际价格（元）</th>
            <th>支付价格（元）</th>
            <th>出货状态</th>
            <th>支付状态</th>
            <th>支付方式</th>
            <th>订单时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="orders" items="${ordersList }">
          <tr>
            <th>${orders.id }</th>
            <td>${orders.userId }</td>
            <td>${orders.nodeName }</td>
            <td>${orders.vendorId }</td>
            <td>
            <c:if test="${orders.channelsType == 1 }">32格子柜</c:if>
            <c:if test="${orders.channelsType == 2 }">冰山机</c:if>
            </td>
            <td>${orders.productName }</td>
            <td>${orders.totalPrice/100 }</td>
            <td>${orders.payPrice/100 }</td>
            <td>
            <c:if test="${orders.orderStatus == 1 }">未出货</c:if>
            <c:if test="${orders.orderStatus == 2 }">出货成功</c:if>
            <c:if test="${orders.orderStatus == 3 }">出货失败</c:if>
            <c:if test="${orders.orderStatus == 4 }">出货超时</c:if>
            <c:if test="${orders.orderStatus == 5 }">设备占用(自动退款)</c:if>
            </td>
            <td>
            <c:if test="${orders.payStatus == 1 }">待支付</c:if>
            <c:if test="${orders.payStatus == 2 }">支付成功</c:if>
            <c:if test="${orders.payStatus == 3 }">支付失败</c:if>
            <c:if test="${orders.payStatus == 4 }">已退款</c:if>
            </td>
            <td>
            <c:if test="${orders.payMethod == 1 }">微信</c:if>
            <c:if test="${orders.payMethod == 2 }">友宝钱包</c:if>
            </td>
            <td>${orders.payTime }</td>
            <td>
            <c:if test="${orders.payStatus == 2 }">
            <a class="refund" href="#" data-id="${orders.id }" data-pay="${orders.payPrice }" data-total="${orders.totalPrice }" data-trade="xxxxxxx" data-user="${orders.userId }" >退款</a>
            </c:if>
            </td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
      <br />
        <!-- 分页查询 -->
        <div class="pager">
            <font size="2">共 ${page.totalPageCount} 页</font> <font size="2">第
            ${page.pageNow} 页</font> <a href="${ctx}/PC/${page.scope}?pageNow=1&findType=${findType}">首页</a>
            <c:choose>
                <c:when test="${page.pageNow - 1 > 0}">
                    <a href="${ctx}/PC/${page.scope}?pageNow=${page.pageNow - 1}&findType=${findType}">上一页</a>
                </c:when>
                <c:when test="${page.pageNow - 1 <= 0}">
                    <a href="${ctx}/PC/${page.scope}?pageNow=1&findType=${findType}">上一页</a>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${page.totalPageCount==0}">
                    <a href="${ctx}/PC/${page.scope}?pageNow=${page.pageNow}&findType=${findType}">下一页</a>
                </c:when>
                <c:when test="${page.pageNow + 1 < page.totalPageCount}">
                    <a href="${ctx}/PC/${page.scope}?pageNow=${page.pageNow + 1}&findType=${findType}">下一页</a>
                </c:when>
                <c:when test="${page.pageNow + 1 >= page.totalPageCount}">
                    <a href="${ctx}/PC/${page.scope}?pageNow=${page.totalPageCount}&findType=${findType}">下一页</a>
                </c:when>
            </c:choose>
            <c:choose>
                <c:when test="${page.totalPageCount==0}">
                    <a href="${ctx}/PC/${page.scope}?pageNow=${page.pageNow}&findType=${findType}">尾页</a>
                </c:when>
                <c:otherwise>
                    <a href="${ctx}/PC/${page.scope}?pageNow=${page.totalPageCount}&findType=${findType}">尾页</a>
                </c:otherwise>
            </c:choose>
        </div>
       
<!-- ----------------------------------------------------------------------- -->
	 </div>
	</div>
  </div>
    
  
	<!-- 订单退款 -->
    <div  id="refundModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
              &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
             确认退款
            </h4>
          </div>
          <div class="modal-body">
              <form class="form-horizontal">
                   <div class="form-group">
                      <label class="col-sm-3 control-label">订单ID</label>
                      <div  class="col-sm-7">
                        <p id="refund_order_id" class="form-control-static">email@example.com</p>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-sm-3 control-label">支付单号</label>
                      <div  class="col-sm-7">
                        <p id="refund_trade" class="form-control-static">email@example.com</p>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-sm-3 control-label">用户ID</label>
                      <div class="col-sm-7">
                        <p id="refund_user_id" class="form-control-static">email@example.com</p>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-sm-3 control-label">商品原价</label>
                      <div  class="col-sm-7">
                        <p id="refund_total" class="form-control-static">email@example.com</p>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-sm-3 control-label">支付价格</label>
                      <div  class="col-sm-7">
                        <p id="refund_pay" class="form-control-static">email@example.com</p>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-sm-3 control-label">预计退款</label>
                      <div class="col-sm-7">
                        <p id="refund_refund" class="form-control-static">email@example.com</p>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="col-sm-3 control-label">退款原因</label>
                      <div  class="col-sm-7">
                          <select id="reasonSelect" class="form-control" title="请选择退款原因">
                              <option value="1">出货失败</option>
                              <option value="2">出错商品</option>
                              <option value="3">商品质量问题</option>
                              <option value="4">测试</option>
                              <option value="5">其他原因</option>
                          </select>
                      </div>
                    </div>

                    <div id="otherDiv" class="form-group" style="display:none;">
                      <div class="col-md-offset-3 col-sm-7">
                          <input type="text" class="form-control" placeholder="填写其他退款原因">
                      </div>
                    </div>
              </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
            </button>
            <button id="refund_btn" type="button" class="btn btn-primary save">
              确定
            </button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal -->
    </div>
    <script type="text/javascript" src="../js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
      function find(form){
    	  var id =  document.getElementById("id").value;
          var nodeId =  document.getElementById("nodeId").value;
          var nodeName =  document.getElementById("nodeName").value;
          var payStatus =  document.getElementById("payStatus").value;
       
          if (id==""&&nodeId==""&&nodeName==""&&payStatus==""){
        	  alert("无搜索条件!")
    			return false;
          }
  	   	  return true;
      }
      
      $(function(){
  		  $("#orders").addClass('active'); 
          $('.refund').click(function(){
              orderId = $(this).attr('data-id');
              trade = $(this).attr('data-trade');
              userId = $(this).attr('data-user');
              totalPrice = $(this).attr('data-total');
              payPrice = $(this).attr('data-pay');

              $("#refund_order_id").html(orderId);
              $("#refund_trade").html(trade);
              $("#refund_user_id").html(userId);
              $("#refund_total").html(totalPrice/100+'元');
              $("#refund_pay").html(payPrice/100+'元');
              $("#refund_refund").html(payPrice/100+'元');

              $('#refundModal').modal('show');
          });

          $('#reasonSelect').change(function(e){
              if($(this).val()=='其他原因'){
                $('#otherDiv').show();
              }else{
                $('#otherDiv').hide();
              }
          });

		 
          $("#refund_btn").click(function(){
              if(confirm('确认要退款吗?')){
                 orderId = $("#refund_order_id").text();
                 reason = getReason();
                 note = "";
                 console.log("reason----"+reason);
                 if(reason == ''){
                     return;
                 }
                 handleRefund(orderId,reason,note);
              }
          });

		  function getReason(){
			  var reason = $("#reasonSelect").val();
			  console.log(reason);
			  return reason;
		  }
          function handleRefund(orderId,reason) {
               $.ajax({
                   url: ctx+'/PC/order/refund',
                   type: 'POST',
                   data: {orderId:orderId,reason:reason,note:note},
                   success: function(data){
                       if(data.code == 200){
                           alert(data.msg);
                       }else if(data.code == 201){
                           alert(data.msg);
                       }else{
                           alert('退款出现异常!');
                       }
                       $('#refundModal').modal('hide');
                       history.go(0);
                   },
                   error: function(xhr, type){
                       console.error(xhr); 
                   }
               });
          }

      })
      
      //需求时间
          $("#createdAt").datetimepicker({
              language: 'zh-CN',
              format: 'yyyy-mm-dd',
              startDate:new Date()
          });

      /*日期*/
      var start = $('#start')
      var end = $('#end')

      $.fn.datetimepicker.dates['zdy'] = {
          days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
          daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
          daysMin:  ["日", "一", "二", "三", "四", "五", "六"],
          months: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
          monthsShort: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
          today: "今天",
          format:"yyyy-mm-dd",
          titleFormat:"yyyy-mm-",
          weekStart:1,
          suffix: [],
          meridiem: ["上午", "下午"]
      };


      start.datetimepicker({
          format: 'yyyy-mm-dd',    /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
          language:  'zdy',
          weekStart: 1,
          todayBtn:  1,
          autoclose: 1,
          //startDate:sdate,
          minView:2,
          maxView:4
      });

      end.datetimepicker({
          format: 'yyyy-mm-dd',    /*此属性是显示顺序，还有显示顺序是mm-dd-yyyy*/
          language:  'zdy',
          weekStart: 1,
          todayBtn:  1,
          autoclose: 1,
          //startDate:sdate,
          minView:2,
          maxView:4
      });



      end.change(function(){
          if (start.val()!=""&&end.val()!="") {

          } else{
              alert("开始时间不能为空")
          }
      })


      $('#js-export').click(function(){
          if(window.confirm('您确定要导出吗？')){
              window.location.href="${ctx}/PC/exportExcel?findType=${findType}"
              return true;
          }else{
              //alert("取消");
              return false;
          }
      });
    </script>
  </body>
</html>