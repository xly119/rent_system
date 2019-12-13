window.onload=function () {
	if(!sessionStorage.user){
		window.location.href="http://localhost:8101/rentsys/login";
	}
	curr_user=JSON.parse(sessionStorage.user);
	console.log("curr user:",curr_user);
	getSubs();
	$('#nav-subs').bind("click",function(e) {
		e.preventDefault();
		currPage=1;
		$('#house-subs-list').empty();
		getSubs();
		$('#pagebttn').css("display","");
	});
	$('#nav-events').bind("click",function(e) {
		e.preventDefault();
		currPage=1;
		$('#house-subs-list').empty();
		getEvents(function(e) {
			return e.state==0;
		});
		$('#pagebttn').css("display","");
	});
	$('#nav-arrange').bind("click",function(e) {
		e.preventDefault();
		currPage=1;
		$('#house-subs-list').empty();
		getEvents(function(e) {
			return e.state!=0;
		});
		$('#pagebttn').css("display","");
	});
	$('#nav-info').bind("click",function(e) {
		e.preventDefault();
		$('#pagebttn').css("display","none");
		$('#house-subs-list').empty();
		var form=$('#user-form').clone();
		form.css("display","");
		form.prependTo('#house-subs-list');
		getUser();

	});
	$('#log-out').bind("click",function(e) {
		e.preventDefault();
		sessionStorage.removeItem('user');
		location.replace('http://localhost:8101/rentsys/login')});

}
var curr_user;
var subsList;
var eventList;
var isSubs;
var currPage=1;
var lastPage;
var lastPageE;
var pageMax=4;
var len;
var lenE;
const HOUSE_STATE=["待缴费","未出租","已出租"];
const EVENT_STATE=["待同意","待赴约","拒绝","已完成"];


function getSubs() {
	$.post("http://localhost:8101/house/get/list",{'ownerId':curr_user.id},function(data){
		subsList=data;
		isSubs=true;
		len=data.length>pageMax?pageMax:data.length;
		lastPage=Math.ceil(data.length/pageMax);
		for (var i = 0; i <len; i++) {
			console.log(data[i]);
			var house=$('#example').clone();
			houseSetter(house,data[i]);
			house.prependTo('#house-subs-list');
		}

	})
}

function getEvents(fun) {
	$.post("http://localhost:8101/house/visitrec/get/by_owner",{'ownerId':curr_user.id},function(data) {
		data=data.filter(fun);
		eventList=data;
		isSubs=false;
		lenE=data.length>pageMax?pageMax:data.length;
		lastPageE=Math.ceil(data.length/pageMax);
		for (var i = 0; i <lenE; i++) {
			console.log(data[i]);
			var event=$('#exampleE').clone();
			eventSetter(event,data[i]);
			event.prependTo("#house-subs-list");
		}
	})
}

function getUser() {
	$.post("http://localhost:8101/user/get/owner",{'id':curr_user.id},function(user) {
		curr_user=user;
		$('#user-phonenum').val(user.phoneNum);
		$('#user-passwd').val(user.passwd);
		$('#user-addr').val(user.address);
		$('#user-name').val(user.name);
	})
	//user=JSON.parse(window.sessionStorage.getItem('user'));

	
}
function goPage(move) {
	if(isSubs)
		goPageHouse(move);
	else
		goPageEvent(move);
}

function updateUser() {
	console.log("change");
	var ret=false;
	$('#user-form input').each(function() {
		if(!$(this).val()){
			ret=true;
		}
	});
	if(ret){
		alert("请勿留空");
		return;
	}
	$.post("http://localhost:8101/user/update/ownerinfo",{
		'id':curr_user.id,
		'phoneNum':$('#user-phonenum').val(),
		'passwd':$('#user-passwd').val(),
		'name':$('#user-name').val(),
		'address':$('#user-addr').val(),
	},function (argument) {
		alert("提交成功");
	})
}
function goPageHouse(move) {
	var start;
	if(move==1&&(currPage+1)<=lastPage){
		currPage++;
		len=currPage==lastPage?subsList.length%pageMax:pageMax;
		len=len==0?pageMax:len;
	}else if(move==-1&&(currPage-1)>0){
		currPage--;
		len=pageMax;
	}else if(move==0){
		currPage=1;
		len=subsList.length>pageMax?pageMax:subsList.length;
	}else{
		return;
	}
	$('#house-subs-list').empty();
	console.log("move :",move,"currPage",currPage,"length",len);
	start=(currPage-1)*pageMax;
	for (var i =0; i <len; i++) {
		var house=$('#example').clone();
		console.log("start：",start,subsList[start+i]);
		houseSetter(house,subsList[start+i]);
		house.prependTo("#house-subs-list");
	}
	
}
function goPageEvent(move) {
	var start;
	if(move==1&&(currPage+1)<=lastPageE){
		currPage++;
		len=currPage==lastPageE?eventList.length%pageMax:pageMax;
		len=len==0?pageMax:len;
	}else if(move==-1&&(currPage-1)>0){
		currPage--;
		len=pageMax;
	}else if(move==0){
		currPage=1;
		len=eventList.length>pageMax?pageMax:eventList.length;
	}else{
		return;
	}
	console.log("move :",move,"currPage",currPage,"length",len);
	$('#house-subs-list').empty();
	start=(currPage-1)*pageMax;
	for (var i =0; i <len; i++) {
		var e=$('#exampleE').clone();
		eventSetter(e,eventList[start+i]);
		e.prependTo("#house-subs-list");
	}
	
}



