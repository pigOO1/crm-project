<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=base%>"/>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">
	$(function (){
		$(".dateInput").datetimepicker({
			language:'zh-CN',//语言
			format:'yyyy-mm-dd',//格式
			minView:'month',//最小视图
			initialDate:new Date(),//初始化显示的日期
			autoclose:true,//日历是否自动关闭
			todayBtn:true,//是否显示今天按钮
			clearBtn:true//是否显示清空按钮
		})
		//给市场活动搜索符号添加单击事件
		$("#create-searchActivityBtn").click(function (){
			//显示模态窗口
			$("#searchMarketActivityModel").modal("show")
			//给搜索域添加键盘弹起事件
			$("#searchActivityTxt").keyup(function () {
				//获取参数
				var activityName = this.value
				//发送请求
				$.ajax({
					url:'workbench/transaction/searchActivity.do',
					data:{
						name:activityName
					},
					dataType:'json',
					type:'post',
					success:function (data) {
						var htmlStr = ""
						//遍历添加市场活动
						$.each(data, function (index, obj) {
							htmlStr += "<tr>"
							htmlStr += "<td><input type=\"radio\" name=\"activity\" id='"+obj.id+"' value=' "+obj.name+"'/></td>"
							htmlStr += "<td>"+obj.name+"</td>"
							htmlStr += "<td>"+obj.startDate+"</td>"
							htmlStr += "<td>"+obj.endDate+"</td>"
							htmlStr += "<td>"+obj.owner+"</td>"
							htmlStr += "</tr>"
						})
						$("#activityTBody").html(htmlStr)
					}
				})
			})
		})
		//给联系人搜索符号添加单击事件
		$("#create-searchContactsBtn").click(function (){
			//显示模态窗口
			$("#searchContactsModel").modal("show")
			//键盘弹起事件
			$("#searchContactsTxt").keyup(function () {
				//获取参数
				var contactsName = this.value

				//发送请求
				$.ajax({
					url: 'workbench/transaction/searchContacts.do',
					data: {
						name: contactsName
					},
					dataType: 'json',
					type: 'post',
					success: function (data) {
						var htmlStr = ""
						//遍历添加市场活动
						$.each(data, function (index, obj) {
							htmlStr += "<tr>"
							htmlStr += "<td><input type=\"radio\" name=\"contacts\" id='" + obj.id + "' value='"+obj.fullname+"'/></td>"
							htmlStr += "<td>" + obj.fullname + "</td>"
							htmlStr += "<td>" + obj.email + "</td>"
							htmlStr += "<td>" + obj.mphone + "</td>"
							htmlStr += "</tr>"
						})
						$("#contactsTBody").html(htmlStr)
					}
				})
			})
		})
		//给这些内容添加单击事件
		$("#contactsTBody").on("click", "input[type='radio']", function (){
			//单击就给文本域赋值
			$("#create-contactsName").val(this.value)
			$("#create-contactsId").val(this.id)

			//关闭模态窗口
			$("#searchContactsModel").modal("hide")
		})
		$("#activityTBody").on("click", "input[type='radio']", function (){
			//单击就给文本域赋值
			$("#create-activityName").val(this.value)
			$("#create-activityId").val(this.id)
			//关闭模态窗口
			$("#searchMarketActivityModel").modal("hide")

		})
		//给阶段下拉列表添加change事件
		$("#create-transactionStage").change(function () {
			//收集参数
			// var stageValue = $(this).find("option:selected").text()
			var stageValue = $("#create-transactionStage option:selected").text()
			//验证
			if(stageValue == ""){
				$("#create-possibility").val("")
				return
			}

			//发送请求
			$.ajax({
				url:'workbench/transaction/getPossibilityByStage.do',
				data:{
					stage:stageValue
				},
				dataType:'json',
				type:'post',
				success:function (data) {
					$("#create-possibility").val(data)
				}
			})
		})
		//给客户名称输入框添加插件
		$("#create-accountName").typeahead({
			source:function (jquery, process) {
				$.ajax({
					url:'workbench/transaction/queryCustomerNameByName.do',
					data:{
						customerName:jquery
					},
					dataType:'json',
					type:'post',
					success:function (data){
						process(data)
					}
				})
			}
		})
		//给保存按钮添加单击事件
		$("#saveCreateTranBtn").click(function () {
			//收集参数
			var owner = $("#create-transactionOwner").val()
			var money = $.trim($("#create-amountOfMoney").val())
			var name = $.trim($("#create-transactionName").val())
			var expectedDate = $("#create-expectedClosingDate").val()
			var customerName = $("#create-accountName").val()
			var stage = $("#create-transactionStage").val()
			var type = $("#create-transactionType").val()
			var source = $("#create-clueSource").val()
			var activityId = $("#create-activityId").val()
			var contactsId = $("#create-contactsId").val()
			var description = $.trim($("#create-describe").val())
			var contactSummary = $.trim($("#create-contactSummary").val())
			var nextContactTime = $.trim($("#create-nextContactTime").val())
			//参数验证
			if(owner == "" | name == "" | expectedDate == "" | customerName == "" | stage == ""){
				alert("必填项还未填写完整")
				return
			}
			var regex = /^(([1-9]\d*)|0)$/
			if(money && !regex.test(money)){
				alert("金额必须为正整数")
				return
			}
			//发送请求
			$.ajax({
				url:'workbench/transaction/saveCreateTran.do',
				data:{
					owner:owner,
					money:money,
					name:name,
					expectedDate:expectedDate,
					customerName:customerName,
					stage:stage,
					type:type,
					source:source,
					activityId:activityId,
					contactsId:contactsId,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime
				},
				dataType:'json',
				type:'post',
				success:function (data){
					if(data.code == "1"){
						//成功
						window.location.href='workbench/transaction/index.do'
					}else {
						//失败
						alert(data.message)
					}
				}
			})
		})
	})
