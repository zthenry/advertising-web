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
}
</script>
</head>

<body>
<div class="content">
    <ul class="breadcrumb">
        <li>广告管理 》 编辑广告</li>
    </ul>
    
	<div class="container-fluid">
        <div class="row-fluid">
			<div class="well">   
			    <form id="userForm" method="post" action="${contextPath}/ad/edit" onsubmit="return check();">
			    <input type="hidden" name="id" value="${ad.id }"/>
				    <table id="sample-table-1" class="table table-hover">
					    <tr>
					      <td width="13%"><span class="bod">广告名称</span></td>
					      <td width="87%"><input type="text" name="adName" id="adName" value="${ad.adName }" size="78" required/>&nbsp;</td>
					    </tr><tr>
					      <td width="13%"><span class="bod">picId</span></td>
					      <td width="87%"><input type="text" name="picId" id="picId" value="${ad.picId }" size="78" required/>&nbsp;</td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">广告语</span></td>
					      <td width="87%"><input type="text" name="adWord" id="adWord" value="${ad.adWord }" size="78" required/>&nbsp;</td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">android url</span></td>
					      <td width="87%"><input type="text" name="androidUrl" id="androidUrl" value="${ad.androidUrl }" size="78" required url/>&nbsp;</td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">ios url</span></td>
					      <td width="87%"><input type="text" name="iosUrl" id="iosUrl" value="${ad.iosUrl }" size="78" required url/>&nbsp;</td>
					    </tr>
					    <tr>
					      <td width="13%"><span class="bod">状态</span></td>
					      <td width="87%">
					      	<select name="status" id="status" class="input-small" required/>
								<option value="1">启用</option>	      
								<option value="0">停用</option>	      
					      	</select>&nbsp;</td>
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