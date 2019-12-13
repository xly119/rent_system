package priv.xly.rentsys.service.impl;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.dao.HouseOwnerDao;
import priv.xly.rentsys.model.HouseOwner;
import priv.xly.rentsys.service.HouseOwnerService;
import priv.xly.rentsys.util.OneImgUpload;

@Service
public class HouseOwnerServiceImpl implements HouseOwnerService {

	@Autowired
	OneImgUpload oneImgUpload;
	@Autowired 
	HouseOwnerDao houseOwnerDao;
	@Value("${web.upload-path}")
	public String FILE_PATH;
	@Value("${web.defsult-pic-path}")
	public String DEFAULT_PIC_URL;

	/*
	 *   新增房主  
	 * @return 新增房主的id
	 * @exception 文件异常，数据库操作异常
	 * @see priv.xly.rentsys.service.HouseOwnerService#insert(org.springframework.web.multipart.MultipartFile, java.util.Map)
	 */
	@Override
	public long insert(MultipartFile file, Map<String, String> param) throws Exception {
		HouseOwner owner = houseOwnerDao.getByPhoneNum(param.get("phoneNum"));
		if(owner!=null) {
			return -1;
		}
		String picName = DEFAULT_PIC_URL;
		if (file != null && file.getSize() > 0) {
			picName = oneImgUpload.saveFile(file).get("name");
		}
		owner = HouseOwner.factory(param.get("passwd"), param.get("name"), picName, param.get("address"),
				param.get("phoneNum"));
		houseOwnerDao.insert(owner);
		return owner.getId();
	}
	/*
	 *  更新房主信息
	 * @exception 文件异常，数据库操作异常
	 * @see priv.xly.rentsys.service.HouseOwnerService#update(org.springframework.web.multipart.MultipartFile, java.util.Map)
	 */

	@Override
	public void update(MultipartFile file, Map<String, String> param) throws Exception {
		int id = Integer.parseInt(param.get("id"));
		HouseOwner owner = houseOwnerDao.get(id);
		if (owner != null) {
			if (file != null && file.getSize() > 0) {
				File oldPic = new File(FILE_PATH + owner.getPicUrl());
				if (owner.getPicUrl() != DEFAULT_PIC_URL && oldPic.exists() && oldPic.isFile()) {
					oldPic.delete();
				}
				owner.setPicUrl(oneImgUpload.saveFile(file).get("name"));
			}
			owner.setName(param.get("name"));
			owner.setPasswd(param.get("passwd"));
			owner.setPhoneNum(param.get("phoneNum"));
			owner.setAddress(param.get("address"));
			houseOwnerDao.update(owner);
		}

	}
	
	/*
	 * 根据id获取房主信息
	 * @return 房主对象
	 * @see priv.xly.rentsys.service.HouseOwnerService#get(int)
	 */

	@Override
	public HouseOwner get(int id) {
		return houseOwnerDao.get(id);
	}

	/*
	 * 根据电话号码验证房主密码
	 * @return 房主对象
	 * @see priv.xly.rentsys.service.HouseOwnerService#getByPhoneNum(java.lang.String)
	 */
	@Override
	public HouseOwner loginCheck(String phoneNum,String passwd) {
		HouseOwner owner = houseOwnerDao.getByPhoneNum(phoneNum);
		if(owner==null) {
			return null;
		}
		return owner.getPasswd().equals(passwd)?owner:null;
	}

}
