<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include page="/page/base.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<c:if test="${adspaceConfId>0}"><title>修改广告位配置</title></c:if>
		<c:if test="${adspaceConfId==0}"><title>新增广告位配置</title></c:if>	
		<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		
		<script type="text/javascript">
			//是否支持广告组
			var supportAdList = '${supportAdlist}';
			
			//修改广告位的配置时,当前生效的广告不能修改开始时间
			var currentExpire = '${currentExpire}';
			
			//记录选中的广告id
			var checkedAdIds="";
			//添加页面
			var addflag=true;
			//添加产品的模板
			var adTemplate = "<tr><td><input type='hidden' name='adId' value={adId} />{adName}</td><td>{adWord}</td><td><input type='text' id='adFlagName'></td>"+
								"<td><span><a onclick='deleteAdvertising({adId})' style='color: #FFCC00;' href='#'>删除</a></span></td></tr>"
			$(function(){
				if(currentExpire==1){
					$("#startTime").attr("readonly","readonly");
				}else{
//					$("#startTime").datetimepicker({
//						dateFormat: "yy-mm-dd",
//     					timeFormat: "hh:mm:ss" 
//            		});
            		$("#startTime").datetimepicker({
              			changeYear : true,
              			showSecond: true,
              			regional : "zh-CN",
              			dateFormat : "yy-mm-dd",
              			timeFormat: "HH:mm:ss"
            		});
				}
				
				$("#effDate").datetimepicker({
              changeYear : true,
              showSecond: true,
              regional : "zh-CN",
              dateFormat : "yy-mm-dd",
              timeFormat: "HH:mm:ss"
            });
            $("#endTime").datetimepicker({
              changeYear : true,
              showSecond: true,
              regional : "zh-CN",
              dateFormat : "yy-mm-dd",
              timeFormat: "HH:mm:ss"
            });
//            	$("#endTime").datetimepicker({
//					dateFormat: "yy-mm-dd",
//     				timeFormat: "hh:mm:ss" 
//            	});
            	
            	$("#advertisingPage").dialog({
            		 	modal:true,
                        autoOpen:false,
                        width: 1500,
                        height:516,
                        resizable:false,
                        position:"center",
                        title:"关联广告",
                        buttons:
                        {
                            "关闭":function(){$(this).dialog("close");}
                        }
          		});
          		if('${adspaceConfId}'==0){
          			$("#selectedAds").attr("style","display:none");
          		}else if('${adspaceConfId}'>0){
          			//修改页面标记
          			addflag=false;
          			checkedAdIds='${adIds}';
          			$("#showAdList").attr("href","${contextPath}/ad/adListForConf?name=&adIds="+checkedAdIds);
          			//设置form表单的adIds
          			$("#adIds").val(checkedAdIds);
          			
          		}
			});
			
			//保存配置
			function save(){
				var result = check();
				if(!result){
					return false;
				}
				if(result && addflag){
					//添加
				    //$('#adspaceFormId').submit();
				    var ok = confirm("确定新增该配置?")
				    if(!ok){
				    	return false;
				    }
					 
				}else if(result && !addflag){
					//修改
					var ok = confirm("确定修改该配置?")
				    if(!ok){
				    	return false;
				    }
				}
				setAdIdsBeforeSave();
				var formData = $('#adspaceFormId').serialize();
	  			$.ajax({ 
	  					type : "POST", 
	  		        	url  : "${contextPath}/adspace/addAdSpaceConf",  
	  		        	cache : false, 
	  		        	data : formData,
	  		        	dataType:"json",
	  		        	success : function(data){
	  		        		alert(data.msg);
	  		        		if(data.success){
	  		        			window.location.href="${contextPath}/adspace/listIndex";
	  		        		}
	  		        		
	  		        	}, 
	  		        	error : function(data,status){
	  		        		alert('未能提交请求');
	  		        	} 
	  		 	});
			}
			function check(){
				var currentSelectedAdIds = $("#adIds").val();
				var adIdArrays = currentSelectedAdIds.split(",");
				if(adIdArrays=="" || adIdArrays.length==0){
					alert("请关联广告后提交");
					return false;
				}
				if(supportAdList==0 && adIdArrays.length>1){
					alert("该广告位当前不支持广告组,只能关联一个广告!");
					return false;
				}
				
				//时间校验
				var startTime=$("#startTime").val();
				var endTime=$("#endTime").val();
				if(startTime=="" || endTime==""){
					alert("时间不能为空!");
					return false;
				}else{
					var result = compareTime(startTime,endTime);
					if(!result){
						alert("开始时间不能大于等于结束时间!");
						return false;
					}
					
				}
				return true;
			}
			function compareTime(start,end){
				return ((new Date(start.replace(/-/g,"\/")))<(new Date(end.replace(/-/g,"\/"))));
			}
			//新增广告
			function addAdvertising(adId,adName,adWord,checked){
				if(checked){
					//选中
					/*首先遍历展示框中的广告,判断是够有重复*/
					var duplicate = false;
					
					$(":hidden","#selectedAdTable").each(function(){
						
						if($(this).val()==adId){
							duplicate = true;
						}
					})
					if(!duplicate)
					{
						
						$("#adspaceFormId").attr("action","${contextPath}/adspace/addAdspaceConf");
						createAdTr(adId,adName,adWord);
						
						//重新统计选取的广告id
						checkedAdIds="";
						$(":hidden","#selectedAdTable").each(function(){
							if(checkedAdIds==""){
								checkedAdIds=$(this).val();
							}else{
								checkedAdIds=checkedAdIds+","+$(this).val();
							}
							
						});
						//设置form表单的adIds
						$("#adIds").val(checkedAdIds);
						$("#showAdList").attr("href","${contextPath}/ad/adListForConf?name=&adIds="+checkedAdIds);
					}
				}else{
					//取消选中
					deleteAdvertising(adId);
				}
			}
			
			function createAdTr(adId,adName,adWord){
				var ad= adTemplate.replace("{adName}",adName);
				var ad = ad.replace("{adId}",adId);
				var ad = ad.replace("{adId}",adId);
				var ad = ad.replace("{adWord}",adWord);
				$(ad).appendTo("#selectedAdTable");
			}
			
			function deleteAdvertising(e,o){
				$(o).parent().parent().parent().remove();
			}
			
			
			function deleteAdvertising(adId){
				$(":input[name='adId']").filter("[value='"+adId+"']").parent().parent().remove();
				//重新统计选取的广告id
				checkedAdIds="";
				$(":hidden","#selectedAdTable").each(function(){
					if(checkedAdIds==""){
						checkedAdIds=$(this).val();
					}else{
						checkedAdIds=checkedAdIds+","+$(this).val();
					}
				});
				//设置form表单的adIds
				$("#adIds").val(checkedAdIds);
				$("#showAdList").attr("href","${contextPath}/ad/adListForConf?name=&adIds="+checkedAdIds);
			}
			
			function showAdvertisingPage(){
				
				$("#advertisingPage").dialog("open");
				$("#selectedAds").attr("style","block");
			}
			
			function setAdIdsBeforeSave(){
				var checkedAdIds="";
				$("#selectedAdTable").find("tr").each(function(i){
					if(i>0){
						var arrtd= $(this).children();
						var td0 = arrtd.eq(0).find("input").val();
						var td2 = arrtd.eq(2).find("input").val();
						if(checkedAdIds==""){
							checkedAdIds=td0+"_"+td2;
						}else{
							checkedAdIds=checkedAdIds+","+td0+"_"+td2;
						}
					}
					
				});
				$("#adIds").val(checkedAdIds);
			}
			
			
		</script>

	</head>

	<body>
		<div class="content">

			<div class="header">

				<h1 class="page-title">
				<c:if test="${adspaceConfId>0}">修改广告位配置</c:if>
				<c:if test="${adspaceConfId==0}">新增广告位配置</c:if>	
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
					<c:if test="${adspaceConfId>0}">修改广告位配置</c:if>
					<c:if test="${adspaceConfId==0}">新增广告位配置</c:if>	
				</li>
			</ul>

			<div class="container-fluid">
				<div class="row-fluid">

					<div class="btn-toolbar">
						<button class="btn btn-primary" onclick="save()">
							<i class="icon-save"></i> 保存
						</button>
						<div class="btn-group">
						</div>
					</div>
					<div class="well">
						<div id="myTabContent" class="tab-content">
							<div class="tab-pane active in" id="home">
								<form id="adspaceFormId" action="${contextPath}/adspace/addAdSpaceConf" method="post">
									<input id="adSpaceId" name="adSpaceId" type="hidden"  value="${adspaceId}"/>
									<input id="adIds" name="adIds" type="hidden"  value="${adIds}"/>
									<input id="id" name="id" type="hidden"  value="${adspaceConfId}"/>
									<label>
										生效日期
									</label>
									<input id="startTime" name="startTime" type="text" class="input-xlarge" value="<fmt:formatDate value="${adSpaceConf.startTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date"/>"/>
									<label>
										失效日期
									</label>
									<input id="endTime" name="endTime" type="text" class="input-xlarge" value="<fmt:formatDate value="${adSpaceConf.endTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date"/>"/>
									<label>
									<label>
                                                                                                          是否启用该配置信息
                                    </label>
									<select name="status" id="status" class="input-xlarge">
										<c:if test="${adspaceConfId>0}">
												<c:if test="${currentExpire==1}">
													<option value="1" selected="selected">
														启用
													</option>
												</c:if>
												<c:if test="${currentExpire!=1}">
													<c:if test="${adSpaceConf.status==1}">
													<option value="1" selected="selected">
														启用
													</option>
													<option value="0">
														禁用
													</option>
												</c:if>
												<c:if test="${adSpaceConf.status==0}">
													<option value="1">
														启用
													</option>
													<option value="0" selected="selected">
														禁用
													</option>
												</c:if>
												</c:if>
												
										</c:if>
										<c:if test="${adspaceConfId==0}">
												<option value="1" selected="selected">
													启用
												</option>
												<option value="0">
													禁用
												</option>

										</c:if>
									</select>

								</form>
								<label><a onclick= "showAdvertisingPage()"  href ="${contextPath}/ad/adListForConf?name=&curPage=1" target = "adPageFrame" id="showAdList"><strong> 关联广告 </strong></a></label>
								<br><br>
								
							</div>
							
						</div>

					</div>
					
					<div class="well" id="selectedAds" >
						<span>关联的广告</span>
						<table id="selectedAdTable"
							class="table table-bordered table-hover">
							<tr>
								<th width="10%" align="center" bgcolor="#EEEEEE">
									<span class="tabletitle">广告名称</span>
								</th>
								<th width="10%" align="center" bgcolor="#EEEEEE">
									<span class="tabletitle">广告语</span>
								</th>
								<th width="10%" align="center" bgcolor="#EEEEEE">
									<span class="tabletitle">标记名</span>
								</th>
								<th width="10%" align="center" bgcolor="#EEEEEE">
									<span class="tabletitle">操作</span>
								</th>
							</tr>
							<c:forEach var="ad" items="${advertisings}">
								<tr>
									<td>
										<input type="hidden" value="${ad.id}" name="adId"/>
										${ad.adName}
									</td>
									<td>
										${ad.adWord}
									</td>
									<td>
										<input type="text" value="${ad.adFlagName}" id="adFlagName">  
									</td>
									<td>
										<a href="#">删除</a>
									</td>

								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div id="advertisingPage"><iframe name="adPageFrame" scrolling=auto width="100%" height="400px" frameborder=0 ></iframe></div>
		<div id="selectedAdvertising"></div>
		
	</body>
</html>



