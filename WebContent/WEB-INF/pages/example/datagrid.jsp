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

	<table id="dg" class="easyui-datagrid" title="Basic DataGrid"
		style="width: 700px; height: 350px"
		method="post"             
        idField="FAppID"     
        rownumbers="true" 
        pagination="true"   
        singleSelect="true"   
        showFooter="false"     
        collapsible="false" 
        url="<%=contextPath%>/easyui/query"
		>
		<thead>
			<tr>
				<th data-options="field:'FAppID',width:80">应用ID</th>
				<th data-options="field:'FAppName',width:150">应用名称</th>
			</tr>
		</thead>
	</table>
	
	<%-- <table id="dg" class="easyui-datagrid" title="应用列表"
		style="width: 700px; height: 350px" rownumbers="true" iconCls="icon-save" rownumbers="true" showFooter="false"
		data-options="singleSelect:true,collapsible:false,url:'<%=contextPath%>/easyui/query',pagination:true,method:'post'">
		<thead>
			<tr>
				<th data-options="field:'FAppID',width:80">应用ID</th>
				<th data-options="field:'FAppName',width:150">应用名称</th>
			</tr>
		</thead>
	</table> --%>
	

	

</body>
</html>
<script type="text/javascript">
<%-- $.ajax({
    url: "<%=contextPath%>/easyui/query",
    type: "post",
    success: function (text) {
    	var data = JSON.parse(text);//转换成json对象，必须的！！！不然有异常！！！  
    	$('#dg').datagrid('loadData', data); 
    }
}); --%>
</script>