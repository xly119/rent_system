package priv.xly.rentsys.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.dao.HouseDao;
import priv.xly.rentsys.model.house.House;
import priv.xly.rentsys.model.house.HouseVisitRecord;
import priv.xly.rentsys.service.HouseService;
import priv.xly.rentsys.util.OneImgUpload;

@Service
public class HouseServiceImpl implements HouseService {

	@Autowired
	OneImgUpload oneImgUpload;
	@Autowired
	HouseDao houseDao;
	@Value("${web.upload-path}")
	public String FILE_PATH;
	@Value("${web.defsult-pic-path}")
	public String DEFAULT_PIC_URL;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public int insertHouse(MultipartFile file, Map<String, String> param) throws Exception {
		String picName = DEFAULT_PIC_URL;
		if (file != null && file.getSize() > 0) {
			picName = oneImgUpload.saveFile(file).get("name");
		}
		House house = House.HouseFactory(param.get("ownerId"), Integer.parseInt(param.get("maxTenantNum")),
				Float.parseFloat(param.get("rent")), Integer.parseInt(param.get("type")), picName);
		houseDao.insertHouse(house);
		return house.getId();
	}

	@Override
	public void updateInfo(MultipartFile file, Map<String, String> param) throws Exception {
		int id = Integer.parseInt(param.get("id"));
		House house = houseDao.getHouseById(id);
		if (house != null) {
			if (file != null && file.getSize() > 0) {
				File oldPic = new File(FILE_PATH + house.getPicUrl());
				if (house.getPicUrl() != DEFAULT_PIC_URL && oldPic.exists() && oldPic.isFile()) {
					oldPic.delete();
				}
				house.setPicUrl(oneImgUpload.saveFile(file).get("name"));
			}
			house.setMaxTenantNum(Integer.parseInt(param.get("maxTenantNum")));
			house.setRent(Float.parseFloat(param.get("rent")));
			houseDao.updateHouseInfo(house);
		}
	}

	@Override
	public void updateState(int id, int state) {
		houseDao.updateHouseState(state, id);
	}

	@Override
	public void updateVisitState(int state, int id) {
		houseDao.updateVisitState(state, id);
	}

	@Override
	public void deleteHouse(int id) {
		House house = houseDao.getHouseById(id);
		if (house != null) {
			File oldPic = new File(FILE_PATH + house.getPicUrl());
			if (house.getPicUrl() != DEFAULT_PIC_URL && oldPic.exists() && oldPic.isFile()) {
				oldPic.delete();
			}
		}
		houseDao.deleteHouse(id);
	}

	@Override
	public List<House> getHouses(int ownerId) {
		return houseDao.getHouses(ownerId);
	}

	@Override
	public int insertVistRecord(int ownerId, int houseId, int visterId, String visitTime) throws Exception {
		HouseVisitRecord rec = HouseVisitRecord.factory(ownerId, houseId, visterId, format.parse(visitTime));
		houseDao.insertVisit(rec);
		return rec.getId();
	}

	@Override
	public List<HouseVisitRecord> getVisitList(int houseId) {
		return houseDao.getVisits(houseId);
	}

	@Override
	public House getHouseById(int id) {
		return houseDao.getHouseById(id);
	}

	@Override
	public List<HouseVisitRecord> getVisitListByOwner(int ownerId) {
		return houseDao.getVisitsByOwner(ownerId);
	}

	@Override
	public List<House> getHouseAvailable() {
		return houseDao.getHouseAvailable();
	}

	@Override
	public List<HouseVisitRecord> getVisitListByVisiter(int visiterId) {
		return houseDao.getVisitsByVisiter(visiterId);
	}
}
