
function signUp() {
	var form =new FormData();
	var ret=false;
	$('#new-owner-form input').each(function() {
		if(!$(this).val()&&$(this).attr("id")!='pic'&&$(this).attr("id")!='ignore'){
			ret=true;
		}else{
			console.log("value:",$(this).attr("id"),$(this).val());
			form.append($(this).attr("id"),$(this).val());
		}
	});
	if(ret){
		alert("请勿留空");
		return;
	}

	form.append('file',$('#pic')['0'].files[0]);

	$.ajax({
		url: 'http://localhost:8101/user/register/owner',
		type: 'POST',
		data: form,
		processData : false,
		contentType : false,
		success:function(data){
			if(data==-1){
				var helper=$("#phone-hlper");
				helper.text("此号码已经被注册为租客");
				helper.css("color","red");
				$("#phoneNum").one("input",function() {
					var helper=$("#phone-hlper");
					helper.text("此号码将用于登录和看房联络");
					helper.css("color","");
				})
			}else{
				alert("success");
				history.go(-1);
			}
			
		},
		error : function(data) {
			alert("注册失败！");
		}
	})
}