<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	Map<String,String> pMap=(Map<String, String>) application.getAttribute("pMap");

	Set<String> set=pMap.keySet();


%>
<!DOCTYPE HTML>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>

	<script type="text/javascript">

		$(function (){

			var json={

				<%
					for(String key:set){

						String value=pMap.get(key);
				%>

					"<%=key%>" : "<%=value%>",

				<%
					}

				%>

			};
			//可能性显示
			$("#create-transactionStage").change(function (){
				$("#create-possibility").val(json[$("#create-transactionStage").val()])
			})
			//客户名称自动查询
			$("#create-customerName").typeahead({
				source: function (query, process) {
					$.post(
							"workbench/transaction/getCustomerName.do",
							{ "name" : query },
							function (data) {
								//alert(data);
								process(data);
							},
							"json"
					);
				},
				delay: 500
			});

			//日期组件
			$(".time1").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			//日期组件
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "top-left"
			});

			//市场活动源
			$("#activitySrcModel").click(function (){
				ActivityLikeList()
			})
			$("#activityBtn").click(function (){
				$xz=$("input[name=xz]:checked");
				var id=$xz.val()
				var name=$("#"+id).html();
				$("#create-activitySrc").val(name);
				$("#create-activitySrcId").val(id);
			})
			$("#activityLikeName").keydown(function (event){
				if (event.keyCode == 13){
					ActivityLikeList()
				}
				return false;
			})


			/*<--  ================================================      -->*/

			//联系人名称
			$("#contactsModel").click(function (){
				ContactsLikeList()
			})
			$("#contactsBtn").click(function (){
				$xz=$("input[name=zx]:checked");
				var id=$xz.val()
				var name=$("#"+id).html();
				$("#create-contactsName").val(name);
				$("#create-contactsNameId").val(id);
			})
			$("#ContactsLikeName").keydown(function (event){
				if (event.keyCode == 13){
					ContactsLikeList()
				}
				return false;
			})

			//提交表单
			$("#add").click(function (){

				$("#tranForm").submit();

			})


		})
		//关联查找市场活动模态窗口列表遍历
		function ActivityLikeList(){
			$.ajax({
				url:"workbench/transaction/ActivityLikeList.do",
				data:{
					"name":$.trim($("#ActivityLikeName").val())
				},
				dataType:"json",
				type:"post",
				success:function (data){

					var html="";
					$.each(data,function (i,n){
						html+='<tr>'
						html+='<td><input type="radio" name="xz" value="'+n.id+'"/></td>'
						html+='<td id="'+n.id+'">'+n.name+'</td>'
						html+='<td>'+n.startDate+'</td>'
						html+='<td>'+n.endDate+'</td>'
						html+='<td>'+n.owner+'</td>'
						html+='</tr>'

					})
					$("#activity-body").html(html)
					$("#LikeName").val("")
					$("input[name=xz]").prop("checked",false);
					$("#findMarketActivity").modal("show");

				}
			})
		}
		//关联查找联系人模态窗口列表遍历
		function ContactsLikeList(){
			$.ajax({
				url:"workbench/transaction/ContactsLikeList.do",
				data:{
					"name":$.trim($("#ContactsLikeName").val())
				},
				dataType:"json",
				type:"post",
				success:function (data){

					var html="";
					$.each(data,function (i,n){
						html+='<tr>'
						html+='<td><input type="radio" name="zx" value="'+n.id+'"/></td>'
						html+='<td id="'+n.id+'">'+n.fullname+'</td>'
						html+='<td>'+n.email+'</td>'
						html+='<td>'+n.mphone+'</td>'
						html+='</tr>'

					})
					$("#contacts-body").html(html)
					$("#ContactsLikeName").val("")
					$("input[name=zx]").prop("checked",false);
					$("#findContacts").modal("show");

				}
			})
		}

	</script>

</head>
<body>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询" id="ActivityLikeName">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="activity-body">

						</tbody>
					</table>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" id="activityBtn">确定</button>
					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询" id="ContactsLikeName">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="contacts-body">

						</tbody>
					</table>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" id="contactsBtn">确定</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="add">保存</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;" id="tranForm" method="post" action="workbench/transaction/insertTran.do">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner" name="owner">
				  <option></option>
					<c:forEach items="${Auser}" var="u">
						<option value="${u.id}" ${u.id eq user.id ? "selected":""}>${u.name}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-amountOfMoney" name="money">
			</div>
		</div>

		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-transactionName" name="transactionName">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time1" id="create-expectedClosingDate" name="expectedClosingDate" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-customerName" name="customerName" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-transactionStage" name="transactionStage">
				  <option></option>
				  <c:forEach items="${stage}" var="s">
					  <option value="${s.value}" id="s.id">${s.text}</option>
				  </c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType" name="transactionType">
				  <option></option>
				  <option>已有业务</option>
				  <option>新业务</option>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource" name="clueSource">
					<option></option>
					<c:forEach items="${source}" var="o">
						<option value="${o.id}">${o.text}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);"><span class="glyphicon glyphicon-search" id="activitySrcModel"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-activitySrc">
				<input type="hidden" id="create-activitySrcId" name="activitySrcId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" ><span class="glyphicon glyphicon-search" id="contactsModel"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-contactsName">
				<input type="hidden" id="create-contactsNameId" name="contactsNameId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe" name="describe"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary" name="contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time" id="create-nextContactTime" name="nextContactTime" readonly>
			</div>
		</div>
		
	</form>
</body>
</html>