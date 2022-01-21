<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	/*
        创建分页查询

        需要后台返回数据
        {total：数据总数，ActivityList：表单List}

        返回采用vo类
        参数total
        参数List<T>
     */
	function pageList(pageNo,pageSize){

		$("#qx").prop("checked",false);

		$("#search-name").val($("#hidden-name").val().trim())
		$("#search-owner").val($("#hidden-owner").val().trim())
		$("#search-startDate").val($("#hidden-startDate").val().trim())
		$("#search-endDate").val($("#hidden-endDate").val().trim())

		$.ajax({
			url:"pageList.do",
			data:{
				"pageSize":pageSize,
				"pageNo":pageNo,
				"owner":$.trim($("#search-owner").val()),
				"name":$.trim($("#search-name").val()),
				"startDate":$.trim($("#search-startDate").val()),
				"endDate":$.trim($("#search-endDate").val())
			},
			type:"get",
			dataType:"json",
			success:function (data){

				var html="";

				$.each(data.list,function (i,n){

					html+='<tr class="active">'
					html+='<td><input type="checkbox" name="xz" value="'+n.id+'"/></td>'
					html+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'detail.html\';">'+n.name+'</a></td>'
					html+='<td>'+n.owner+'</td>'
					html+='<td>'+n.startDate+'</td>'
					html+='<td>'+n.endDate+'</td>'
					html+='</tr>'

				})

				$("#body").html(html);

				var totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});
			}
		})
	}



	$(function(){

		pageList(1,2)
		// 创建表单的ajax
		$("#addBtn").click(function (){

			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});

				$.ajax({
					url:"userlist.do",
					dataType: "json",
					type: "get",
					success:function (data){
					var html="<option></option>"

						$.each(data,function(i,n){
							var name=n.name;
							if (	"${user.name}" == name ){
								html+="<option value="+n.id+" selected>"+name+"</option>"
							}else{
								html+="<option value="+n.id+">"+name+"</option>"
							}

						})


						$("#create-owner").html(html);
					}
				})



				$("#createActivityModal").modal("show");



			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		})

		/*
		* 表单提交的ajax
		* */
		$("#subtn").click(function (){

			$.ajax({
				url:"addActivity.do",
				data:{
					"owner":$.trim($("#create-owner").val()),
					"name":$.trim($("#create-name").val()),
					"startDate":$.trim($("#create-startDate").val()),
					"endDate":$.trim($("#create-endDate").val()),
					"cost":$.trim($("#create-cost").val()),
					"description":$.trim($("#create-description").val()),
					"createTime":$.trim($("#create-createTime").val()),
				},
				type:"post",
				dataType:"json",
				success:function (data){


					if (data.success){
						alert("添加成功");
					}else{
						alert("添加失败");
					}

				},
				error:function (e){
					alert("系统错误："+e);
				}
			})
			pageList(1,2)
		})

		//分页查询按钮
		$("#search-btn").click(function (){


			$("#hidden-name").val($("#search-name").val().trim())
			$("#hidden-owner").val($("#search-owner").val().trim())
			$("#hidden-startDate").val($("#search-startDate").val().trim())
			$("#hidden-endDate").val($("#search-endDate").val().trim())

			pageList(1,2);
		})

		//删除数据按钮
		$("#deleteBtn").click(function (){

			if(confirm("你确定删除吗？")){

				var $qx=$("input[name=xz]:checked");

				if ($qx.length == 0){
					alert("请选择要删除的数据")
				}else{
					//alert($qx.val());

					var msg="";

					for (var i=0;i<$qx.length;i++){
						msg+="id="+$qx[i].value;
						if (i<$qx.length-1){
							msg+="&";
						}
					}
					$.ajax({
						url:"deleteActivity.do",
						data:msg,
						dataType:"json",
						type:"post",
						success:function (data){

							if (data.success){
								alert(data)
								alert("删除成功");
								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else{
								alert("删除失败");
							}

						}
					})

				}
			}


		})

		//修改数据按钮
		$("#editBtn").click(function (){

			var $xz=$("input[name=xz]:checked");
			if ($xz.length == 0){
				alert("请选择要修改的数据")
			}else if ($xz.length > 1){
				alert("只能选择单条数据进行修改")
			}else{
				//日期插件
				$(".time").datetimepicker({
					minView: "month",
					language:  'zh-CN',
					format: 'yyyy-mm-dd',
					autoclose: true,
					todayBtn: true,
					pickerPosition: "bottom-left"
				});

				//owner所有者遍历
				$.ajax({
					url:"userlist.do",
					dataType: "json",
					type: "post",
					success:function (data){
						var html="<option></option>"
						$.each(data,function(i,n){
							html+="<option value="+n.id+" selected>"+n.name+"</option>"
						})
						$("#edit-owner").html(html);
					}
				})

				//根据id查询要修改表单的数据
				$.ajax({
					url:"selectActivity.do",
					data:"id="+$.trim($xz[0].value),
					dataType:"json",
					type:"post",
					success:function (data){
						$("#edit-owner").val(data.owner);
						$("#edit-name").val(data.name);
						$("#edit-startDate").val(data.startDate);
						$("#edit-endDate").val(data.endDate);
						$("#edit-cost").val(data.cost);
						$("#edit-describe").val(data.description);

					}
				})

				$("#editActivityModal").modal("show");


				//进行表单修改操作
				$("#updateBtn").click(function (){

					var $xz=$("input[name=xz]:checked");
					//修改表单
					$.ajax({
						url:"updateActivity.do",
						data:{
							"id":$.trim($xz[0].value),
							"owner":$.trim($("#edit-owner").val()),
							"name":$.trim($("#edit-name").val()),
							"startDate":$.trim($("#edit-startDate").val()),
							"endDate":$.trim($("#edit-endDate").val()),
							"cost":$.trim($("#edit-cost").val()),
							"describe":$.trim($("#edit-describe").val())

						},
						dataType:"json",
						type:"post",
						success:function (data){
							if(data.success){
								alert("修改成功")

								$("#editActivityModal").modal("hide");

								pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
										,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else{
								alert("修改失败")
							}

						}
					})

				})

			}

		})



		//全选框选择
		$("#qx").on("click",function (){
			$("input[name=xz]").prop("checked",this.checked);
		})
		$("#body").on("click",$("input[name=xz]"),function (){
			$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
		})


	});


</script>
</head>
<body>

<input type="hidden" id="hidden-owner">
<input type="hidden" id="hidden-name">
<input type="hidden" id="hidden-startDate">
<input type="hidden" id="hidden-endDate">

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">


								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime " class="col-sm-2  control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2  control-label" >结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="subtn" class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startDate">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endDate">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon" >名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon" >所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="search-btn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"  id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="body">



					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">

			<div id="activityPage">

			</div>

			</div>
			
		</div>
		
	</div>
</body>
</html>