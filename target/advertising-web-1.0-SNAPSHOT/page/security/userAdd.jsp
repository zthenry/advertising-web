<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="/page/base.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	<%
	String msg=(String)request.getAttribute("msg");
	if(msg!=null && !msg.equals("")){
	%>
	Boxy.alert('<%=msg%>');
	<%}%>
});
function check(){
	 var search_str = /^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/;
	 var email_val = $("#email").val();
	 if(!search_str.test(email_val)){        
	 	alert("请输入正确的邮箱 !");
	 	return false;
	 }
}
</script>
</head>

<body>
<div class="content">
    <ul class="breadcrumb">
        <li>权限管理 》 新增管理员</li>
    </ul>
    
	<div class="container-fluid">
        <div class="row-fluid">
			<div class="well">   
			    <form id="userForm" method="post" action="${contextPath}/user/manage/add" onsubmit="return check();">
				    <table id="sample-table-1" class="table table-hover">
					    <tr>
					      <td width="13%"><span class="bod">用户名</span></td>
					      <td width="87%"><input type="text" name="userName" id="userName" size="78" required/>&nbsp;</td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">密码</span></td>
					      <td width="87%"><input type="password" name="password" id="password" size="78" required/>&nbsp;</td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">邮箱</span></td>
					      <td width="87%"><input type="text" name="email" id="email" size="78" required />&nbsp;</td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">状态</span></td>
					      <td width="87%">
					      	<select name="status" id="status" class="input-small" required/>
								<option value="1">启用</option>	      
								<option value="0">停用</option>	      
					      	</select>
						  </td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">设置角色</span></td>
					      <td width="87%">
					      <c:forEach var="roles" items="${rolesList}" varStatus="status">
					      		<input type="radio" name="roleId" value="${roles.id }" required/>&nbsp;${roles.name }
					      </c:forEach>
					      </td>
					    </tr>
					    <tr>
					      <td>&nbsp;</td>
					      <td>
					      	<button type="submit" class="btn btn-primary"><i class="icon-save"></i> 确定</button>
				    		<button type="button" class="btn" onclick="history.go(-1);">返 回</button>
					      </td>
					    </tr>
				    </table>
			    </form>
			</div>
		</div>
	</div>
</div>
</body>
</html>