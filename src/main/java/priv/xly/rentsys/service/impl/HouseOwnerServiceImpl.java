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
	@Value("${}")
	public String FILE_PATH;
	@Value("${}")
	public String DEFAULT_PIC_URL;

	@Override
	public int insert(MultipartFile file, String passwd) throws Exception {
		String picName = DEFAULT_PIC_URL;
		if (file != null && file.getSize() > 0) {
			picName = oneImgUpload.saveFile(file).get("name");
		}
		HouseOwner owner=new HouseOwner(passwd, picName);
		houseOwnerDao.insert(owner);
		return owner.getId();
	}

	@Override
	public void update(MultipartFile file, Map<String, String> param) throws Exception {
		int id = Integer.parseInt(param.get("id"));
		HouseOwner owner = houseOwnerDao.get(id);
		if (owner != null) {
			if (file != null && file.getSize() > 0) {
				File oldPic = new File(owner.getPicUrl());
				if (owner.getPicUrl() != DEFAULT_PIC_URL && oldPic.exists() && oldPic.isFile()) {
					oldPic.delete();
				}
				owner.setPicUrl(oneImgUpload.saveFile(file).get("name"));
			}
			owner.setName(param.get("name"));
			owner.setPhoneNum(param.get("phoneNum"));
			owner.setAddress(param.get("address"));
			houseOwnerDao.update(owner);
		}

	}

	@Override
	public HouseOwner get(int id) {
		return houseOwnerDao.get(id);
	}

}
