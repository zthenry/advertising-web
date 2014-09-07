<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="/page/base.jsp"></jsp:include>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<link rel="stylesheet" type="text/css" href="${path}/resource/style/login_style.css"/>
<script type="text/javascript">

$(function(){
	$("#submit").click(function(){
		var userName = document.getElementById("userName").value;
		
	});
});
</script>
</head>

<body>
<form action="<%=request.getContextPath()%>/user/manage/index" method="post" id="userForm">
<div class="wap"><font color="#ffffff"></font>
	<div class="top"></div>
    <div class="bot">
    <div class="con">
    	<label>用户名</label><input name="userName" id="userName" type="text" /><br>
        <label class="secr">密码</label><input name="password" id="password" type="password" /><br>
        <input type="submit" id="submit" class="btn_land" value="登录"/>
    </div>
    </div>
</div>
</form>
</body>
</html>
