<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../js/easyui/demo.css">
<script type="text/javascript" src="../js/boot.js"></script>
</head>
<body>
	<h2>Client Side Pagination in DataGrid</h2>
	<p>This sample shows how to implement client side pagination in
		DataGrid.</p>
	<div style="margin: 20px 0;"></div>

	<table id="dg">
		<thead>
			<tr>
				<th field="itemid" width="80">Item ID</th>
				<th field="productid" width="100">Product</th>
				<th field="listprice" width="80" align="right">List Price</th>
				<th field="unitcost" width="80" align="right">Unit Cost</th>
				<th field="attr1" width="240">Attribute</th>
				<th field="status" width="60" align="center">Status</th>
			</tr>
		</thead>
	</table>
</body>
</html>
<script type="text/javascript">
	$(function() {
		$('#dg').datagrid({ 
	        title:'测试数据列表', 
	        iconCls:'icon-edit',//图标 
	        width: 700, 
	        height: 'auto', 
	        //nowrap: false, 
	        //striped: true, 
	        //border: true, 
	        //collapsible:false,//是否可折叠的 
	        //fit: true,//自动大小 
	        //url:'listApp.action',
	        url:'/datagrid_data1.json',
	        //sortName: 'code', 
	        //sortOrder: 'desc', 
	        //remoteSort:false,
	        autoRowHeight:false,
	        idField:'itemid', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        frozenColumns:[[ 
	            {field:'ck',checkbox:true} 
	        ]],
	        toolbar: [{ 
	            text: '添加', 
	            iconCls: 'icon-add', 
	            handler: function() { 
	                //openDialog("add_dialog","add"); 
	            } 
	        }, '-', { 
	            text: '修改', 
	            iconCls: 'icon-edit', 
	            handler: function() { 
	                //openDialog("add_dialog","edit"); 
	            } 
	        }, '-',{ 
	            text: '删除', 
	            iconCls: 'icon-remove', 
	            handler: function(){ 
	                //delAppInfo(); 
	            } 
	        }], 
	    }); 
		
		var pager = $('#dg').datagrid('getPager'); // get the pager of datagrid
		$(pager).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	    }); 
		/* pager.pagination({
			buttons : [ {
				iconCls : 'icon-search',
				handler : function() {
					alert('search');
				}
			}, {
				iconCls : 'icon-add',
				handler : function() {
					alert('add');
				}
			}, {
				iconCls : 'icon-edit',
				handler : function() {
					alert('edit');
				}
			} ]
		}); */
	})
</script>