package priv.xly.rentsys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.model.house.House;
import priv.xly.rentsys.model.house.HouseLeaseRecord;
import priv.xly.rentsys.model.house.HouseVisitRecord;

public interface HouseService {

	int insertHouse(MultipartFile file, Map<String, String> param) throws Exception;
	void updateInfo(MultipartFile file, Map<String, String> param) throws Exception;
	void updateState(int id,int state);
	void deleteHouse(int id);
	House getHouseById(int id);
	List<House> getHouses(int ownerId);
	
	int insertVistRecord(int ownerId,int houseId, int visterId,Date visitTime);
	void updateVisitState(int state,int id);
	List<HouseVisitRecord> getVisitListByOwner(int ownerId);
	List<HouseVisitRecord> getVisitList(int houseId);
	
	int insertLeaseRecord(int houseId, int tenantId, Date startDate, Date endDate);
	List<HouseLeaseRecord> getLeaseList(int houseId);
	
 
}
