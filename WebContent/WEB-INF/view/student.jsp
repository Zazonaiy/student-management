<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.studentmam.model.*" %>
<%
	List<Student> studentList = (List<Student>) request.getAttribute("studentList");
	//String order_class = (String)request.getAttribute("order");
	//int a = 'a';
	//System.out.println(order_class);
	if (studentList == null){
		System.out.println("studentList is null ! ! !");
	}
	System.out.println(request.getAttribute("pageSize"));
	System.out.println(request.getAttribute("pageNo"));
	//if (request.getAttribute("pageNo")==null && request.getAttribute("pageSize")==null){
	//	request.setAttribute("pageNo", 1);
	//	request.setAttribute("pageSize", 5);
	//}

	int pagesize = (Integer) request.getAttribute("pageSize");
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生信息管理</title>
<script type="text/javascript" src="./static/jquery-3.4.1.js"></script>
<style type="text/css">
	a{
		text-decoration: none;
	}
	
</style>
</head>

<body>
	<h1 align="center">学生信息管理系统</h1>
	<div align="center">
		<form action="addstu" method="post">
			<div>学号: <input type="text" name="stu_num" required="required"></div>
			<div>姓名: <input type="text" name="stu_name" required="required"></div>
			<div>生日: <input type="date" name="birth_day" required="required"></div>
			<div>性别: <input type="text" name="sex"></div>
			<div> <input type="submit">  <button type="button" id="batchDelBtn">批量删除</button></div>
		</form>
	</div>
	<div align="center" id="table-head">
		<table width="60%" border="black solid 1px" >
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
			<%for(Student student: studentList){ %>
			<tr>
				
				<td><input class="cbox" type="checkbox" value=<%=student.getStuNum() %> />
					<%=student.getStuNum() %></td>
				<td><%=student.getStuName() %></td>
				<td><%=student.getBirthDay() %></td>
				<td><%=student.getSex() %></td>
				<td><a href="javascript://" onclick="deleteStudent('<%=student.getStuNum() %>')">删除</a>
					<a href="javascript://" onclick="updateStudent('<%=student.getStuNum() %>')">修改</a><td>
			</tr>
			<%} %>
			
		</table>
		<form method="post" action="student">
			<h4>学生信息查询</h4>
			
			姓名<input type="text" name="keyword" value="${keywords}">
			<button type="submit" >搜索</button>
		</form>
		
		<div class="updatePane" hidden="hidden">
			<form action="updstu" method="post">
				<h5>修改学生信息</h5>
				学号: <input type="text" name="stu_num" required="required">
				姓名: <input type="text" name="stu_name" required="required">
				生日: <input type="date" name="birth_day" required="required">
				性别: <input type="text" name="sex">
				<button type="submit">修改</button>
			</form>
		</div>
		
		<div style="text-align:center" id="pagingPane">
		<a href="javascript://" onclick="goToPage(1)">首页</a>
		<a href="javascript://" onclick="goToPage(${pageNo}-1)">上一页</a>
		<a href="javascript://" onclick="goToPage(${pageNo}+1)">下一页</a>
		<a href="javascript://" onclick="goToPage(${lastPage})">末页</a>
		</div>
		<div style="text-align:center">
		当前是第${pageNo}页，每页
		<select id="page_size_sel" onchange="queryWithPageSize($(this).val())">
		<option value="5">5</option>
		<option value="10">10</option>
		<option value="15">15</option>
		<option value="20">20</option>
		</select>
		条，共${lastPage}页，共${studentCount}条数据
		</div>
		<!-- 排序和分页的数据提交 -->
		<form method="post" action="orderstu" id="search_form">
			<input hidden="hidden" name="orderBy" value="${orderParam1}">
			<input hidden="hidden" name="orderWay" value="${orderParam2}">
			<input hidden="hidden" name="pageNo" value="${pageNo}">
			<input hidden="hidden" name="pageSize" value="${pageSize}">
		</form>
		
	</div>
</body>
</html>

<script>
	function deleteStudent(stuNum){
		if (confirm("真的要删除该学生么？\n") == true){
			window.location.href="delstu?stuNum="+stuNum;
			alert("删除成功");
			return true;
		}else{
			return false;
		}
	}
	function updateStudent(stuNum){
		$(".updatePane input:nth-child(2)").attr("value", stuNum);
		var pane = $(".updatePane");
		if (pane.attr("hidden") == "hidden"){
			pane.removeAttr("hidden");
		}else{
			pane.attr("hidden", "hidden");
		}
	}
	
	$("#batchDelBtn").click(function(){
		if (confirm("真的要删除这些学生么？\n") == true){
			var str = "";
	        $.each($("input:checkbox:checked"), function(){
	            str += $(this).val() + " ";
	        })
	        console.log(str);
	        window.location.href="delstu?stuNums="+str;
	        alert("批量删除成功");
			return true;
		}else{
			return false;
		}
		
	})
	$("#table-head tr:first a").click(function(){
		//alert($(this).attr("class"));
		//var param = $(this).attr("class");
		//window.location.href="orderstu?orderWay=" + param;
		var orderBy = $(this).attr("orderBy");
		var orderWay = $(this).attr("orderWay");

		$("input[name='orderBy']").val(orderBy);
		$("input[name='orderWay']").val(orderWay);
		//$("#search_form").submit();
		goToPage($("input[name='pageNo']").val());
		
	})
	
	function goToPage(pageNo){
		if (pageNo < 1){
			alert("已经是第一页了!");
			return ;
		}
		$("input[name='pageNo']").val(pageNo);
		$("#search_form").submit();
		$("#page_size_sel").val(${pageSize});
	}
	
	function queryWithPageSize(pageSize){
		console.log("^^^^^^^^^^^^^^^^^^^^^^" + pageSize);
		$("input[name='pageSize']").val(pageSize);
		goToPage(1);
		
	}
	
	$(function(){
		var order = "${order}";
		$("."+order).css("color","red");
		// TODO
		//alert($("input[name='pageNo']").val());
		
		var pagesize = <%=pagesize %>
		$("#page_size_sel").val(pagesize);

		//alert($("#page_size_sel").val());
	});
	

	
</script>