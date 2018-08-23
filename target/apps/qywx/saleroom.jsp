<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <title>销售统计</title>
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <link rel="stylesheet" href="${ctx}/qywx/css/base.css">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
    <script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.2/weui.min.js"></script>
    <style type="text/css">

        .dataTop {
            background: #fff;
            color: #999;
            text-align: center;
            border-bottom: 1px solid #e5e5e5;
            display: -webkit-box;
            display: -moz-box;
            display: -ms-flexbox;
            display: flex;
            -webkit-box-pack: justify;
            -moz-box-pack: justify;
            -ms-flex-pack: justify;
            -webkit-box-align: center;
            -moz-box-align: justify;
            -ms-flex-align: center;
            justify-content: space-between;
            align-content: center;
        }

        .dataChoice{
          width: 100%;
          box-sizing: border-box;
          -webkit-box-sizing: border-box;
          background: url(${ctx}/qywx/images/bottomArrow.png) 98% center no-repeat;
          border-right: 1px solid #e5e5e5;
        }

        .sltMore {
            height:40px;
            line-height: 40px;
            position: relative;
            padding-right: .4rem;
        }

        .sltMore ul {
            position: absolute;
            display: none;
            left: -1px;
            right: -1px;
            top: 41px;
            border: 1px solid #e5e5e5;
            border-top: none;
            padding: 0px .02rem;
            min-width: 100%;
            background: #fff;
            z-index: 10;
            max-height: 18em;
            overflow: auto;
        }

        .sltMore ul li {
            line-height: 1.6em;
            text-align: left;
            position: relative;
            padding: 0.1rem 5% 0.1rem 18%;
        }

        .sltMore ul li:hover {
            background: #f5f5f5;
            padding-right: 1em;
        }

        /*.sltMore ul li:last-child{ width:100%; height:2em;}
        .sltMore li:last-child button{width:100%; margin:0px auto; display: block; background:#64cd66; border:none; color:#fff;}*/
        .sltMore li.on img {
            display: block;
        }

        .sltMore li img {
            height: .8em;
            margin: -0.3em .1em .1em .1em;
            position: absolute;
            top: 50%;
            left: 5%;
            display: none;
        }

        .dataPeriod {
            text-align: center;
            background: #fff;
        }

        .dateStart, .dateTo, .dateEnd {
            color: #999;
            display: inline-block;
            width: 26%;
            margin-bottom: .66rem;
        }

        .dateStart input, .dateEnd input {
            width: 100%;
            padding-top: 20px;
            height: 1.2rem;
            line-height: 1.2rem;
            border-bottom: 1px solid #666;
            border-top: none;
            border-left: none;
            border-radius: 0px;
            border-right: none;
            text-align: center;
            position: relative;
        }

        .dataAlternative {
            margin-top: 20px;
            margin-bottom: 20px;
            text-align: center;
        }

        .dataAlternative button {
            display: inline-block;
            width: 16%;
            height: 16vw;
            line-height: 16vw;
            margin: 0px 8%;
            border-radius: 50%;
        }

        .dataAlternative button[data-value=day] {
            border: 1px solid #FFA220;
            background: url(${ctx}/qywx/images/day.png) center no-repeat;
            background-size: 50% auto;
        }

        .dataAlternative button[data-value=week] {
            border: 1px solid #FD6466;
            background: url(${ctx}/qywx/images/week.png) center no-repeat;
            background-size: 50% auto;
        }

        .dataAlternative button[data-value=month] {
            border: 1px solid #33C9F0;
            background: url(${ctx}/qywx/images/month.png) center no-repeat;
            background-size: 50% auto;
        }

        .dataAlternative button.on[data-value=day] {
            background: #FFA220 url(${ctx}/qywx/images/dayOn.png) center no-repeat;
            background-size: 50% auto;
        }

        .dataAlternative button.on[data-value=week] {
            background: #FD6466 url(${ctx}/qywx/images/weekOn.png) center no-repeat;
            background-size: 50% auto;
        }

        .dataAlternative button.on[data-value=month] {
            background: #33C9F0 url(${ctx}/qywx/images/monthOn.png) center no-repeat;
            background-size: 50% auto;
        }

        .dataTable {
            width: 100%;
            overflow: auto;
            position: relative;

        }

        .dataTable table {
            width: 98%;
            margin: 0px auto;
            line-height: 1.6em;
        }

        .dataTable th {
            text-align: center;
            border: 1px solid #999;
            border-collapse: collapse;
        }

        .dataTable td {
            border: 1px solid #999;
            text-align: center;
            border-collapse: collapse;
        }

        .dataTable td.red {
            color: #f00;
        }
    </style>
