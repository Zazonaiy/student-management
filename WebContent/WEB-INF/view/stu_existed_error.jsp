<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生已存在，无法添加</title>
</head>
<body>
	<h1 align="center">学生信息管理系统</h1>
	<div align="center">
		<form action="" method="post">
			<div>学号: <input type="text" name="stu_num" required="required"></div>
			<div>姓名: <input type="text" name="stu_name" required="required"></div>
			<div>生日: <input type="date" name="birth_day" required="required"></div>
			<div>性别: <input type="text" name="sex"></div>
			<div> <input type="submit"></div>
		</form>
	</div>
	<div align="center">
		<table width="60%" border="black solid 1px">
			<tr>
				<th>学号</th>
				<th>姓名</th>
				<th>出生日期</th>
				<th>性别</th>
				<th>操作</th>
			</tr>
			
		</table>
	</div>
</body>
</html>
<script>
	window.onload=function(){
		alert("学生已存在，无法添加");
		window.location.href="student";
	}
</script>