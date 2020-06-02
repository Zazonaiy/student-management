<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生信息管理系统(AJAX版)</title>
<script type="text/javascript" src="./static/jquery-3.4.1.js"></script>
<script type="text/javascript" src="./static/dialog-plus.js"></script>
<script type="text/javascript" src="./static/dialog.js"></script>
<style type="text/css">
	a{
		text-decoration: none;
	}
	#batchDelBtn, #addStuBtn{
		margin-top: 10px;
		height : 40px;
		weight : 400px;
	}
	.addPane{
		text-align: left;
	}
	.addPane input{
		padding: 1px 0px;
		width: 173px;
		height: 21px;
	}
	
	
</style>
</head>

<body>
	<h1 align="center">学生信息管理系统</h1>
	<div align="center" class="addPane" hidden="hiddeen">
		<form action="addstu" method="post">
			<div>学号: <input type="text" name="stu_num" required="required"></div>
			<div>姓名: <input type="text" name="stu_name" required="required"></div>
			<div>生日: <input type="date" name="birth_day" required="required"></div>
			<div>性别: <input type="text" name="sex"></div>
			<!-- <div align="center"> <button type="button" onclick="addStudent()">提交</button> </div> -->
		</form>
	</div>
	
	
	<div align="center" id="table-head">
		<table width="60%" border="black solid 1px" >
		<thead>
			<tr>
				<th>学号<a class="od-by-num-asc" href="javascript://" orderBy="s_num" orderWay="asc"> ↑ </a>
					<a class="od-by-num-desc" href="javascript://" orderBy="s_num" orderWay="desc"> ↓ </a></th>
				<th>姓名<a class="od-by-name-asc" href="javascript://" orderBy="s_name" orderWay="asc"> ↑ </a>
					<a class="od-by-name-desc" href="javascript://" orderBy="s_name" orderWay="desc"> ↓ </a></th>
				<th>出生日期<a class="od-by-birthday-asc" href="javascript://" orderBy="s_birthday" orderWay="asc"> ↑ </a>
					<a class="od-by-birthday-desc" href="javascript://" orderBy="s_birthday" orderWay="desc"> ↓ </a></th>
				<th>性别<a class="od-by-sex-asc" href="javascript://" orderBy="s_sex" orderWay="asc"> ↑ </a>
					<a class="od-by-sex-desc" href="javascript://" orderBy="s_sex" orderWay="desc"> ↓ </a></th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
			
		</table>
		<div align="center">
		<button type="button" id="batchDelBtn">批量删除</button>
		<button id="addStuBtn" onclick="openAddPane()">添加学生</button>
		</div>
		<form method="post" action="student_man">
			<h4>学生信息查询</h4>
			
			姓名<input type="text" name="keyword" value="" id="keyword">
			<button type="button" onclick="queryStudent()" >搜索</button>
		</form>
		
		<div class="updatePane" hidden="hidden">
			<form action="updstu" method="post">
				<h5>修改学生信息</h5>
				学号: <input type="text" name="stu_num" required="required">
				姓名: <input type="text" name="stu_name" required="required">
				生日: <input type="date" name="birth_day" required="required">
				性别: <input type="text" name="sex">
				<!-- <button type="button" onclick="updateStudent()">修改</button> !-->
			</form>
		</div>
	</div>
	
	<div style="text-align:center" id="pagingPane">
		<a href="javascript://" onclick="goToPage(1)">首页</a>
		<a href="javascript://" onclick="goToPage($('#pageNo').val()*1 - 1)">上一页</a>
		<a href="javascript://" onclick="goToPage($('#pageNo').val()*1 + 1)">下一页</a>
		<a href="javascript://" onclick="goToPage($('#lastPage').val()*1)">末页</a>
	</div>
	
	
	
	<div style="text-align:center" id="paggingSelect">
		<select id="page_size_sel" onchange="queryWithPageSize($(this).val())">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20">20</option>
		</select>
	</div>
	
		
	<form method="post" action="orderstu" id="search_form">
			<input hidden="hidden" name="orderBy" value="">
			<input hidden="hidden" name="orderWay" value="">
			<input hidden="hidden" name="pageNo" value="" id="pageNo">
			<input hidden="hidden" name="pageSize" value="">
			<input hidden="hidden" name="lastPage" value="" id="lastPage">
	</form>
	
	<div id="view_stu" hidden="hidden">
			<h5>修改学生信息</h5>
			<div>学号:  <span id="view_stu_num"></span></div>
			<div>姓名:  <span id="view_stu_name"></span></div>
			<div>生日:  <span id="view_birth_day"></span></div>
			<div>性别:  <span id="view_sex"></span></div>
			<!-- <button type="button" onclick="updateStudent()">修改</button> !-->

	</div>
		