</head>
<body style="background-color: #fff;">
<div class="wrap">
    <div class="dataTop">
        <div class="dataChoice">
            <div class="sltMore" id="one">
                <p>全部点位</p>
                <ul class="dropSlt">
                    <li>全不选</li>
                    <c:forEach var="nodes" items="${nodeList }">
                   	 <li data-id="${nodes.id }" class="on">${nodes.name } <img src="${ctx}/qywx/images/hook.png"></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div class="dataPeriod">
        <div class="dateStart">
            <div class="weui-cell__bd">
                <input class="weui-input" id="dateStart" type="datetime" onfocus="this.blur();">
            </div>
        </div>
        <div class="dateTo">至</div>
        <div class="dateEnd">
            <div class="weui-cell__bd">
                <input class="weui-input" type="datetime" id="dateEnd" onfocus="this.blur();">
            </div>
        </div>
        <div class="dataAlternative">
            <button data-value="day"  class="on"></button>
            <button data-value="week"></button>
            <button data-value="month"></button>
        </div>
    </div>
    <div>
    	&nbsp;
    	&nbsp;
    	&nbsp;
    	销售总数：<span id="productSum"></span>
    	&nbsp;
    	&nbsp;
    	&nbsp;
    	销售总价：<span id="productPrice"></span>
    </div>
    <div class="dataTable">
        <table>
            <thead>
            <tr>
                <th>商品名</th>
                <th>数量</th>
                <th>销售额(元)</th>
            </tr>
            </thead>
            <tbody id="tableShow">
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    var oneOption=[];
    $(document).bind("click touchstart",".sltMore",function( e ) {
        if($(e.target).closest("#one").length == 0 &&$('#one').attr('show')=='true'){
            
            $('#one .dropSlt').hide();
            $('#one').attr('show',false)
            if($('#one .dropSlt').attr('change')=='true'){
                oneOption=[]
                //$(".lists").removeClass('on');
                // $('#one li').each(function(){
                //     if($(this).attr('class')=='on'){
                //         console.log($(this).attr('data-id'));
                //         var n = $(this).attr('data-id');
                //         $(".list-"+n).addClass('on');
                //     }
                // });

                $('#one .on').each(function(){
                    oneOption.push(parseInt($(this).attr('data-id')))
                });

                $('#one .dropSlt').attr('change',false);
            }

            ajaxGetSale();
        }
    });

    //点击出现下拉框
    $('.sltMore').bind('click',function(){
        $(this).attr('show',true);
        $(this).find('.dropSlt').show();
    });

    $(".dropSlt").bind('click',function(){
        $(this).attr('change',true)
    });

    //全选反选
    $('#one li:eq(0)').bind('click',function(){
        var ll=$(this).parents('ul').find('li').length-1;
        if($.trim($(this).text())=='全选'){
            $(this).text('全不选')
            //$(this).find('img').show();
            $(this).siblings('li:lt('+ll+')').addClass('on');
            /*$(this).parents('.sltMore').find('p').text('已选'+($(this).parents('ul').find('li').length-1)+'项')*/
            if($(this).parents('.sltMore').attr('id')=='one'){
                $(this).parents('.sltMore').find('p').text('已选全部点位类型')
            }else{
                $(this).parents('.sltMore').find('p').text('已选全部点位')
            }
        }else if($.trim($(this).text())!='全选'){
            $(this).text('全选');
            //$(this).find('img').hide();
            $(this).siblings('li:lt('+ll+')').removeClass('on');
            /*if($(this).parents('.sltMore').attr('id')=='one'){
             $(this).parents('.sltMore').find('p').text('已选0项')
             }else{
             $(this).parents('.sltMore').find('p').text('已选0项')
             }*/
            $(this).parents('.sltMore').find('p').text('已选0项')
        }
        /*$(this).find('img').toggle();
         $(this).siblings('li').toggleClass('on').find('img').toggle();*/
    })
    
    //除全选 选项外的  其他选项点击的控制
    $('#one li:gt(0)').bind('click',function(){
        if($(this).index()<$(this).parents('ul').find('li').length){
            $(this).toggleClass('on');
        }
        //var vle=parseInt($(this).parents('.sltMore').find('p').find('span').text())
    })
    var initTextOne=$('.sltMore').eq(0).find('p').text();
    var initTextTwo=$('.sltMore').eq(1).find('p').text();
    
    //已选多少项
    $('#one li:gt(0)').bind('click',function(){
        var m=0;
        $(this).parents('.dropSlt').find('li:gt(0)').each(function(i,e){
            if(i<$(this).parents('.dropSlt').find('li:gt(0)').length){
                if($(this).attr('class').indexOf('on')!=-1){
                    m++;
                    $(this).parents('.sltMore').find('p').text('已选'+m+'项')
                }
                if(m==0){
                    if($(this).parents('.sltMore').attr('id')=='one'){
                        $(this).parents('.sltMore').find('p').text(initTextOne)
                    }else{
                        $(this).parents('.sltMore').find('p').text(initTextTwo)
                    }
                }
            }
        })
    })
