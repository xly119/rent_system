window.onload=function() {
 $('select').formSelect();
 $('.datepicker').datepicker();

}
function signUp() {
	var form =new FormData();
  var ret=false;
  $('#new-tenant-form input').each(function() {
    if(!$(this).val()&&$(this).attr("id")!='pic'&&$(this).attr("id")!='ignore'){
     ret=true;
   }else{
    console.log($(this).attr("id"),$(this).val());
    form.append($(this).attr("id"),$(this).val());
  }
});
  if(ret){
    alert("请勿留空");
    return;
  }

  form.append('sex',$('#sex').val());
  form.append('file',$('#pic')['0'].files[0]);
  form.set('birthday',dateConv($("#birthday").val()));
  $.ajax({
    url: 'http://localhost:8101/user/register/tenant',
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

function dateConv(date) {
 date=new Date(date);
 var m=(date.getMonth()<10?"0":"")+(date.getMonth()+1);
 var d=(date.getDate()<10?"0":"")+date.getDate();
 var after=date.getFullYear()+"-"+m+"-"+d;
 console.log("after conve",after);
 return after;
}