<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="/page/base.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>广告列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){});
		
	</script>

  </head>
  
  <body>
    <div class="content">
    		 <div class="header">

				<h1 class="page-title">
					广告位配置详情
				</h1>
			</div>

			<ul class="breadcrumb">
				<li>
					<a href="javascript:history.back(-1);">返回上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li>
					<a href="index.html">广告位管理</a>
					<span class="divider">/</span>
				</li>
				<li class="active">
					广告配置信息
				</li>
			</ul>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="search-well">
							平台名称:
							<input type="text" value="${appClientName}"  id="appClientName" readonly="readonly"/>
							平台版本：
							<input type="text" value="${version}"  id="appClientName" readonly="readonly"/>
							广告位编号：
							<input type="text" value="${adspaceNum}"  id="appClientName" readonly="readonly"/>
							广告位配置ID：
							<input type="text" value="${adSpaceConf.id}"  id="appClientName" readonly="readonly"/>
							<br>创建时间：
							<input type="text" value="<fmt:formatDate value="${adSpaceConf.createTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date"/>"  readonly="readonly"/>
							开始生效时间：
							<input type="text" value="<fmt:formatDate value="${adSpaceConf.startTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date"/>"  readonly="readonly"/>
							生效结束时间：
							<input type="text" value="<fmt:formatDate value="${adSpaceConf.endTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date"/>"  readonly="readonly"/>
						    <div class="btn-toolbar">
						    <c:if test="${isExpire==1}">
								<button class="btn btn-primary"
									onclick="location.href='${contextPath }/adspace/toEditAdSpaceConf?adspaceConfId=${adSpaceConf.id}'">
									修改
								</button>
								<div class="btn-group">
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>	
			<div class="well">
				<strong>关联的广告信息</strong>
				<table id="sample-table-1" class="table table-bordered table-hover">
					<tr>
						<td width="5%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">广告ID</span>
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">广告名称</span>
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">广告语</span>
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">图片</span>
						</td>
						<td width="15%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">安卓url</span>
						</td>
						<td width="15%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">IOS_url</span>
						</td>
						<td width="15%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">创建时间</span>
						</td>
						<td width="5%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">标记名</span>
						</td>
						
					</tr>
					<c:forEach var="ad" items="${advertisingList}">
						<tr>
							<td>${ad.id}</td>
							<td>${ad.adName}</td>
							<td>${ad.adWord}</td>
							<td>${ad.picId}</td>
							<td>${ad.androidUrl}</td>
							<td>${ad.iosUrl}</td>
							<td><fmt:formatDate  value="${ad.createTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${ad.adFlagName}</td>
							
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination"></div>
</div>
  </body>
</html>
