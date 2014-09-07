<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="/page/base.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<c:if test="${adSpaceId==0}">
			<title>新增广告位</title>
		</c:if>
		<c:if test="${adSpaceId>0}">
			<title>修改广告位</title>
		</c:if>
		<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<script type="text/javascript">
			var adspaceId = '${adSpaceId}';
			$(function(){
				var msg = '${msg}';
				
				if((msg!=undefined || msg!=null) && msg.length>0){
						alert(msg);
						window.location.href="${contextPath}/adspace/listIndex";
				}
				
				
			});
			
			
			function check(){
				if(adspaceId>0){
					$("#editAdspaceFormId").submit();
				}else{
					var id = $("#appClientId").val();
					var version = $("#appClientVersion").val();
					var num = $("#adSpaceNumber").val();
				
					var result = true;
					$.ajax({
      					type:"get",
      					url:"${contextPath}/adspace/checkBeforeSaveAdspace?id="+id+"&version="+version+"&num="+num,
      					dataType:"json",
      					success:function(data){
      						if(data.success){
      							$("#addAdspaceFormId").submit()
      						}else{
      							alert("广告位编号已存在");
      						}
      					}
      		   		});
				}
				
      		  
			}
			
			function getVersion(){
				var adClientId = $("#appClientId").val();
				if(adClientId==null || adClientId==undefined || adClientId==0){
					alert("请选择广告平台");
					$("#appClientVersion").html("");
					return false;
				}
				$.ajax({
      				type:"get",
      				url:"${contextPath}/adspace/getVersion/"+adClientId,
      				dataType:"json",
      				success:function(data){
      		    		$.each(data.versionList,function(){
      		    			$("#appClientVersion").append('<option value="'+this.version+'">'+this.version+'</option>');
      		    		});
      				}
      		   });
			}
		</script>
	</head>

	<body class="">
		<div class="content">

			<div class="header">
				<c:if test="${adSpaceId==0}">
					<h1 class="page-title">
						新增广告位
					</h1>
				</c:if>
				<c:if test="${adSpaceId>0}">
					<h1 class="page-title">
						修改广告位
					</h1>
				</c:if>
			</div>

			<ul class="breadcrumb">
				<li>
					<a href="javascript:history.back(-1);">返回上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li>
					<a href="index.html">广告位管理</a>
					<span class="divider">/</span>
				</li>

				<c:if test="${adSpaceId==0}">
					<li class="active">
						新增广告位
					</li>
				</c:if>
				<c:if test="${adSpaceId>0}">

					<li class="active">
						修改广告位
					</li>
				</c:if>
			</ul>

			<div class="container-fluid">
				<div class="row-fluid">

					<div class="btn-toolbar">
						<button class="btn btn-primary" onclick="check()">
							<i class="icon-save"></i> 保存
						</button>
						<div class="btn-group">
						</div>
					</div>
					<div class="well">
						<div id="myTabContent" class="tab-content">
							<div class="tab-pane active in" id="home">
								<c:if test="${adSpaceId==0}">
									<form id="addAdspaceFormId"
										action="${contextPath}/adspace/addAdspace" method="post">
										<label>
											平台名称
										</label>
										<select name="appClientId" id="appClientId"
											class="input-xlarge" onchange="getVersion()">
											<option value="0">
												请选择
											</option>
											<c:forEach var="adClient" items="${adClientList}">
												<option value="${adClient.id}">
													${adClient.name}
												</option>
											</c:forEach>
										</select>
										<label>
											平台版本
										</label>
										<select name="appClientVersion" id="appClientVersion"
											class="input-xlarge">
										</select>
										<label>
											广告位编号
										</label>
										<input type="text" value="" class="input-xlarge"
											name="adSpaceNumber" id="adSpaceNumber" />

										<label>
											广告位描述
										</label>
										<textarea rows="3" class="input-xlarge" id="description"
											name="description"></textarea>
										<label>
											状态
										</label>
										<select name="status" id="status" class="input-xlarge">
											<option value="1" selected="selected">
												启用
											</option>
<!-- 											<option value="0"> -->
<!-- 												禁用 -->
<!-- 											</option> -->
										</select>
										<label>
											是否支持广告组
										</label>
										<select name="supportAdlist" id="supportAdlist"
											class="input-xlarge">
											<option value="1">
												支持
											</option>
<!-- 											<option value="0" selected="selected"> -->
<!-- 												不支持 -->
<!-- 											</option> -->
										</select>
									</form>
								</c:if>
								
								
								<c:if test="${adSpaceId>0}">
									<form id="editAdspaceFormId"
										action="${contextPath}/adspace/addAdspace" method="post">
										
										<input type="hidden" value="${adSpace.id}"  id="id" name="id"/>
										<input type="hidden" value="${adSpace.appClientId}"  id="appClientId" name="appClientId"/>
										<label>
											平台名称:
										</label>
										<input type="text" value="${adSpace.appClientName}"  readonly="readonly"/>
										<label>
											平台版本
										</label>
										<input type="text" value="${adSpace.appClientVersion}"  name="appClientVersion" id="appClientVersion" readonly="readonly"/>
										
										<label>
											广告位编号
										</label>
										<input type="text" value="${adSpace.adSpaceNumber}" name="adSpaceNumber" id="adSpaceNumber" readonly="readonly"/>
										<label>
											广告位描述
										</label>
										<textarea rows="3" class="input-xlarge" id="description" name="description" >${adSpace.description}</textarea>
										<label>
											状态
										</label>
										<select name="status" id="status" class="input-xlarge">
											<c:if test="${adSpace.status==1}">
												<option value="1" selected="selected">
													启用
												</option>
<!-- 												<option value="0"> -->
<!-- 													禁用 -->
<!-- 												</option> -->
											</c:if>
											<c:if test="${adSpace.status==0}">
												<option value="1" >
													启用
												</option>
												<option value="0" selected="selected">
													禁用
												</option>
											</c:if>
											
										</select>
										<label>
											是否支持广告组
										</label>
										<select name="supportAdlist" id="supportAdlist" class="input-xlarge">
											<c:if test="${adSpace.supportAdlist==1}">
												<option value="1" selected="selected">
													支持
												</option>
<!-- 												<option value="0" > -->
<!-- 													不支持 -->
<!-- 												</option> -->
											</c:if>
											<c:if test="${adSpace.supportAdlist==0}">
												<option value="1">
													支持
												</option>
												<option value="0" selected="selected">
													不支持
												</option>
											</c:if>
											
										</select>
									</form>
								</c:if>
								
							</div>
							
						</div>

					</div>

				</div>
			</div>
		</div>

	</body>
</html>



