window.onload=function () {
	if(!sessionStorage.user){
		window.location.href="http://localhost:8101/rentsys/login";
	}
    currUser=JSON.parse(sessionStorage.user);
    console.log("curr user:",currUser);
	getSubs();
	$('#nav-subs').bind("click",function(e) {
		e.preventDefault();
		$('#user-form').css("display","none");
		currPage=1;
		$('#house-subs-list').empty();
		getSubs();
		$('#pagebttn').css("display","");
	});
	$('#nav-events').bind("click",function(e) {
		e.preventDefault();
		$('#user-form').css("display","none");
		currPage=1;
		$('#house-subs-list').empty();
		getEvents();
		$('#pagebttn').css("display","");
	});
	$('#nav-info').bind("click",function(e) {
		e.preventDefault();
		$('#pagebttn').css("display","none");
		$('#house-subs-list').empty();
		$('#user-form').css("display","");
		getUser();
	});
		$('#log-out').bind("click",function(e) {
		e.preventDefault();
		sessionStorage.removeItem('user');
		location.replace('http://localhost:8101/rentsys/login')});
	 $('select').formSelect();
	  $('.datepicker').datepicker();

}
var currUser;
var subsList;
var eventList;
var isSubs;
var currPage=1;
var lastPage;
var lastPageE;
var pageMax=4;
var len;
var lenE;
const PIC_PATH="D://rentsys/";
const HOUSE_STATE=["平房","带阳台的楼房","独立式住宅"];
const EVENT_STATE=["待同意","待赴约","被拒绝","已完成"];


function getSubs() {
	$.post("http://localhost:8101/house/get/list/subs",{'userId':currUser.id},function(data){
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

function getEvents() {
	$.post("http://localhost:8101/house/visitrec/get/by_tenant",{'visiterId':currUser.id},function(data) {
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
	$.post("http://localhost:8101/user/get/tenant",{'id':currUser.id},function(user) {
		currUser=user;
		$('#user-phonenum').val(user.phoneNum);
		$('#user-passwd').val(user.passwd);
		$('#user-birthday').val(dateConv(user.birthday));
		$('#user-addr').val(user.address);
		$('#user-name').val(user.name);
		$('#user-sex').val(user.sex);
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
	var birthday=dateConv($('#user-birthday').val());
	console.log("date is",birthday);
	$.post("http://localhost:8101/user/update/tenantInfo",{
		'id':currUser.id,
		'phoneNum':$('#user-phonenum').val(),
		'passwd':$('#user-passwd').val(),
		'birthday':birthday,
		'address':$('#user-addr').val(),
		'name':$('#user-name').val(),
		'sex':$('#user-sex').val(),
	},function() {
		alert("提交成功");
		location.reload();
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
	item.find("#house-pic").attr("src","/"+target.picUrl);
	item.find("#house-title").text(target.title);
	//item.find("#house-location").text(target.address);
	//item.find("#house-type").text(HOUSE_STATE[target.type]);
	//item.find("#house-max").text("最多住"+target.maxTenantNum+"人");
	var desc=HOUSE_STATE[target.type]+" · "+target.maxTenantNum+"人";
	item.find("#house-desc").text(desc);
	item.find("#house-rent").text(target.rent);
	var link=item.find("a");
	//item.find("a").attr("href",INFO_PAGE+"id="+target.id);
	link.data("id", target.id);
	link.bind("click", function(e) {
		e.preventDefault();
		sessionStorage.setItem("currHouse", $(this).data("id"));
		location.href="http://localhost:8101/rentsys/house/info";
	})
	item.css("display","");
}

function eventSetter(item,target) {
	var date=new Date(target.visitTime);
	item.find("#event-state").text("状态："+EVENT_STATE[target.state]);
	item.find("#event-mark").attr("value",target.mark);
	item.find("#event-date").attr("value",date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
	item.find("#event-house").attr("value",target.address);
	if(target.state==2){
		item.find("#event-card").addClass("grey lighten-3");
	}else{
		item.find("#event-card").removeClass("grey lighten-3");
	}
			item.css("display","");

		}

		function dateConv(date) {
		 date=new Date(date);
		var m=(date.getMonth()<10?"0":"")+(date.getMonth()+1);
		var d=(date.getDate()<10?"0":"")+date.getDate();
		var after=date.getFullYear()+"-"+m+"-"+d;
		console.log("after conve",after);
		return after;
		}