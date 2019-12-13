window.onload=function() {
	if(!sessionStorage.user){
		window.location.href="http://localhost:8101/rentsys/login";
	}
	houseid=sessionStorage.getItem("houseid");
	console.log("paying house:",houseid);
}

var houseid;

function payUpdate() {
		$.post("http://localhost:8101/house/update/state",
			{
				'id':houseid,
				'state':1,
			},
			function () {
				history.back();
			})
}