</script>
<script type="text/javascript">
  
    var clickType = 1 ;//时间选择为按钮快速定位 2 时间选择为日历勾选
    //格式化日期：yyyy-MM-dd
    function formatDate(date) {
        var myyear = date.getFullYear();
        var mymonth = date.getMonth()+1;
        var myweekday = date.getDate();

        if(mymonth < 10){
            mymonth = "0" + mymonth;
        }
        if(myweekday < 10){
            myweekday = "0" + myweekday;
        }
        return (myyear+"/"+mymonth + "/" + myweekday);
    }
    var now=new Date();
    console.log('now time '+now)
    var nowDay    = now.getDate(); //当前日
    var nowMonth  = now.getMonth()+1; //当前月
    var nowYear   = now.getFullYear(); //当前年
    var dateStart = formatDate(now);
    var dateEnd   = formatDate(now);
    console.log(nowYear+'this years')
    console.log(nowMonth+'this month')
    
    //$('#dateEnd,#dateStart').val(formatDate(now));
    
    //当年当月当日
    $('.dataAlternative button').click(function(){
        $(this).addClass('on');
        $(this).siblings('button').removeClass('on');
        var value=$(this).attr('data-value');
        if(value=='day'){
            $('#dateEnd,#dateStart').val(formatDate(now));
            dateEnd   = formatDate(now);
            dateStart = formatDate(now);
            ajaxGetSale();
        }else if(value=='week'){
            clickType = 1;
            var w=weekSet();
            var wStart=w[0];
            var wEnd=w[1];
            $('#dateStart').val(wStart);
            $('#dateEnd').val(wEnd);
            dateStart = wStart;
            dateEnd   = wEnd;
            ajaxGetSale();
        }else if(value=='month'){
            clickType = 3;
            var m=monthSet();
            var mStart=m[0];
            var mEnd=m[1];
            $('#dateStart').val(mStart);
            $('#dateEnd').val(mEnd);
            dateStart = mStart;
            dateEnd   = mEnd;
            ajaxGetSale();
        }else{
            $('#dateEnd,#dateStart').val(formatDate(now));
        }
    });
    function weekSet() {
        //获取本周
        var getDays = function ()
        {
            var now = new Date;
            var day = now.getDay ();
            var week = "1234567";
            var first = 0 - week.indexOf (day);
            var f = new Date;
            f.setDate (f.getDate () + first);
            //获取本周结束日期
            var last = 6 - week.indexOf (day);
            var l = new Date;
            l.setDate (l.getDate () + last);
            return [
                f, l
            ];
        }
        //日期格式化
        var week=getDays();
        var weekStart=week[0];
        var weekEnd=week[1];
        var weekSetStart=formatDate(weekStart);
        var weekSetEnd=formatDate(weekEnd);
        return [weekSetStart,weekSetEnd]
    }
    function monthSet(){
        var now=new Date();
        var nowDay = now.getDate(); //当前日
        var nowMonth = now.getMonth(); //当前月
        var nowYear = now.getYear(); //当前年
        nowYear += (nowYear < 2000) ? 1900 : 0; //
        //获得某月的天数
        function getMonthDays(myMonth){
            var monthStartDate = new Date(nowYear, myMonth, 1);
            var monthEndDate = new Date(nowYear, myMonth + 1, 1);
            var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
            return days;
        }
        //获得本月的开始日期
        function getMonthStartDate(){
            var monthStartDate = new Date(nowYear, nowMonth, 1);
            return formatDate(monthStartDate);
        }

        //获得本月的结束日期
        function getMonthEndDate(){
            var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowMonth));
            return formatDate(monthEndDate);
        }
        return [getMonthStartDate(),getMonthEndDate()]
    }
    function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式
        console.log('DateDiff')
        var  aDate,  oDate1,  oDate2,  iDays
        aDate  =  sDate1.split("/")
        oDate1  =  new  Date(aDate[1]  +  '/'  +  aDate[2]  +  '/'  +  aDate[0])    //转换为12-18-2006格式
        aDate  =  sDate2.split("/")
        oDate2  =  new  Date(aDate[1]  +  '/'  +  aDate[2]  +  '/'  +  aDate[0])
        iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
        return  iDays + 1
    }
    function checkEndTime(begin,end){
        console.log('checkEndTime')
        var start=new Date(begin.replace("/", "/").replace("/", "/"));
        var end=new Date(end.replace("/", "/").replace("/", "/"));
        if(end<start){
            return false;
        }
        return true;
    }

    $('#dateEnd').bind('click',function(){
        $('.dataAlternative button').removeClass("on")
        clickType = 2;
        console.log(nowYear+'=====')
        weui.datePicker({
            start: 1990,
            end: 2100,
            defaultValue: [nowYear, nowMonth, nowDay],
            onChange: function(result){
                //
            },
            onConfirm: function(result){
                var year=result[0]
                var month=result[1]<10?'0'+result[1]:result[1]
                var day=result[2]<10?'0'+result[2]:result[2]
                $('#dateEnd').val(year+'/'+month+'/'+day);
                dateEnd   = $('#dateEnd').val();
                ajaxGetSale();
                //点击日历确定按钮的时候
                /*if(!!dateStart||!!dateEnd){
                 //没填数值的时候
                 }else if(!checkEndTime(dateStart,dateEnd)){
                 alert('结束时间必须晚于开始时间')
                 }else if(DateDiff(dateStart,dateEnd)>90){
                 alert("最多可查询90天内的数据")
                 }else{
                 //取值加载数据？
                 }*/
            },
            id: 'dateEnd'
        });
    })
    $('#dateStart').bind('click',function(){
        $('.dataAlternative button').removeClass("on")
        clickType = 2;
        weui.datePicker({
            start: 1990,
            end: 2100,
            defaultValue: [nowYear, nowMonth, nowDay],
            onChange: function(result){
                //日期选择中发生变化的时候
            },
            onConfirm: function(result){
                var year=result[0]
                var month=result[1]<10?'0'+result[1]:result[1]
                var day=result[2]<10?'0'+result[2]:result[2]
                $('#dateStart').val(year+'/'+month+'/'+day);
                console.log("日期选择中发生变化的时候"+dateStart);
                dateStart   = $('#dateStart').val();
                console.log("日期变化后"+dateStart);
                ajaxGetSale();
                /*//点击日历确定按钮的时候
                 if(!!dateStart||!!dateEnd){
                 //没填数值的时候
                 }else if(!checkEndTime(dateStart,dateEnd)){
                 alert('结束时间必须晚于开始时间')
                 }else if(DateDiff(dateStart,dateEnd)>90){
                 alert("最多可查询90天内的数据")
                 }else{
                 //取值加载数据？
                 }*/
            },
            id: 'dateStart'
        });
    });
