<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>APP列表</title>
<jsp:include page="/page/base.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	tishi();
});
function updateStatus(id, status){
	$.ajax({
		url:"${contextPath}/user/manage/updateStatus",
		type:'post',
		data:{id : id, status : status},
		dataType:'json',
		success:function(data){
			Boxy.alert(data.msg);
			window.parent.frames.mainFrame.location.reload();
		}
	});
}
function resetPwd(id){
	$.ajax({
		url:"${contextPath}/user/manage/resetPwd",
		type:'post',
		data:{id : id},
		dataType:'json',
		success:function(data){
			Boxy.alert(data.msg);
		}
	});
}
function tishi(){
	if('${param.flag}'=='0'){
		Boxy.alert('操作失败');
	}
	if('${param.flag}'=='1'){
		Boxy.alert('操作成功');
	}		
}
</script>
</head>

<body>
	<div class="content">
        <ul class="breadcrumb">
            <li>权限管理 》 管理员列表</li>
        </ul>
        <div class="container-fluid">
            <div class="row-fluid">
				<div class="btn-toolbar">
				    <button class="btn btn-primary" onclick="location.href='${contextPath }/user/manage/toAddUser'"><i class="icon-plus"></i> 新增管理员</button>
				</div>
				<div class="well">
					<table id="sample-table-1" class="table table-bordered table-hover">
						<tr>
					        <td width="2%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">ID</span></td>
					        <td width="10%" align="left" bgcolor="#EEEEEE"><span class="tabletitle">名称</span></td>
					        <td width="10%" align="left" bgcolor="#EEEEEE"><span class="tabletitle">邮箱</span></td>
					        <td width="10%" align="left" bgcolor="#EEEEEE"><span class="tabletitle">编辑</span></td>
					        <td width="10%" align="left" bgcolor="#EEEEEE"><span class="tabletitle">重置密码</span></td>
					        <td width="10%" align="left" bgcolor="#EEEEEE"><span class="tabletitle">禁用/启用</span></td>
					    </tr>
					    <c:forEach var="user" items="${userList}" varStatus="status">
							<tr>
								<td align="center"><span class="tablecontent">${user.id }</span></td>
								<td align="left"><span class="tablecontent">${user.userName }</span></td>
								<td align="left"><span class="tablecontent">${user.email }</span></td>
								<td align="left"><a class="btn btn-mini" href="${contextPath }/user/manage/toEditUser?id=${user.id }">编辑</a></td>
								<td align="left"><a class="btn btn-mini" href="#" onclick="resetPwd(${user.id })">重置密码</a></td>
								<td align="left">
									<c:if test="${user.status==0 }">
										<a class="btn btn-mini" href="${contextPath}/user/manage/updateStatus?id=${user.id }&status=1">启用</a>
									</c:if>
									<c:if test="${user.status==1 }">
										<a class="btn btn-mini" href="${contextPath}/user/manage/updateStatus?id=${user.id }&status=0">禁用</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
            </div>
        </div>
    </div>
</body>
</html>