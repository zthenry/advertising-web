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
		var adIdArray = new Array();
		
		$(function(){
			var selectedAdIds = eval("("+'${adIdList}'+")");
			$.each(selectedAdIds,function(i){
				adIdArray.push(this);
			});
		});
		function change(obj,adId,adName,adWord){
			if(obj.checked){
				adIdArray.push(adId);
			}else{
				adIdArray.remove(adId);
			}
			parent.addAdvertising(adId,adName,adWord,obj.checked);
			var selected = ""
			//获取最新的选择的广告id并设置
			for(var i=0;i<adIdArray.length;i++){
				if(i==0){
					selected=adIdArray[i];
				}else{
					selected=selected+","+adIdArray[i];
				}
			}
			$("#adIds").val(selected);
		}
		
		//返回指定元素的索引
		Array.prototype.indexOf = function(val) {              
    		for (var i = 0; i < this.length; i++) {  
        		if (this[i] == val) return i;  
    		}  
    		return -1;  
		};
		//使用splice删除元素
		Array.prototype.remove = function(val) {  
    		var index = this.indexOf(val);  
    		if (index > -1) {  
        		this.splice(index, 1);  
    		}  
		};
		
		function nextPage(page){
    		$("#curPage").val(page);
    		$("#search").submit();
    	}
	</script>

  </head>
  
  <body>
    <div class="content">
       
        <div class="container-fluid">
            <div class="row-fluid">
					<div class="search-well">
						<form class="form-inline"
							action="${contextPath}/ad/adListForConf" method="get" id="search">
							广告名称：<input type="text" name="name" class="input-small" value="${name}"/>
							<input type="hidden" name="curPage" id="curPage" value="">
							<input id="adIds" name="adIds" type="hidden"  value="${adIds}"/>
							<button class="btn" type="submit">
								<i class="icon-search"></i> 搜索
							</button>
						</form>
					</div>
					
			 </div>
		</div>
			<div class="well">
				<table id="sample-table-1" class="table table-bordered table-hover">
					<tr>
						<td class="block_header"><input id="check_all" type="checkbox" /></td>
						<td width="10%" align="center" bgcolor="#EEEEEE">
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
						<td width="20%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">安卓url</span>
						</td>
						<td width="20%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">IOS_url</span>
						</td>
						<td width="15%" align="center" bgcolor="#EEEEEE">
							<span class="tabletitle">创建时间</span>
						</td>
						
					</tr>
					<c:forEach var="ad" items="${adList}">
						<tr>
							<c:if test="${ad.checked==1}">
								<td><input onpropertychange="change(this,${ad.id},'${ad.adName}','${ad.adWord}')" onchange="change(this,${ad.id},'${ad.adName}','${ad.adWord}')" id="check_${ad.id}" value="${ad.id}" type="checkbox" checked="checked"/></td>
							</c:if>
							<c:if test="${ad.checked==0}">
								<td><input onpropertychange="change(this,${ad.id},'${ad.adName}','${ad.adWord}')" onchange="change(this,${ad.id},'${ad.adName}','${ad.adWord}')" id="check_${ad.id}" value="${ad.id}" type="checkbox" /></td>
							</c:if>
							<td>${ad.id}</td>
							<td>${ad.adName}</td>
							<td>${ad.adWord}</td>
							<td>${ad.picId}</td>
							<td>${ad.androidUrl}</td>
							<td>${ad.iosUrl}</td>
							<td><fmt:formatDate value="${ad.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							
							
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
