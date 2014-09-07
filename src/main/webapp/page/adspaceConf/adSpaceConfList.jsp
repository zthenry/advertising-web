<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<jsp:include page="/page/base.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>APP列表</title>
<script src="${contextPath}/resource/script/jquery.pagination.js" type="text/javascript"></script>
<script type="text/javascript">
	var pageSign = false;
	$(function(){
	    $("#confType").val('${confType}')
		
	});
	
	
    
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

    function getAdspaceNum(){
    	var appClientId = $("#appClientId").val();
    	var ver = $("#appClientVersion").val();
		if(appClientId==null || appClientId==undefined || appClientId==0){
			alert("请选择广告平台");
			$("#appClientVersion").html("");
			return false;
		}
		$.ajax({
      		type:"get",
      		url:"${contextPath}/adspace/getAdspaceNum/"+appClientId,
      		data:{"version":ver},
      		dataType:"json",
      		success:function(data){
      		    $.each(data.adSpaceList,function(){
      		    	$("#adSpaceNumber").append('<option value="'+this.id+'">'+this.adSpaceNumber+'</option>');
      		    });
      		}
      	});
    }
    
    function changConfStatus(adSpaceConfId,target){
    	var tip ="";
    	if(target==0){
    		tip = "禁用广告位配置后,该配置就会失效,确定禁用？"
    		
    	}else if(target==1){
    		tip = "启用该广告位配置后,在指定的时间内就会生效,确定启用？"
    	}
    	var result = confirm(tip);
    	if(!result){
    		return false;
    	}
    	$.ajax({
      		type:"get",
      		url:"${contextPath}/adspace/changAdSpaceConfStatus",
      		data:{"adSpaceConfId":adSpaceConfId,"target":target},
      		dataType:"json",
      		success:function(data){
      		   alert(data.msg);
      		   $("#search").submit();
      		}
      	});
    	
    }
    //刷新缓存
    function refreshRedis(adSpaceConfId){
    	var tip = "确定将数据刷新到缓存中？"
    	
    	var result = confirm(tip);
    	if(!result){
    		return false;
    	}
    	$.ajax({
      		type:"get",
      		url:"${contextPath}/adspace/refreshRedis",
      		data:{"adSpaceConfId":adSpaceConfId},
      		dataType:"json",
      		success:function(data){
      		   alert(data.msg);
      		   $("#search").submit();
      		}
      	});
    }
    
    //获取缓存数据
    function getDataFromRedisById(adSpaceConfId){
    	$.ajax({
      		type:"get",
      		url:"${contextPath}/adspace/getDataFromRedis",
      		data:{"adSpaceConfId":adSpaceConfId},
      		dataType:"json",
      		success:function(data){
      		   alert(data.result);
      		}
      	});
    }
    
    function nextPage(page){
    	$("#curPage").val(page);
    	$("#search").submit();
    }
</script>
</head>

<body>
<div class="content">
        
        <ul class="breadcrumb">
				<li>
					<a href="javascript:history.back(-1);">返回上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li>
					<a href="${contextPath}/adspace/listIndex">广告位管理</a>
					<span class="divider">/</span>
				</li>
				<li class="active">
					广告位配置信息管理
				</li>
			</ul>
        <div class="container-fluid">
            <div class="row-fluid">
					<div class="search-well">
						<form class="form-inline"
							action="${contextPath}/adspace/conf" method="get" id="search">
							<input type="hidden" name="curPage" id="curPage" value="">
							<input type="hidden" value="${id}"  name="id" />
							<input type="hidden" value="${appClientId}"  name="appClientId" id="appClientId"/>
							平台名称:
							<input type="text" value="${appClientName}"  id="appClientName" readonly="readonly"/>
							平台版本：
							<input type="text" value="${version}" name="version" id="version" readonly="readonly" />
							
							广告位编号：
							<input type="text" value="${adspaceNum}" name="adspaceNum" id="adspaceNum" readonly="readonly" />
							
							配置时效类型：
							<select name="confType" id="confType" class="input-small" >
								<option value="0" selected="selected">
									全部
								</option>
								
								<option value="1">
									当前生效
								</option>
								<option value="2">
									过期
								</option>
								<option value="3">
									预配置--启用
								</option>
								<option value="4">
									预配置--禁用
								</option>
							</select>
							
							<button class="btn" type="submit">
								<i class="icon-search"></i> 搜索
							</button>
						</form>
						<c:if test="${status==1}">
							<div class="btn-toolbar">
								<button class="btn btn-primary"
									onclick="location.href='${contextPath }/adspace/toAddAdSpaceConf?adspaceId=${id}'">
									<i class="icon-plus"></i> 配置广告
								</button>
								<div class="btn-group">
								</div>
							</div>
						</c:if>
					</div>
					
			 </div>
		</div>
			<div class="well">
				<table id="sample-table-1" class="table table-bordered table-hover">
					<tr>
						<td width="10%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">配置ID</span>
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">广告位id</span>
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">创建时间</span>
						</td>
						<td width="15%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">开始生效时间</span>
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">结束时间</span>
						</td>
						<td width="10%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">状态</span>
						</td>
						<td width="30%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">操作</span>
						</td>
					</tr>
					<c:forEach var="conf" items="${adSpaceConfs}" varStatus="status">
						<tr>
							<td>${conf.id}</td>
							<td>${conf.adSpaceId}</td>
							<td><fmt:formatDate value="${conf.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
							<td><fmt:formatDate value="${conf.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
							<td><fmt:formatDate value="${conf.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
							<td>
								<c:if test="${conf.status==0}">禁用</c:if>
								<c:if test="${conf.status==1}">启用</c:if>
							</td>
							<td>
								<!-- 所有类别都能查看 -->
								<a href="${contextPath}/adspace/adSpaceConf/adList/${conf.id}/${appClientName}/${version}/${adspaceNum}">查看</a>
								<!-- 预配置的可以删除 修改，启用，禁用-->
								<c:if test="${conf.currentStatus==21 || conf.currentStatus==20}">
									<a href="">删除</a>
									<a href="${contextPath}/adspace/toEditAdSpaceConf?adspaceConfId=${conf.id}">修改</a>
									<c:if test="${conf.status==1}"><a href="javascript:changConfStatus(${conf.id},0)">禁用</a></c:if>
									<c:if test="${conf.status==0}"><a href="javascript:changConfStatus(${conf.id},1)">启用</a></c:if>
								</c:if>
								
								
								<!-- 当前生效的 且禁用的-->
								<c:if test="${conf.currentStatus==1 && conf.status==0}">
									
								</c:if>
								<!-- 当前生效的 且启用的-->
								<c:if test="${conf.currentStatus==1 && conf.status==1}">
									<a href="${contextPath}/adspace/toEditAdSpaceConf?adspaceConfId=${conf.id}">修改</a>
									<a href="javascript:refreshRedis(${conf.id})">刷新缓存</a>
									<a href="javascript:getDataFromRedisById(${conf.id})">查看缓存数据</a>
								</c:if>
								
								
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="pagination"></div>
			</div>
			
</div>
<script type='text/javascript'>
        var options = {
            currentPage: '${page.curPage}',
            totalPages: '${page.pageCount}',
	    	numberOfPages:'${page.pageSize}',
	    	alignment:'left',
	    	pageUrl: function(type, page, current){
				return "javascript:nextPage("+page+")";  
            }
        }
        
        $('.pagination').bootstrapPaginator(options);
    </script><span style="font-family:'sans serif, tahoma, verdana, helvetica';"><span style="white-space:normal;"> </span></span>
</body>
</html>