var id;
var currH;
var currO;
var subsCheck;
const HOUSE_STATE=["平房","带阳台的楼房","独立式住宅"];
var currUser;
window.onload=function () {
	if(!sessionStorage.user){
		window.location.href="http://localhost:8101/rentsys/login";
	}
	id=sessionStorage.getItem("currHouse");
	get(id);
	currUser=JSON.parse(sessionStorage.user);
}


function get(param) {
	$.post("http://localhost:8101/house/get",{'id':""+param},function(data) {
		console.log(data);
		currH=data;
		$('#house-title').text(data.title);
		$('#house-pic1').attr('src',"/"+data.picUrl);
		$('#house-pic2').attr('src',"/"+data.picUrl);
		$('#house-pic3').attr('src',"/"+data.picUrl);
		$('#house-rent').prepend(data.rent);
		console.log($('#house-rent').html());
		$('#house-address').text(data.address);
		$('#house-type').text(HOUSE_STATE[data.type]);
		$('#house-max').text("最多"+data.maxTenantNum+"人");
		$('#subs-num').text("已被"+data.subs+"人收藏");

		$.post("http://localhost:8101/user/get/owner",{'id':""+data.ownerId},function (owner) {
			console.log(owner);
			currO=owner;
			$('#house-owner-pic').attr('src',"/"+owner.picUrl);
			$('#house-owner-name').text(owner.name);
		})
		$.post("http://localhost:8101/house/subscirbe/check",{'userId':currUser.id,'houseId':""+data.id}
			,function (res) {
				console.log(res);
				subsCheck=res;
				if(res){
					var icon=$('#subs-icon');
					icon.removeClass("fa-star");
					icon.addClass("fa-check");
					icon.text("已收藏");
				}
			})

	})
	

}

function arrange() {
	var timeItem=$('#arrange-time');
	var time=timeItem.val();
	if(!time){
		timeItem.popover('show');
		timeItem.bind("input",function() {
			timeItem.popover('hide');
		})
		return;
	}
	var mark=$('#arrange-mark').val();
	console.log("form :",time,mark);
	$.post( 'http://localhost:8101/house/visitrec/request', 
	{
		'houseId':""+currH.id,
              	'visterId':currUser.id,//还没写
              	'visitTime':time,
              	'mark':mark, //还没加
              }, function(data) {
              	alert("安排上了");
              	location.reload();
              })
}

function subs() {
	var icon=$('#subs-icon');
	if(subsCheck){
		icon.removeClass("fa-check");
		icon.addClass("fa-star");
		icon.text("收藏");
		$('#subs-num').text("已被"+(--currH.subs)+"人收藏");
		$.post("http://localhost:8101/house/subscirbe/dec",{'userId':""+currUser.id,'houseId':""+currH.id});
		subsCheck=false;
	}else{
		icon.removeClass("fa-star");
		icon.addClass("fa-check");
		icon.text("已收藏");
		$('#subs-num').text("已被"+(++currH.subs)+"人收藏");
		$.post("http://localhost:8101/house/subscirbe/inc",{'userId':""+currUser.id,'houseId':""+currH.id});
		subsCheck=true;

	}
}
