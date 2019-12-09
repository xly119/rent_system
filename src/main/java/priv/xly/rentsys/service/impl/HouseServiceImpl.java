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

	/*
	 * 新增房屋
	 * 
	 * @return 新增房屋的id
	 * 
	 * @exception 文件异常，数据库操作异常
	 * 
	 * @see
	 * priv.xly.rentsys.service.HouseService#insertHouse(org.springframework.web.
	 * multipart.MultipartFile, java.util.Map)
	 */
	@Override
	public int insertHouse(MultipartFile file, Map<String, String> param) throws Exception {
		String picName = DEFAULT_PIC_URL;
		if (file != null && file.getSize() > 0) {
			picName = oneImgUpload.saveFile(file).get("name");
		}
		House house = House.HouseFactory(Integer.parseInt(param.get("ownerId")),
				Integer.parseInt(param.get("maxTenantNum")), Float.parseFloat(param.get("rent")),
				Integer.parseInt(param.get("type")), picName, param.get("address"));
		houseDao.insertHouse(house);
		return house.getId();
	}

	/*
	 * 更新房屋信息
	 * 
	 * @exception 文件异常，数据库操作异常
	 * 
	 * @see
	 * priv.xly.rentsys.service.HouseService#updateInfo(org.springframework.web.
	 * multipart.MultipartFile, java.util.Map)
	 */
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
			house.setType(Integer.parseInt(param.get("type")));
			houseDao.updateHouseInfo(house);
		}
	}

	/*
	 * 变更房屋状态 在房主缴纳费用，房屋出租，房屋退租时使用
	 * 
	 * @see priv.xly.rentsys.service.HouseService#updateState(int, int)
	 */
	@Override
	public void updateState(int id, int state) {
		houseDao.updateHouseState(state, id);
	}

	/*
	 * 根据房屋id获取房屋
	 * 
	 * @return 房屋
	 * 
	 * @see priv.xly.rentsys.service.HouseService#getHouseById(int)
	 */
	@Override
	public House getHouseById(int id) {
		return houseDao.getHouseById(id);
	}

	/*
	 * 根据房主id获取房屋列表
	 * 
	 * @return 房屋列表
	 * 
	 * @see priv.xly.rentsys.service.HouseService#getHouses(int)
	 */
	@Override
	public List<House> getHouses(int ownerId) {
		return houseDao.getHouses(ownerId);
	}

	/*
	 * 获取可供出租房屋列表 房客主页推荐列表获取
	 * 
	 * @return 房屋列表
	 * 
	 * @see priv.xly.rentsys.service.HouseService#getHouseAvailable()
	 */
	@Override
	public List<House> getHouseAvailable() {
		return houseDao.getHouseAvailable();
	}

	/*
	 * 根据id删除房屋
	 * 
	 * @see priv.xly.rentsys.service.HouseService#deleteHouse(int)
	 */
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

	/*
	 * 新增看房预约
	 * 
	 * @return 新增预约的id
	 * 
	 * @see priv.xly.rentsys.service.HouseService#insertVistRecord(int, int, int,
	 * java.lang.String)
	 */
	@Override
	public int insertVistRecord(int houseId, int visterId, String visitTime, String mark) throws Exception {
		House house = houseDao.getHouseById(houseId);
		HouseVisitRecord rec = HouseVisitRecord.factory(house.getOwnerId(), houseId, visterId, format.parse(visitTime),
				mark, house.getAddress());
		houseDao.insertVisit(rec);
		return rec.getId();
	}

	/*
	 * 变更预约状态 房主同意预约时调用
	 * 
	 * @see priv.xly.rentsys.service.HouseService#updateVisitState(int, int)
	 */
	@Override
	public void updateVisitState(int state, int id) {
		houseDao.updateVisitState(state, id);
	}

	/*
	 * 根据房屋id获取看房预约列表
	 * 
	 * @return 预约列表
	 * 
	 * @see priv.xly.rentsys.service.HouseService#getVisitList(int)
	 */
	@Override
	public List<HouseVisitRecord> getVisitList(int houseId) {
		return houseDao.getVisits(houseId);
	}

	/*
	 * 根据房主id获取预约列表获取
	 * 
	 * @return 预约列表
	 * 
	 * @see priv.xly.rentsys.service.HouseService#getVisitListByOwner(int)
	 */
	@Override
	public List<HouseVisitRecord> getVisitListByOwner(int ownerId) {
		return houseDao.getVisitsByOwner(ownerId);
	}

	/*
	 * 根据房客id获取预约列表
	 * 
	 * @return 预约列表
	 * 
	 * @see priv.xly.rentsys.service.HouseService#getVisitListByVisiter(int)
	 */
	@Override
	public List<HouseVisitRecord> getVisitListByVisiter(int visiterId) {
		return houseDao.getVisitsByVisiter(visiterId);
	}

	@Override
	public void insertSubs(int houseId, int userId) {
		houseDao.insertSubs(userId, houseId);
		houseDao.updateHouseSubs(1, houseId);
	}

	@Override
	public void delSubs(int houseId, int userId) {
		houseDao.deleteSubs(userId, houseId);
		houseDao.updateHouseSubs(-1, houseId);
	}

	@Override
	public boolean subsCheck(int houseId, int userId) {
		return houseDao.getSubsById(userId, houseId) == null ? false : true;
	}

	@Override
	public List<House> getHouseSubscribed(int userId) {
		return houseDao.getHouseSubscribed(userId);
	}
}