</script>
</head>
<body>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="searchMarketActivityModel" role="dialog">
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
						    <input type="text" class="form-control" id="searchActivityTxt" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
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
						<tbody id="activityTBody" class="searchTBody">
<%--							<tr>--%>
<%--								<td><input type="radio" name="activity"/></td>--%>
<%--								<td>发传单</td>--%>
<%--								<td>2020-10-10</td>--%>
<%--								<td>2020-10-20</td>--%>
<%--								<td>zhangsan</td>--%>
<%--							</tr>--%>
<%--							<tr>--%>
<%--								<td><input type="radio" name="activity"/></td>--%>
<%--								<td>发传单</td>--%>
<%--								<td>2020-10-10</td>--%>
<%--								<td>2020-10-20</td>--%>
<%--								<td>zhangsan</td>--%>
<%--							</tr>--%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="searchContactsModel" role="dialog">
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
						    <input type="text" class="form-control" id="searchContactsTxt" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
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
						<tbody id="contactsTBody" class="searchTBody">
<%--							<tr>--%>
<%--								<td><input type="radio" name="activity"/></td>--%>
<%--								<td>李四</td>--%>
<%--								<td>lisi@bjpowernode.com</td>--%>
<%--								<td>12345678901</td>--%>
<%--							</tr>--%>
<%--							<tr>--%>
<%--								<td><input type="radio" name="activity"/></td>--%>
<%--								<td>李四</td>--%>
<%--								<td>lisi@bjpowernode.com</td>--%>
<%--								<td>12345678901</td>--%>
<%--							</tr>--%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveCreateTranBtn">保存</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner">
				  <c:forEach items="${userList}" var="user">
					  <option value="${user.id}">${user.name}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-amountOfMoney">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-transactionName">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control dateInput" id="create-expectedClosingDate" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-accountName" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-transactionStage">
			  	<option></option>
				  <c:forEach items="${stageList}" var="stage">
					  <option value="${stage.id}">${stage.value}</option>
				  </c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType">
				  <option></option>
					<c:forEach items="${transactionTypeList}" var="transaction">
						<option value="${transaction.id}">${transaction.value}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource">
				  <option></option>
					<c:forEach items="${sourceList}" var="source">
						<option value="${source.id}">${source.value}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-activityName" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="create-searchActivityBtn"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="create-activityId">
				<input type="text" class="form-control" id="create-activityName" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" id="create-searchContactsBtn"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="create-contactsId">
				<input type="text" class="form-control" id="create-contactsName" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control dateInput" id="create-nextContactTime" readonly>
			</div>
		</div>
		
	</form>
</body>
</html>