</body>
</html>
<script>
	
	$("#table-head tr:first a").click(function(){
		//alert($(this).attr("class"));
		//var param = $(this).attr("class");
		//window.location.href="orderstu?orderWay=" + param;
		var orderBy = $(this).attr("orderBy");
		var orderWay = $(this).attr("orderWay");

		$("input[name='orderBy']").val(orderBy);
		$("input[name='orderWay']").val(orderWay);
		console.log($("input[name='orderBy']").val());
		console.log($("input[name='orderWay']").val());
		//$("#search_form").submit();
		goToPage($("input[name='pageNo']").val());
	
	})
	
	function goToPage(pageNo){
		//alert(pageNo);
		if (pageNo < 1){
			alert("已经是第一页了!");
			return ;
		}
		console.log(pageNo);
		$("input[name='pageNo']").val(pageNo);
		queryStudent()
		//$("#page_size_sel").val(${pageSize});
	}
	
	function queryWithPageSize(pageSize){
		console.log("^^^^^^^^^^^^^^^^^^^^^^" + pageSize);
		$("input[name='pageSize']").val(pageSize);
		goToPage(1);
		
	}
	
	
	
	/**
	 * 查询
	 */
	function queryStudent(){
		var keyword = $("#keyword").val();
		var orderParam1 = $("input[name='orderBy']").val();
		var orderParam2 = $("input[name='orderWay']").val();
		var paggingParam1 = $("input[name='pageNo']").val();
		var paggingParam2 = $("input[name='pageSize']").val();
		console.log(orderParam1);
		console.log(orderParam2);
		
		var tbody = $("#table-head table tbody");
		tbody.html("load...")
		$.get("student_man", 
				{'keyword' : keyword, 'action' : 'queryStudent', 'orderParam1' : orderParam1, 'orderParam2' : orderParam2,
				'paggingParam1' : paggingParam1, 'paggingParam2' : paggingParam2}, 
				function(ret){
			console.log(ret);
			//var json = JSON.parse(ret)
			var json = ret;
			var stuArray = json.studentList;
			$("#keyword").val(json.keywords);
			$("input[name='orderBy']").val(json.orderParam1);
			$("input[name='orderWay']").val(json.orderParam2);
			$("input[name='pageNo']").val(json.pageNo);
			$("input[name='pageSize']").val(json.pageSize);
			$("input[name='lastPage']").val(json.lastPage);
			//$("#paggingSelect select").before().text("");
			var tmp = $("#paggingSelect select").children().clone();
			//$("#paggingSelect select").empty().append(tmp);
			//$("#paggingSelect p:first").text("当前是第"+json.pageNo+"页，每页");
			$("#paggingSelect").contents().filter(function(){
				return this.nodeType == 3;
			}).remove();
			
			$("#paggingSelect select").before("当前是第"+json.pageNo+"页，每页");
			$("#paggingSelect select").after("条，共"+json.lastPage+"页，共"+json.studentCount+"条数据");
			
			
			tbody.html("");
			console.log(stuArray);
			
			$.each(stuArray, function(index, value){
				/*
				tbody.append(`<tr>
					<td><input class="cbox" type="checkbox" value="value.s_num"/> 
						${value.s_num}</td>
					<td>${value.s_name}</td>
					<td>${value.s_birthday}</td>
					<td>${value.s_sex}</td>
					<td><a href="javascript://" onclick="deleteStudent('value.s_num')">删除</a>
						<a href="javascript://" onclick="updateStudent('value.s_num')">修改</a> </td>	
						
				</tr>`);
				*/
				tbody.append("<tr><td><input class='cbox' type='checkbox' value='"+value.s_num+"' />" + "<a href='javascript://' onclick='viewStudent(" + value.s_num + ")'>"+value.s_num+"</a></td>" + 
					"<td>" + value.s_name + "</td>" + 
					"<td>" + value.s_birthday + "</td>" + 
					"<td>" + value.s_sex + "</td>" + 
					"<td><a href='javascript://' onclick='deleteStudent("+value.s_num+")'> 删除 </a>" + 
					"<a href='javascript://' onclick='openUpdatePane("+value.s_num+")'> 修改 </a> </td>" + "</tr>")
			});
			
			
		});
	}
	/**
	 * 查看详情
	 */
	
	function viewStudent(stuNum){
		$.get('student_man', {action : "viewStudentInfo", "stuNum" : stuNum}, 
				function (result){
			console.log(result);
			console.log(result.status);
			if (result.status == 0){
				$("#view_stu div span:eq(0)").text(result.stuNum);
				$("#view_stu div span:eq(1)").text(result.stuName);
				$("#view_stu div span:eq(2)").text(result.stuBirthday);
				$("#view_stu div span:eq(3)").text(result.stuSex);
				var d = dialog({
					title : "浏览学生信息",
					content : $("#view_stu")
				})
				d.showModal();
			}else{
				alert("失败 " + result.status)
			}
		})
	}
	
	/**
	 * 修改
	 */
	//展开窗口
	function openUpdatePane(stuNum){
		/*
		$(".updatePane input:nth-child(2)").attr("value", stuNum);
		var pane = $(".updatePane");
		if (pane.attr("hidden") == "hidden"){
			pane.removeAttr("hidden");
		}else{
			pane.attr("hidden", "hidden");
		}*/
		
		$(".updatePane input:nth-child(2)").attr("value", stuNum);
		var d = dialog({
			title : "修改学生信息",
			content: $(".updatePane"),
			yesText : "修改",
			noText : "关闭"
		}, function(){
			updateStudent();
			this.close();
		}, function(){
			this.close();
		});
		d.showModal();
	}
	function updateStudent(){
		var stuNum = $(".updatePane input[name='stu_num']").val();
		var stuName = $(".updatePane input[name='stu_name']").val();
		var birthDay = $(".updatePane input[name='birth_day']").val();
		var sex = $(".updatePane input[name='sex']").val();
		$.post("student_man", {'action' : 'updateStudent', 'stuNum' : stuNum, 'stuName' : stuName, 'birthDay' : birthDay, 
			'sex' : sex}, function(result){
			if (result.status == 0){
				alert("修改成功");
				queryStudent();
			}else{
				alert(result.status);
			}
			$(".updatePane").attr("hidden", "hidden");
			
		});
	}
	
	/**
	 * 添加
	 */
	function openAddPane(){
		var d = dialog({
			title : "新增学生信息",
			content: $(".addPane"),
			yesText : "添加",	//不管用
			noText : "关闭",		//不管用
			yesClose : false 	//不管用
		}, function(){
			var isInputNull = false;
			$.each($(".addPane input"), function(){
				if ($(this).val().trim() == null || $(this).val().trim() == ""){
					isInputNull = true;
					return false;
				}
			})
			if (!isInputNull){
				addStudent();
				this.close();
			}else{
				alert("输入框不能为空");
			}
			
		}, function(){
			this.close();
		});
		d.showModal();
	}
	function addStudent(){
		
		
		var stuNum = $(".addPane input[name='stu_num']").val();
		var stuName = $(".addPane input[name='stu_name']").val();
		var birthDay = $(".addPane input[name='birth_day']").val();
		var sex = $(".addPane input[name='sex']").val();
		$.post("student_man", {'action' : 'addStudent', 'stuNum' : stuNum, 'stuName' : stuName, 'birthDay' : birthDay, 
			'sex' : sex}, function(result){
			if (result.status == 0){
				alert("添加成功");
				queryStudent();	
			}else{
				alert(result.status)
			}
			$(".addPane input[name='stu_num']").val("");
			$(".addPane input[name='stu_name']").val("");
			$(".addPane input[name='birth_day']").val("");
			$(".addPane input[name='sex']").val("");
			return ;
		});
	}
	
	/**
	 * 删除
	 */
	function deleteStudent(stuNum){
		if (confirm("真的要删除该学生么？\n") == true){
			$.post("student_man", {'action' : 'deleteStudent', 'stuNum' : stuNum}, function(result){
				if (result.status == 0){
					alert("删除成功");
				}else{
					alert(result.status);
				}
				queryStudent();
			});
			
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除
	 */
	$("#batchDelBtn").click(function(){
		if ($("input:checkbox:checked").length == 0){
			alert("你没有选中学生");
			return ;
		}
		if (confirm("正在进行批量操作，是否继续？\n") == true){
			var str = "";
			$.each($("input:checkbox:checked"), function(){
				str += $(this).val() + " ";
			})
			$.post("student_man", {'action' : 'deleteStudent', 'stuNums' : str}, function(result){
				if (result.status == 0){
					alert("批量删除成功");
				}else{
					alert(result.status);
				}
				queryStudent();
			});
			
			return true;
		}else{
			return false;
		}
		
			
	});
	
	
	
	
	
	$(function(){
		//alert("!");
		//jqAjax();
		queryStudent();
		//var modal = dialog.defaults;
		//modal.skin['facebook'];
		//modal.drag=true;
	});
	
	
	
	
</script>