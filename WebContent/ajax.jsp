<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax演示</title>
<script type="text/javascript" src="./static/jquery-3.4.1.js"></script>
<style type="text/css">
div {
	color: red;
}
</style>
</head>
<body>
	<div id="content"></div>
	<button id="btn" onclick="jqAjax()">AJAX TEST</button>
</body>
</html>
<script>
	var httpReq;
	function protoAjax(){
		if (window.XMLHttpRequest){
			// 	新版浏览器
			httpReq = new XMLHttpRequest();
		}else{
			httpReq = new ActiveXObject("Micorsoft.XMLHTTP");
		}
		var content = document.getElementById("content");
		content.innerText = "loading...";
		httpReq.open("GET", "ajax", true);	//true异步, false同步
		httpReq.send();	//发起AJAX请求
		
		//通过回调得到响应内容
		//通过onreadystatechange事件触发回调
		httpReq.onreadystatechange = function(){
			//正常提交请求并返回
			if (httpReq.readyState == 4 && httpReq.status == 200){
				var responseText = httpReq.responseText;
				content.innerText = responseText;
			}
		}
	}
	
	function jqAjax(){
		$("#content").text("loading...");
		$.ajax({
			url : "ajaxstudent",
			type : "get",  // get or post
			async : true, //是否异步请求，默认true
			data : {	  //提交给服务器的参数
				s_num : "123",
				s_name : "张三"
			},
			dataType:"json",	//把后台传过来的JSON.toJSONString字符串转换为json格式
			success : function(ret){	//成功响应的回调,responseText
				$("#content").text(ret);
				alert(ret.sex);
			},
			error : function(ret){
				alert("发生错误");
			}
			
		})
	}
	
	function jqAjaxGet(){
		$("#content").text("loading...");
		$.get("ajax", {		//ajax请求, 数据, success回调函数
			s_num : "123",
			s_name : "张三"
		}, function(ret){
			$("#content").text(ret);
		})
	}
	
	function jqAjaxPost(){
		$("#content").text("loading...");
		$.post("ajax", {		//ajax请求, 数据, success回调函数
			s_num : "123",
			s_name : "张三"
		}, function(ret){
			$("#content").text(ret);
		})
	}
	
	function jqAjaxLoad(){
		$("#content").text("loading...");
		$("#content").load("content.jsp", function(ret) {
			alert(ret);
		});
	}
	
</script>
