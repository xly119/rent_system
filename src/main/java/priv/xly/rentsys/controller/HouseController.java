package priv.xly.rentsys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.model.house.House;
import priv.xly.rentsys.model.house.HouseVisitRecord;
import priv.xly.rentsys.service.HouseService;

@RestController
@RequestMapping("/house")
@CrossOrigin(origins = "*")
public class HouseController {
	
	@Autowired
	private HouseService houseService;
	
	@RequestMapping("/add")
	public Object insertHouse(@RequestParam Map<String, String> params,
							  @RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		return houseService.insertHouse(file, params);
	}

	@RequestMapping("/visitrec/request")
	public Object insertVisitRecord(@RequestParam(value = "houseId") int houseId,
									@RequestParam(value = "visterId") int visterId,
									@RequestParam(value = "visitTime") String visitTime,
									@RequestParam(value = "mark") String mark) throws Exception{
		return houseService.insertVistRecord(houseId, visterId, visitTime,mark);
	}

	@RequestMapping("/visitrec/change_state")
	public void changeVisitState(@RequestParam(value = "state") int state,
			 					 @RequestParam(value = "id") int id) {
		houseService.updateVisitState(state, id);
	}
	
	@RequestMapping("/visitrec/get/by_owner")
	public List<HouseVisitRecord> getVisitsByOwner(@RequestParam(value = "ownerId") int ownerId){
		return houseService.getVisitListByOwner(ownerId);
	}
	
	@RequestMapping("/visitrec/get/by_tenant")
	public List<HouseVisitRecord> getVisitsByTenant(@RequestParam(value = "visiterId") int visiterId){
		return houseService.getVisitListByVisiter(visiterId);
	}
	
	@RequestMapping("/visitrec/get/by_house")
	public List<HouseVisitRecord> getVisitsByHouse(@RequestParam(value = "houseId") int houseId){
		return houseService.getVisitList(houseId);
	}

	@RequestMapping("/subscirbe/inc")
	public void subscribe(@RequestParam(value = "houseId") int houseId,
						  @RequestParam(value = "userId") int userId) {
		houseService.insertSubs(houseId, userId);
	}
	
	@RequestMapping("/subscirbe/dec")
	public void unsubscribe(@RequestParam(value = "houseId") int houseId,
							@RequestParam(value = "userId") int userId) {
		houseService.delSubs(houseId, userId);
	}
	
	@RequestMapping("/subscirbe/check")
	public Object subscribeCheck(@RequestParam(value = "houseId") int houseId,
							     @RequestParam(value = "userId") int userId) {
		return houseService.subsCheck(houseId, userId);
	}
	
	@RequestMapping("/update/info")
	public void updateHouseInfo(@RequestParam Map<String, String> params,
							  	@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		houseService.updateInfo(file, params);
	}
	
	@RequestMapping("/update/state")
	public void updateHouseState(@RequestParam(value = "state") int state,
								 @RequestParam(value = "id") int id) {
		houseService.updateState(id, state);
	}
	
	@RequestMapping("/del")
	public void deleteHouse(@RequestParam(value = "id") int id) {
		houseService.deleteHouse(id);
	}
	
	@RequestMapping("/get")
	public House getHouse(@RequestParam(value = "id") int id) {
		return houseService.getHouseById(id);
	}
	
	@RequestMapping("/get/list")
	public List<House> getHousesByOwner(@RequestParam(value = "ownerId") int ownerId) {
		return houseService.getHouses(ownerId);
	}
	
	@RequestMapping("/get/list/available")
	public List<House> getHouseAvailable() {
		return houseService.getHouseAvailable();
	}
	
	@RequestMapping("/get/list/subs")
	public List<House> getHouseSubscribed(@RequestParam(value = "userId") int userId){
		return houseService.getHouseSubscribed(userId);
	}
}
