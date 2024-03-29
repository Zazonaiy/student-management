<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改密码</title>
<link rel="stylesheet" href="./static/font/iconfont.css">
<link rel="stylesheet" href="./static/css/register.css">
<script type="text/javascript" src="./static/jquery-3.4.1.js"></script>
</head>
<body>

	<div class="container">
	<form>
        <!--头部图片-->
        <div class="head">
            <h1>没问题，请告诉我你的新密码</h1>
        </div>
        <!--主体注册框-->
        <div class="register">
            <div class="register-pane">
                <span class="iconfont icon-denglu"></span>
                <input placeholder="请输入用户名" type="text" name="username">
                <span class="iconfont icon-cuowu error" hidden></span>
            </div>
            <div class="register-pane">
                <span class="iconfont icon-denglumima-"></span>
                <input placeholder="请输入旧密码" type="password" name="oldpassword">
                <span class="iconfont icon-cuowu error" hidden></span>
            </div>
            <div class="register-pane">
                <span class="iconfont icon-denglumima-"></span>
                <input placeholder="请输入新密码" type="password" name="password">
                <span class="iconfont icon-cuowu error" hidden></span>
            </div>
            
            
            <div class="register-btn">
            	<button class="btn" type="button" onclick="update()">走你</button>
        	</div>
        </div>
        
	</form>
    </div>
</body>
</html>

<script>
	function update(){
		
		$(".error").attr("hidden", "hidden");
		var inputs = $("input").val();
		if (inputs == "" || inputs == undefined || inputs == null){
			alert("账号和密码不能为空哦");
			$(".error").attr("hidden", false);
			return false;
		}
		
		var uname = $("input[name=username]").val();
		var opass = $("input[name=oldpassword]").val();
		var upass = $("input[name=password]").val();
		if (opass == upass){
			alert("新密码不能跟旧密码一样哦~");
			$(".error:eq(1)").attr("hidden", false);
			$(".error:eq(2)").attr("hidden", false);
			$("input[name=oldpassword]").val("");
			$("input[name=password]").val("");
			return false;
		}
		
		$.post("login-controller", {action : "doUpdate", updateStatus : 4, username : uname, oldpassword : opass, newpassword : upass}, function(ret){
			console.log(ret.status);
			if (ret.status == 0){
				alert("密码修改成功！" + " ->" + uname + "<-, 快去登录吧" + " ^_^");
				$(location).attr("href", "login-controller");
			}else if(ret.status == 1){
				$(".error:eq(0)").attr("hidden", false);
				alert("用户名不存在哦  w(o_ow)");
				$("input[name=oldpassword]").val("");
				$("input[name=password]").val("");
			}else if(ret.status == 2){
				$(".error:eq(1)").attr("hidden", false);
				alert("旧密码验证错误  w(o_ow)");
				$("input[name=oldpassword]").val("");
				$("input[name=password]").val("");
			}else{
				$(".error").attr("hidden", false);
				alert("未知错误 _(???)_  " + ret.status);
				$("input[name=oldpassword]").val("");
				$("input[name=password]").val("");
			}
		})
	}

</script>