window.onload=function () {
	if(!sessionStorage.user){
		window.location.href="http://localhost:8101/rentsys/login";
	}
	getHouseList();
	$('select').formSelect();
	$('#log-out').bind("click",function(e) {
		e.preventDefault();
		sessionStorage.removeItem('user');
		location.replace('http://localhost:8101/rentsys/login')})
}
const HOUSE_STATE=["平房","带阳台的楼房","独立式住宅"];
const PIC_PATH="D://rentsys/";
var pageMax=5;
var currPage=1;
var houseList=[];
var houseListBackup;
var lastPage=0;
var addr;
var high;
var low;
var max;
var typ;

function getHouseList() {
	$.post("http://localhost:8101/house//get/list/available",{},function(data,status) {
		houseList=data;
		houseListBackup=data;
		console.log(data);
		var len=data.length>pageMax?pageMax:data.length;
		lastPage=Math.ceil(data.length/pageMax);
		for (var i = 0; i <len; i++) {

			var house=$('#example').clone();
			houseSetter(house,data[i]);
			house.attr("id","house"+i);
			house.appendTo("#house-list");
		}
	})


}
function houseSetter(item,target) {
	item.data("houseId",target.id);
	item.find("img").attr("src","/"+target.picUrl);
	var houseTitle=target.title.length>14?target.title.substring(0,13)+"..":target.title;
	var houseAddr=target.address.length>16?target.address.substring(0,16):target.address;
	item.find("#house-name").text(houseTitle);
	item.find("#house-addr").text(houseAddr);
			/**item.find(".house-desc1").text("最多住"+target.maxTenantNum+"人");
			item.find(".house-desc2").text(HOUSE_STATE[target.type]);
			item.find(".house-rent").text(target.rent);**/
			var desc=target.maxTenantNum+"人 · "+HOUSE_STATE[target.type];
			item.find("#house-desc").text(desc);
			item.find("#house-rent").text(target.rent);
			var link=item.find("a");
			link.data("id", target.id);
			item.find("a").bind("click", function(e) {
				e.preventDefault();
				sessionStorage.setItem("currHouse", $(this).data("id"));
				location.href="http://localhost:8101/rentsys/house/info";
			})            
			item.css("display","");
		}


		function goPage(move) {
			var start;
			if(move==1&&(currPage+1)<=lastPage){
				currPage++;
				len=currPage==lastPage?houseList.length%pageMax:pageMax;
				len=len==0?pageMax:len;
			}else if(move==-1&&(currPage-1)>0){
				currPage--;
				len=pageMax;
			}else if(move==0){
				currPage=1;
				len=houseList.length>pageMax?pageMax:houseList.length;
			}else{
				return;
			}
			$('#house-list').empty();
			start=(currPage-1)*pageMax;
			console.log("move",move,"currPage",currPage,"len",len,"start",start,"lastPage",lastPage);
			for (var i =0; i <len; i++) {
				var house=$('#example').clone();
				houseSetter(house,houseList[start+i],i);
				house.appendTo("#house-list");
			}

		}
		function houseFilter(house) {
			console.log("条件",addr,low,high,max,typ);
			var ifaddr=house.address.indexOf(addr) != -1 
			var iflow=house.rent>=low;
			var ifhigh=house.rent<=high;
			var iftyp=typ==-1?true:house.type==typ;
			var ifmax=house.maxTenantNum>=max;
			console.log("curr house",house,ifaddr,iflow,ifhigh,ifmax,iftyp);
			return ifaddr&&iflow&&ifhigh&&iftyp&&ifmax;
		}

		function getHouseFiltered() {
			addr=$("#address").val();
			addr=addr?addr:"";
			high=$("#rent-high").val();
			high=high?high:Number.MAX_SAFE_INTEGER;
			low=$("#rent-low").val();
			low=low?low:0;
			max=$("#maxTenantNum").val();
			max=max?max:0;
			typ=$("#type").val();
			typ=typ?typ:-1;
			houseList=houseListBackup;
			houseList=houseList.filter(houseFilter);
			lastPage=Math.ceil(houseList.length/pageMax);
			goPage(0);
		}
		function sort(up) {

			houseList.sort(function(a,b) {
				return up?a.rent-b.rent:b.rent-a.rent;
			})
			goPage(0);
	// body...
}

function randomList(){
	houseList.sort(() => Math.random() - 0.5);
	goPage(0);
}