</script>
<script type="text/javascript">
  
    // $('.wrap').show();
    console.log('dateStart'+dateStart);
    console.log('dateEnd'+dateEnd);
    $(function(){
        /* var lastDay = weekSet();
        dateEnd    = lastDay[1];
        dateStart  = lastDay[0];
        $('#dateStart').val(lastDay[0]);
        $('#dateEnd').val(lastDay[1]); 
        */
        
        dateEnd   = formatDate(now);
        dateStart = formatDate(now);
        
        $('#dateStart').val(dateStart);
        $('#dateEnd').val(dateEnd);
        


        var ll=$(this).parents('ul').find('li').length-1;
        $('#one li.on').each(function(i,e){
            var nodeId = $(this).attr('data-id');
            oneOption.push(nodeId);
        });
        
        ajaxGetSale();
    });
   
    function twoLevelMount(){
        var mountOn=0;
        console.log('mountOn',mountOn);
        $('#two').find('li:gt(0)').each(function(i,e){
            console.log('this1',(this))
            if(i<$(this).parents('.dropSlt').find('li:gt(0)').length){
                console.log('this2',$(this))
                if($(this).attr('class').indexOf('on')!=-1){
                    mountOn++;
                    if(mountOn<$(this).parents('.dropSlt').find('li:gt(0)').length){
                        $(this).parents('.sltMore').find('p').text('已选'+mountOn+'项')
                    }else if(mountOn==$(this).parents('.dropSlt').find('li:gt(0)').length){
                        if($(this).parents('.sltMore').attr('id')=='one'){
                            $(this).parents('.sltMore').find('p').text('已选全部点位类型')
                        }else if($(this).parents('.sltMore').attr('id')=='two'){
                            $(this).parents('.sltMore').find('p').text('已选全部点位')
                        }else if($(this).parents('.sltMore').attr('id')=='three'){
                            $(this).parents('.sltMore').find('p').text('已选全部品类')
                        }else if($(this).parents('.sltMore').attr('id')=='four'){
                            $(this).parents('.sltMore').find('p').text('已选全部种类')
                        }
                    }

                }
                if(mountOn==0){
                    if($(this).parents('.sltMore').attr('id')=='one'){
                        $(this).parents('.sltMore').find('p').text(initTextOne)
                    }else{
                        $(this).parents('.sltMore').find('p').text(initTextTwo)
                    }
                }
            }
        })
    }

    function ajaxGetSale(){
       console.log('发送请求'+oneOption);
       var nodeId = JSON.stringify(oneOption);
        if(oneOption.length <=0){
            alert("至少选择一个点位");
            return false;
        };

        var loading;
        $.ajax({
            url: "${ctx}/data/ajax/cons_trend",
            data: {
                begin  :dateStart,
                end   :dateEnd,
                nodeIds :oneOption,
            },
            type: "POST",
            dataType: 'JSON',
            timeout: 30000,
            context: $('body'),
            beforeSend:function(){
  				loading = weui.loading('数据加载中');
            },
            complete:function(){
            	loading.hide();
            },
            success: function (data) {
                $('#tableShow').empty();
                console.log(data.data);
                	  var sum = 0;
                	  var price = 0;
                       $.each(data.data,function(i,n){
					       var $tr = $("<tr>"+
					       "<td>"+n.name+"</td>"+
					       "<td>"+n.amount+"</td>"+
					       "<td>"+n.sales/100+"</td>"+
					       "</tr>");
					       var $table = $('#tableShow');
					       $table.append($tr);
					       sum = sum+n.amount;
					       price = price+n.sales;
					   });
					   console.log("sum:"+sum);
					   $("#productSum").text(sum);
					   console.log("price"+price);
					   $("#productPrice").text(price/100);
                       
            },
            error:function(jqXHR, textStatus, errorThrown){
  
            }
        });
        
    }
</script>
<script type="text/javascript">  
	var ctx= '${ctx}';
</script> 
</body>
</html>