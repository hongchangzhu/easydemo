<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/js/easyui/demo.css">
<script type="text/javascript" src="<%=contextPath%>/js/boot.js"></script>
</head>
<body>
	<h2>Client Side Pagination in DataGrid</h2>
	<p>This sample shows how to implement client side pagination in
		DataGrid.</p>
	<div style="margin: 20px 0;"></div>
	<input class="easyui-validatebox easyui-datebox" data-options="sharedCalendar:'#cc',required:true">
	<div id="cc" class="easyui-calendar"></div>
	
	<form id="" action="">

	<table id="dg" class="easyui-datagrid" title="Basic DataGrid"
		style="width: 700px; height: 350px" method="post" idField="FAppID"
		rownumbers="true" pagination="true" singleSelect="true"
		showFooter="false" collapsible="false" toolbar="#tb"
		url="<%=contextPath%>/easyui/query">
		<thead>
			<tr>
				<th data-options="field:'FAppID',width:80">应用ID</th>
				<th data-options="field:'FAppName',width:150">应用名称</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 3px">
		<span>应用ID:</span> <input id="FAppID" style="line-height: 26px; border: 1px solid #ccc"> 
		<span>应用名称:</span> <input id="FAppName" style="line-height: 26px; border: 1px solid #ccc">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">搜索</a>
	</div>
	
	</form>




</body>
</html>
<script type="text/javascript">

var dg = $('#dg');
function doSearch(){
	var searchDiv = $('#tb');
	var divEle = searchDiv.find(':input[disabled!="disabled"][id]');
	var cond = {};
	var opts = dg.datagrid('options');
	var pager = dg.datagrid('getPager');
	var queryParams = opts.queryParams;
	
	divEle.each(function(){
		var $this = $(this);
		var value = $this.val();
		if(value != null){
			cond[$this.attr('id')] = value;
			queryParams[$this.attr('id')] = value;
		}
	});
	
	 
	alert(JSON.stringify(queryParams));
	//cond.pageSize = opts.pageSize;//每页数据数
	//cond.pageNumber = opts.pageNumber;//页数
	var objToStr=JSON.stringify(cond)
	
	dg.datagrid('load',{
		submitData:objToStr
	});
}

<%-- $.ajax({
    url: "<%=contextPath%>/easyui/query",
    type: "post",
    success: function (text) {
    	var data = JSON.parse(text);//转换成json对象，必须的！！！不然有异常！！！  
    	$('#dg').datagrid('loadData', data); 
    }
}); --%>
</script>