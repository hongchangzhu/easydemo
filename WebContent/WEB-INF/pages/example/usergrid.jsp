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
<script type="text/javascript" src="<%=contextPath%>/js/boot.js"></script>
</head>
<body>
<div id="p" class="easyui-panel" title="" style="width:800px;height:100%;padding:10px;align:center">
	<h2>Basic DataGrid</h2>
	<p>The DataGrid is created from markup, no JavaScript code needed.</p>
	<div style="margin: 20px 0;"></div>

	<table id="dg" class="easyui-datagrid" title="用户列表"
		toolbar="#toolbar" style="width: 700px; height: 250px"
		showFooter="false" idField="userName"
		data-options="singleSelect:true,collapsible:false,url:'<%=contextPath%>/user/query',method:'post'">
		<thead>
			<tr>
				<th data-options="field:'userName',width:80,align:'center'">姓名</th>
				<th data-options="field:'age',width:100,align:'center'">年龄</th>
				<th data-options="field:'sex',width:80,align:'center'">性别</th>
				<th data-options="field:'brithday',width:120,align:'center'">生日</th>
				<th data-options="field:'address',width:320,align:'center'">地址</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:void(0)" iconCls="icon-add" plain="true"
			class="easyui-linkbutton" onclick="$('#w').window('open')">新增</a>
	</div>
	<div id="w" class="easyui-window" title="增加用户" 
		data-options="modal:true,closed:true,closable:true,minimizable:false,maximizable:false,collapsible:false,resizable:false,iconCls:'icon-save'" 
		style="width:270px;height:350px;padding:0px;">
		<div style="padding:10px 10px 10px 10px">
	    <form id="ff" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input class="easyui-textbox" type="text" name="userName" data-options="required:true" style="width:180px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>年龄:</td>
	    			<td><input class="easyui-numberbox" type="text" name="age" min="0" data-options="required:false" style="width:180px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td>
	    				<input type="radio" name="sex" value="1"><span>男</span>
						<input type="radio" name="sex" value="2"><span>女</span>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>生日:</td>
	    			<td><input class="easyui-datebox" name="brithday" data-options="required:false" style="width:180px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="address" data-options="required:false" style="width:180px"></input></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input class="easyui-textbox" name="note" data-options="multiline:true" style="height:60px;width:180px"></input></td>
	    		</tr>
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div>
	    </div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
function add(){
	//window.open('<%=contextPath%>
	//user/insert', '_self');
	}
function submitForm(){
	$('#ff').form('submit');
}
function clearForm(){
	$('#ff').form('clear');
}
</script>