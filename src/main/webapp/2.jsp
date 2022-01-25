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
    <meta charset="UTF-8">

    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript">

        $(function(){

               $.ajax({
                   url:"userlist.do",
                   dataType: "json",
                   type: "get",
                   success:function (data){
                       var html="<option></option>"
                       $.each(data,function(i,n){

                               html+="<option value="+n.id+" selected>"+n.name+"</option>"

                       })
                        var id='${user.name}';

                        alert(id)
                       $("#create-owner").html(html);
                       $("#create-owner").val(id);

                   }
               })


        })

    </script>
</head>
<body>
<select id="create-owner">

</select>



</body>
</html>
