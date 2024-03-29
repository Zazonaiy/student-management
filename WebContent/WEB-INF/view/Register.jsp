<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册</title>
<link rel="stylesheet" href="./static/font/iconfont.css">
<link rel="stylesheet" href="./static/css/register.css">
<script type="text/javascript" src="./static/jquery-3.4.1.js"></script>
</head>
<body>

    <div class="container">
    <form action="register" method="post" onsubmit="return checkForm()">
        <!--头部图片-->
        <div class="head">
            <h1>欢迎你加入我们，请告诉我你的名字</h1>
        </div>
        <!--主体注册框-->
        <div class="register">
        
            <div class="register-pane" id="u">
                <span class="iconfont icon-denglu"></span>
                <input placeholder="请输入用户名" type="text" name="username">
                <span class="iconfont icon-cuowu error" hidden></span>
            </div>
            <div class="register-pane" id="p">
                <span class="iconfont icon-denglumima-"></span>
                <input placeholder="请输入密码" type="password" name="password">
                <span class="iconfont icon-cuowu error" hidden></span>
            </div>
            <div class="register-pane" id="rp">
                <span class="iconfont icon-denglumima-"></span>
                <input placeholder="请输入密码" type="password" name="repassword">
                <span class="iconfont icon-cuowu error" hidden></span>
            </div>
            <div class="tiaokuan-pane">
                <span class="tiaokuan">请仔细阅读注册条款</span>
                <input type="checkbox" id="isconfirm" value="">
            </div>
            
            <div class="register-btn">
            <button class="btn" type="button" onclick="registe()">注册</button>
        	</div>
        </div>
  		
	</form>
    </div>
</body>
</html>

<script>
	function registe(){
		var isConfirm = $("#isconfirm").is(":checked");
		console.log(isConfirm);
		if ($("#u input").val() == "" || $("#p input").val() == "" || $("#rp input").val() == "") {
			alert("账号和密码不能为空哦");
			$(".error").attr("hidden", "hidden");
			$(".error").attr("hidden", false);
			return ;
		}
		if ($("#p input").val() != $("#rp input").val()){
			$(".error").attr("hidden", "hidden");
			$("#p").find(".error").attr("hidden", false);
			$("#rp").find(".error").attr("hidden", false);
			alert("输入密码不一致");
			return ;
		}
		if (!isConfirm){
			alert("请仔细阅读条款并勾选已读");
			return ;
		}
		var uname = $("#u input").val();
		var upass = $("#rp input").val();
		$.post("login-controller", {action : "doRegiste", registeStatus : 4, username : uname, password : upass}, function(ret){
			if (ret.status == 0){
				alert("注册成功！欢迎你" + " ->" + uname + "<-, 快去登录吧" + " ^_^");
				$(location).attr("href", "login-controller");
			}else{
				alert("账号已存在，注册失败" + " >_<  errorcode:" + ret.status);
			}
		})
		
	}

</script>