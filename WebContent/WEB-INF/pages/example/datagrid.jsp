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

	<table id="dg">
		<thead>
			<tr>
				<th data-options="field:'FAppID',width:80">FAppID</th>
				<th data-options="field:'FAppName',width:100">FAppName</th>
			</tr>
		</thead>
	</table>
</body>
</html>
<script type="text/javascript">
	//datagrid初始化 
	$('#dg').datagrid({
		title : '应用系统列表',
		iconCls : 'icon-edit',//图标 
		width : 700,
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,//是否可折叠的 
		fit : true,//自动大小 
		url : '<%=basePath%>/easyui/query',
		//sortName: 'code', 
		//sortOrder: 'desc', 
		remoteSort : false,
		idField : 'FAppID',
		singleSelect : false,//是否单选 
		pagination : true,//分页控件 
		rownumbers : true,//行号 
		frozenColumns : [ [ {
			field : 'FAppName',
			checkbox : true
		} ] ],
		toolbar : [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				openDialog("add_dialog", "add");
			}
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : function() {
				openDialog("add_dialog", "edit");
			}
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				delAppInfo();
			}
		} ],
	});
	//设置分页控件 
	var p = $('#dg').datagrid('getPager');
	$(p).pagination({
		pageSize : 10,//每页显示的记录条数，默认为10 
		pageList : [ 5, 10, 15 ],//可以设置每页记录条数的列表 
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	/*onBeforeRefresh:function(){
	    $(this).pagination('loading');
	    alert('before refresh');
	    $(this).pagination('loaded');
	}*/
	});
</script>