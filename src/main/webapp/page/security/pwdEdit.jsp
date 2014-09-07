<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
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
	var password=$('#password').val();
	var rePassword=$('#rePassword').val();
	if(password!=rePassword){
		Boxy.alert('两次输入密码不一致!');
		return false;
	}
}
</script>
</head>

<body>
<div class="content">
    <ul class="breadcrumb">
        <li>个人设置 》 修改密码</li>
    </ul>
    
	<div class="container-fluid">
        <div class="row-fluid">
			<div class="well">   
			    <form id="appForm" method="post" action="${contextPath}/user/manage/editPwd" onsubmit="return check();">
				    <input type="hidden" name="id" value="3">
				    
				    <table id="sample-table-1" class="table table-hover">
					    <tr>
					      <td width="13%"><span class="bod">请输入新密码</span></td>
					      <td width="87%"><input type="password" name="password" id="password" size="78" required/>&nbsp;</td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">请再输入一次</span></td>
					      <td width="87%"><input type="password" name="rePassword" id="rePassword" size="78" required/>&nbsp;</td>
					    </tr>
					    <tr>
					      <td>&nbsp;</td>
					      <td>
					      	<button type="submit" class="btn btn-primary"><i class="icon-save"></i> 确定</button>
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