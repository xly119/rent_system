
window.onload=function () {
	$('select').formSelect();
	$('.modal').modal();
	$('#login-bttn').bind("click",function(e) {
		e.preventDefault();
		$("#login-modal").modal("open");
	})
}
var userType;

function login() {
	console.log("curr :",sessionStorage.user);
	if(sessionStorage.user){
		alert("请勿重复登陆哦");
		return;
	}
	userType=$('#user-type').val()==0?"owner":"tenant";
	var passwd=$('#user-passwd').val();
	var phoneNum=$("#user-phone").val();
	if(!passwd||!phoneNum){
		alert("请填写完整");
	}
	console.log("user type",userType);
	$.post(
		"http://localhost:8101/user/login/"+userType,
		{
			'passwd':passwd,
			'phoneNum':phoneNum,
		},function (data) {
			if(data){
				console.log("user recieve",data);
				sessionStorage.setItem("user",JSON.stringify(data));
				var newUrl=userType=="owner"?"http://localhost:8101/rentsys/main/owner"
						:"http://localhost:8101/rentsys/recommend";
				location.href=newUrl;
			}else{
				$("#passwdNote").css("display","");
				$('#user-passwd').one("input",function() {
					$("#passwdNote").css("display","none");
				})
			}
		}
		)
	
}