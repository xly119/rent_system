var id;

window.onload=function () {
	if(!sessionStorage.user){
		window.location.href="http://localhost:8101/rentsys/login";
	}
	$('select').formSelect();
	id=sessionStorage.getItem("currEditHouseId");
	console.log(id);
	getHouseInfo();
}

function getHouseInfo() {
	$.post("http://localhost:8101/house/get",{'id':id},function(house) {
		$('#title').val(house.title);
		$('#address').val(house.address);
		$('#rent').val(house.rent);
		$('#type').val(house.type);
		$('#maxTenantNum').val(house.maxTenantNum);
		$('#ignore').attr('placeholder',house.picUrl);
	})
}
function updateHouse() {
	var ret=false;
	var form =new FormData();
	$('#new-house-form input').each(function() {
		if(!$(this).val()){
			if($(this).attr('id')!='pic'&&$(this).attr('id')!='ignore'){
				ret=true;
			}
		}else{
			console.log($(this).attr("id"),$(this).val());
			form.append($(this).attr("id"),$(this).val());
		}
	});
	if(ret){
		alert("请勿留空");
		return;
	}
	form.append('type',$('#type').val());
	form.append('id',id);
	if($('#ignore').val()){
		console.log("添加照片",$('#ignore').val());
		form.append('file',$('#pic')['0'].files[0]);
	}
	$.ajax({
		url: 'http://localhost:8101/house/update/info',
		type: 'POST',
		data: form,
		processData : false,
		contentType : false,
		success:function(){
			alert("success");
			history.go(-1);
		},
		error : function(data) {
			alert("修改失败！");
		}
	})
}