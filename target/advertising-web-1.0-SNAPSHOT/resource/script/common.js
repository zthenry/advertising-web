var tag = false;
function confirm0(id){
	console.log(id);
	if(tag){ return true; }
	Boxy.confirm('您确认删除吗?', function(){tag = true; $(id).click();}, null);
	return tag;
}