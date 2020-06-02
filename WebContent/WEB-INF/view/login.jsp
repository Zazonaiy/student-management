<%@ page import="com.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>学生信息管理系统-登录</title>
    <link rel="stylesheet" href="./static/css/login.css">
    <link rel="stylesheet" href="./static/font/iconfont.css">
    <script type="text/javascript" src="./static/jquery-3.4.1.js"></script>
    <link rel="shortcut icon" href="#" />
</head>
<body>


<div class="container">
        <!-- 头部 -->
        <div class="head">
            <image src="./static/img/1.jpg" id="headimg"></image>
            <text>法号壹猛男</text>
            <text>四川省乐山市</text>
        </div>

        <form action="validate" method="post" onsubmit="return checkForm()">
            <div class="login">
                <!--用户名-->
                <div class="login-pane">
                    <span class="iconfont icon-denglu"></span>
                    <input placeholder="请输入用户名" type="text" name="username" value="">
                    <span class="iconfont icon-cuowu error" hidden="true"></span>
                </div>
                <!--密码-->
                <div class="login-pane">
                    <span class="iconfont icon-denglumima-"></span>
                    <input placeholder="请输入密码" type="password" name="password" value="">
                    <span class="iconfont icon-cuowu error" hidden="true"></span>
                </div>
            </div>

            <!--登录按钮-->
            <button class="login-btn" type="button" onclick="validate()">登录</button>
            <a href="login-controller?action=doUpdate&&updateStatus=-1" style="color:skyblue;">修改密码</a>
        </form>
            <!--注册按钮-->
            <button class="login-btn reg-btn" type="button" onclick="reigister()">注册</button>

    </div>
</body>
</html>
<script>
	$(function(){
		$(".login .error").attr("hidden", "hidden");
	})
	
	
	function validate(){
		$(".login .error").attr("hidden", "hidden");
		var username = $(".login input:first").val();
		var password = $(".login input:last").val();
		if (username == "" || password == ""){
			if (username == ""){
				$(".login .error:first").attr("hidden", false);
			}
			if (password == ""){
				$(".login .error:last").attr("hidden", false);
			}
			alert("用户名或密码不能为空");
			return ;
		}
		$.post("login-controller", {action : "doLogin", "username" : username, "password" : password}, function(ret){
			if (ret.status == 0){
				$(location).attr("href", "home");
				return ;
			}else{
				if (ret.status == 100){
					alert("登陆失败: 系统错误 " + ret.status);
				}else if (ret.status == 2){
					$(".login .error:last").attr("hidden", false);
					alert("密码错误");
				}else if (ret.status == 1){
					$(".login .error:first").attr("hidden", false);
					alert("用户名不存在")
				}
				var password = $(".login input:last").val("");
				return ;
			}
		});
	}
	
	function reigister(){
		$(location).attr("href", "login-controller?action=doRegiste&&registeStatus=-1");
	}








	
//	$(function(){
//		
		/*
		if (a == false){
			$(".error").attr("hidden", false);
			$("input:last").attr("placeholder", "用户名或密码错误");
		}*/
//		if (a == 1){
//			$(".error:first").attr("hidden", false);
//			$("input:first").attr("placeholder", "用户名不存在");
//		}
//		if (a == 2){
//			$(".error:last").attr("hidden", false);
//			$("input:last").attr("placeholder", "密码错误");
//		}
//	});
//	function checkForm(){
//		var inputs = $("input").val();
		
//		if (inputs == "" || inputs == undefined || inputs == null){
//			alert("账号和密码不能为空哦");
//			return false;
//		}
//		return true;
//	}
//	$(".reg-btn").click(function(){
//		$(window).attr('location','Register.jsp');
//	})
	
</script>