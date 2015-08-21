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
<h2>Basic Form</h2>
	<p>Fill the form and submit it.</p>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" title="New Topic" style="width:400px">
		<div style="padding:10px 60px 20px 60px">
	    <form id="ff" method="post">
	    	<table cellpadding="5">
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input class="easyui-textbox" type="text" name="userName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>年龄:</td>
	    			<td><input class="easyui-textbox" type="text" name="age" data-options="required:false,validType:'int'"></input></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td>
	    				<input type="radio" name="sex" value="1"><span>男</span><br/>
						<input type="radio" name="sex" value="2"><span>女</span><br/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>生日:</td>
	    			<td><input class="easyui-datebox" name="brithday" data-options="required:false"></input></td>
	    		</tr>
	    		<tr>
	    			<td>地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="address" data-options="required:fase"></input></td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input class="easyui-textbox" name="note" data-options="multiline:true" style="height:60px"></input></td>
	    		</tr>
	    	</table>
	    	
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
	    </div>
	    </div>
	</div>
	<script>
		function submitForm(){
			$('#ff').form('submit');
		}
		function clearForm(){
			$('#ff').form('clear');
		}
	</script>
</body>
</html>