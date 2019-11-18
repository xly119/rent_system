package priv.xly.rentsys.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.model.house.House;
import priv.xly.rentsys.model.house.HouseVisitRecord;
import priv.xly.rentsys.service.HouseService;

@RestController
@RequestMapping("")
public class HouseController {
	
	@Autowired
	private HouseService houseService;
	
	@RequestMapping("/add/house")
	public Object insertHouse(@RequestParam Map<String, String> params,
							  @RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		return houseService.insertHouse(file, params);
	}

	@RequestMapping("/visitrec/request")
	public Object insertVisitRecord(@RequestParam(value = "ownerId") int ownerId,
									@RequestParam(value = "houseId") int houseId,
									@RequestParam(value = "visterId") int visterId,
									@RequestParam(value = "visitTime") Date visitTime){
		return houseService.insertVistRecord(ownerId,houseId, visterId, visitTime);
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
	
	@RequestMapping("/visitrec/get/by_house")
	public List<HouseVisitRecord> getVisitsByHouse(@RequestParam(value = "houseId") int houseId){
		return houseService.getVisitList(houseId);
	}
	
	@RequestMapping("/add/leaserec")
	public Object insertLeaseRecord(@RequestParam(value = "houseId") int houseId,
									@RequestParam(value = "tenantId") int tenantId,
									@RequestParam(value = "startDate") Date startDate,
									@RequestParam(value = "endDate") Date endDate) {
		return houseService.insertLeaseRecord(houseId, tenantId, startDate, endDate);
		
	}
	
	@RequestMapping("/update/house/info")
	public void updateHouseInfo(@RequestParam Map<String, String> params,
							  	@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		houseService.updateInfo(file, params);
	}
	
	@RequestMapping("/update/house/state")
	public void updateHouseState(@RequestParam(value = "state") int state,
								 @RequestParam(value = "id") int id) {
		houseService.updateState(id, state);
	}
	
	@RequestMapping("/del/house")
	public void deleteHouse(@RequestParam(value = "id") int id) {
		houseService.deleteHouse(id);
	}
	
	@RequestMapping("/get/house")
	public House getHouse(@RequestParam(value = "id") int id) {
		return houseService.getHouseById(id);
	}
	
	@RequestMapping("/get/houselist")
	public List<House> getHousesByOwner(@RequestParam(value = "ownerId") int ownerId) {
		return houseService.getHouses(ownerId);
	}
}
