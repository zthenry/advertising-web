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
		url:"${contextPath}/adClient/updateStatus",
		type:'post',
		data:{id : id, status : status},
		dataType:'json',
		success:function(data){
			Boxy.alert(data.msg);
			window.parent.frames.mainFrame.location.reload();
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
function setInput(id, name, status){
	$("#adClientModal #id").val(id);
	$("#adClientModal #name").val(name);
	$("#adClientModal #status").val(status);
}
function setInputV(adClientId){
	$("#versionModal #adClientId").val(adClientId);
}
</script>
</head>

<body>
<div class="content">
        
        <ul class="breadcrumb">
            <li>广告管理 》 广告应用平台列表</li>
        </ul>

        <div class="container-fluid">
            <div class="row-fluid">
                    
				<div class="btn-toolbar">
					<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#adClientModal'>创建广告应用平台</button>
					<div class="btn-group">
					</div>
				</div>
				<div class="well">
					<table id="sample-table-1" class="table table-bordered table-hover">
						<tr>
					        <td width="5%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">ID</span></td>
					        <td width="30%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">平台名称</span></td>
					        <td width="25%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">支持的版本</span></td>
					        <td width="20%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">状态</span></td>
					        <td width="20%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">操作</span></td>
					    </tr>
					    <c:forEach var="adClient" items="${adClientList}" varStatus="status">
							<tr>
								<td align="center"><span class="tablecontent">${adClient.id }</span></td>
								<td align="center"><span class="tablecontent">${adClient.name }</span></td>
								<td align="center"><span class="tablecontent">${adClient.adClientVersionStr }</span></td>
								<td align="center">
									<c:if test="${adClient.status==0 }">停用</c:if>
									<c:if test="${adClient.status==1 }">启用</c:if>
								</td>
								<td align="center">
									<c:if test="${adClient.status==0 }">
										<a class="btn btn-mini" href="${contextPath}/adClient/updateStatus?id=${adClient.id }&status=1">启用</a>
									</c:if>
									<c:if test="${adClient.status==1 }">
										<a class="btn btn-mini" href="${contextPath}/adClient/updateStatus?id=${adClient.id }&status=0">停用</a>
									</c:if>
									<a class="btn btn-mini" data-toggle='modal' data-target='#adClientModal' onclick="setInput(${adClient.id }, '${adClient.name }', ${adClient.status })">编辑</a>
									<a class="btn btn-mini" href="${contextPath}/adClientVersion/list?adClientId=${adClient.id }">版本管理</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
            </div>
        </div>
    </div>
<div id="adClientModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">编辑广告应用平台</h3>
  </div>
  <form action="${contextPath }/adClient/edit" method="post">
  <input type="hidden" name="id" id="id"/>
  <div class="modal-body">
    <table id="sample-table-1" class="table">
        <tr>
	      <td>名称</td>
	      <td><input type="text" name="name" id="name" required/>&nbsp;</td>
	    </tr>
	    <tr>
	      <td>状态</td>
	      <td><select name="status" id="status" class="input-small" required/>
				<option value="1">启用</option>	      
				<option value="0">停用</option>	      
	      	  </select>
      	  &nbsp;</td>
	    </tr>
      </table>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    <button class="btn btn-primary">保存</button>
  </div>
  </form>
</div>
</body>
</html>