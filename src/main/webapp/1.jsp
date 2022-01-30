<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>标题</title>
</head>
<body>
$.ajax({
    url:"",
    data:{},
    dataType:"json",
    type:"post",
    success:function (data){

    }
})

$(".time").datetimepicker({
minView: "month",
language:  'zh-CN',
format: 'yyyy-mm-dd',
autoclose: true,
todayBtn: true,
pickerPosition: "top-left"
});

((User)req.getSession().getAttribute("user")).getName()
DateTimeUtil.getSysTime()


</body>
</html>
