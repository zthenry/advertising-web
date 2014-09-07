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
function setInput(id, version, status){
	$("#versionModal #id").val(id);
	$("#versionModal #version").val(version);
	$("#versionModal #status").val(status);
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
					<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#versionModal' onclick="setInput(null, '', null)">新建版本</button>
					<div class="btn-group">
					</div>
				</div>
				<div class="well">
					<table id="sample-table-1" class="table table-bordered table-hover">
						<tr>
					        <td width="5%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">ID</span></td>
					        <td width="30%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">平台名称</span></td>
					        <td width="25%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">版本号</span></td>
					        <td width="20%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">状态</span></td>
					        <td width="20%" align="center" bgcolor="#EEEEEE"><span class="tabletitle">操作</span></td>
					    </tr>
					    <c:forEach var="version" items="${adClientVersionList}" varStatus="status">
							<tr>
								<td align="center"><span class="tablecontent">${version.id }</span></td>
								<td align="center"><span class="tablecontent">${adClient.name }</span></td>
								<td align="center"><span class="tablecontent">${version.version }</span></td>
								<td align="center">
									<c:if test="${version.status==0 }">停用</c:if>
									<c:if test="${version.status==1 }">启用</c:if>
								</td>
								<td align="center">
									<c:if test="${version.status==0 }">
										<a class="btn btn-mini" href="${contextPath}/adClientVersion/updateStatus?id=${version.id }&status=1&version=${version.version }&adClientId=${adClient.id}">启用</a>
									</c:if>
									<c:if test="${version.status==1 }">
										<a class="btn btn-mini" href="${contextPath}/adClientVersion/updateStatus?id=${version.id }&status=0&version=${version.version }&adClientId=${adClient.id}">停用</a>
									</c:if>
									<a class="btn btn-mini" data-toggle='modal' data-target='#versionModal' onclick="setInput(${version.id }, '${version.version }', ${version.status })">编辑</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
            </div>
        </div>
    </div>
<div id="versionModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">编辑版本</h3>
  </div>
  <form action="${contextPath }/adClientVersion/edit" method="post">
  <input type="hidden" name="id" id="id"/>
  <input type="hidden" name="adClientId" id="adClientId" value="${adClient.id }"/>
  <div class="modal-body">
    <table id="sample-table-1" class="table">
        <tr>
	      <td>版本号</td>
	      <td><input type="text" name="version" id="version" required/>&nbsp;</td>
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