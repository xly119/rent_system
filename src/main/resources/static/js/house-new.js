window.onload=function () {
	if(!sessionStorage.user){
		window.location.href="http://localhost:8101/rentsys/login";
	}
	$('select').formSelect();
	currUser=JSON.parse(sessionStorage.user);
}
var currUser;

function newHouse() {
	var ret=false;
	var form =new FormData();
	$('#new-house-form input').each(function() {
		if(!$(this).val()){
			ret=true;
		}else{
			form.append($(this).attr("id"),$(this).val());
		}
	});
	if(ret){
		alert("请勿留空");
		return;
	}
	form.append('type',$('#type').val());
	form.append('file',$('#pic')['0'].files[0]);
	form.append('ownerId',currUser.id);
	    $.ajax({
          url: 'http://localhost:8101/house/add',
          type: 'POST',
          data: form,
          processData : false,
          contentType : false,
          success:function(){
          	alert("success");
          	history.go(-1);
       },
          error : function(data) {
                    alert("新建失败！");
                }
            })
}