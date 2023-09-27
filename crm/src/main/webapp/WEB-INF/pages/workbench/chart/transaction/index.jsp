<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=base%>"/>
    <meta charset="UTF-8">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/echars/echarts.min.js"></script>
<script type="text/javascript">
    $(function () {
        //页面加载完成发送请求
        $.ajax({
            url:'workbench/chart/transaction/queryCountOfTranGroupByStage.do',
            dataType:'json',
            type:'post',
            success:function (data) {
                //调用echarts
                //基于创建好的dom对象，初始化echart实例
                var myChart = echarts.init(document.getElementById("main"))
                var option = {
                    title: {
                        text: '交易统计图表',
                        subtext: '交易表中各个阶段的数量'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c}'
                    },
                    toolbox: {
                        feature: {
                            dataView: { readOnly: false },
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                    series: [
                        {
                            name: '数据量',
                            type: 'funnel',
                            left: '10%',
                            top: 60,
                            bottom: 60,
                            width: '80%',
                            min: 0,
                            max: 100,
                            minSize: '0%',
                            maxSize: '100%',
                            sort: 'descending',
                            gap: 2,
                            label: {
                                formatter: '{b}'
                            },
                            labelLine: {
                                show:true
                            },
                            itemStyle: {
                                opacity:0.7
                            },
                            emphasis: {
                                label: {
                                    position:'inside',
                                    formatter:'{b}:{c}'
                                }
                            },
                            data: data
                        }
                    ]
                }
                myChart.setOption(option)
            }
        })
    })
</script>
</head>
<body>
<div id="main" style="width: 600px; height: 400px"></div>
</body>
</html>