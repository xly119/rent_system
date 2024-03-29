package priv.xly.rentsys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.model.house.House;
import priv.xly.rentsys.model.house.HouseVisitRecord;

public interface HouseService {

	int insertHouse(MultipartFile file, Map<String, String> param) throws Exception;
	void updateInfo(MultipartFile file, Map<String, String> param) throws Exception;
	void updateState(int id,int state);
	void deleteHouse(int id);
	House getHouseById(int id);
	List<House> getHouses(int ownerId);
	List<House> getHouseAvailable();
	
	int insertVistRecord(int houseId, int visterId,String visitTime,String mark)throws Exception;
	void updateVisitState(int state,int id);
	List<HouseVisitRecord> getVisitListByOwner(int ownerId);
	List<HouseVisitRecord> getVisitListByVisiter(int visiterId);
	List<HouseVisitRecord> getVisitList(int houseId);

	void insertSubs(int houseId,int userId);
	void delSubs(int houseId,int userId);
	boolean subsCheck(int houseId,int userId);
	List<House> getHouseSubscribed(int userId);
	
 
}