function houseSetter(item,target) {
	item.attr('id',"house-item"+target.id);
	item.find("#house-pic").attr("src","/"+target.picUrl);
	item.find("#house-title").text(target.title);
	item.find("#house-desc").text("状态 · "+HOUSE_STATE[target.state]);
	var edit=item.find('#house-edit-info'); 
	edit.data("id",target.id);
	edit.bind("click", function(e) {
		e.preventDefault();
		sessionStorage.setItem("currEditHouseId", $(this).data("id"));
		location.href="http://localhost:8101/rentsys/house/edit";
	})
	var state=item.find("#house-edit-state");
	if(target.state==0){
		item.find("#house-edit").css("display","none");
		item.find("#house-sub-icon").css("display","none");
		var editIcon=item.find("#house-edit-icon");
		var payLink=editIcon.find("a");
		editIcon.css("display","");
		payLink.data("houseid",target.id);
		payLink.bind("click",function(e) {
			e.preventDefault();
            sessionStorage.setItem("houseid",$(this).data("houseid"));
			location.href="http://localhost:8101/rentsys/house/pay";
		})
	}else{
		item.find("#house-subnum").text("·"+target.subs+"人已关注");
		var del=item.find("#house-del");
		del.data("houseid",target.id);
		del.bind("click",function (e) {
			e.preventDefault();
			var r=confirm("确定删除？");
			if(r==true){
				var id=$(this).data("houseid");
				console.log("del house :",id);
				$.post("http://localhost:8101/house/del",{'id':id},
					function() {
						console.log("del house success");
						var father=$('#house-item'+id);
						father.find(".card-panel").addClass("grey lighten-3");
						father.find("#house-edit").css("display","none");
						subsList.forEach(function (item, index) {
							console.log("deleted:",item.id,id);
							if(item.id==id){
								subsList.splice(index,1);
								lastPage=Math.ceil(subsList.length/pageMax);
							}
						})
					});
			}
		})
		state.data("target-id",target.id);
		state.data("curr-state",target.state);
		state.text("状态变为:"+HOUSE_STATE[(target.state==1?2:1)])
		state.bind("click",function(e) {
			e.preventDefault();
			var newState=$(this).data("curr-state")==1?2:1;
			var targid=$(this).data("target-id");
			$('#house-item'+targid).find("#house-desc").text("状态 · "+HOUSE_STATE[newState]);
			$.post("http://localhost:8101/house/update/state",{'id':targid,'state':newState,});
			$(this).data("curr-state",newState);
			$(this).text("状态变为:"+HOUSE_STATE[(newState==1?2:1)]);
		})
	}
	item.css("display","");
}

function eventSetter(item,target) {
	var date=new Date(target.visitTime);
	item.attr('id',"event-item"+target.id);
	item.find("#event-state").text("状态："+EVENT_STATE[target.state]);
	item.find("#event-mark").attr("value",target.mark);
	item.find("#event-date").attr("value",date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
	item.find("#event-house").attr("value",target.address);
	console.log("state",target.state);
	if(!target.state){
		var agree=item.find("#event-agree");
		var refuse=item.find("#event-refuse");
		item.find(("#event-op-bttns")).css("display","");
		agree.data("father",target.id);
		refuse.data("father",target.id);
		agree.bind("click",function(e) {
			e.preventDefault();
			var fatherId=$(this).data("father");
			$.post("http://localhost:8101/house/visitrec/change_state",{id:fatherId,state:1});
			var father=$("#event-item"+fatherId);
			father.find(("#event-op-bttns")).css("display","none");
			father.find("#event-state").text("状态：已同意");
		});
		refuse.bind("click",function(e) {
			e.preventDefault();
			var fatherId=$(this).data("father");
			$.post("http://localhost:8101/house/visitrec/change_state",{id:fatherId,state:2});
			var father=$("#event-item"+fatherId);
			father.find(("#event-op-bttns")).css("display","none");
			father.find("#event-state").text("状态：拒绝");
			father.find(".card-panel").addClass("grey lighten-3");
		})
	}
	item.css("display","");

}
