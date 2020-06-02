
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="ErrorPage.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./static/css/HomePage.css">
<script type="text/javascript" src="./static/jquery-3.4.1.js"></script>
</head>
<body>

<div class="container">
	<h1>这里是主页</h1>
	<h2></h2>
	<a href="login-controller">注销</a>
	<a href="student_ajax">管理学生信息</a>
	<div>这里是整合版的主页</div>
</div>
	
</body>
</html>

<script>
	$(function(){
		$.post("home", {action : "getUserInfo"}, function(ret){
			var username = ret.username;
			$(".container h2").text("欢迎回来，" + username);
		})
	})
</script>