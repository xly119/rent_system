package priv.xly.rentsys.service.impl;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.dao.TenantDao;
import priv.xly.rentsys.model.Tenant;
import priv.xly.rentsys.service.TenantService;
import priv.xly.rentsys.util.OneImgUpload;

@Service
public class TenantServiceImpl implements TenantService {

	@Autowired
	OneImgUpload oneImgUpload;
	@Autowired
	TenantDao tenantDao;
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
		Tenant tenant=new Tenant(passwd, picName);
		tenantDao.insert(tenant);
		return tenant.getId();
	}

	@Override
	public void update(MultipartFile file, Map<String, Object> param) throws Exception {
		int id = (int) param.get("id");
		Tenant tenant = tenantDao.get(id);
		if (tenant != null) {
			if (file != null && file.getSize() > 0) {
				File oldPic = new File(tenant.getPicUrl());
				if (tenant.getPicUrl() != DEFAULT_PIC_URL && oldPic.exists() && oldPic.isFile()) {
					oldPic.delete();
				}
				tenant.setPicUrl(oneImgUpload.saveFile(file).get("name"));
			}
			tenant.setAddress(param.get("address").toString());
			tenant.setBirthday((Date) param.get("birthday"));
			tenant.setName(param.get("name").toString());
			tenant.setPhoneNum(param.get("phoneNum").toString());
			tenant.setSex((int) param.get("sex"));
			tenantDao.update(tenant);
		}
	}

	@Override
	public Tenant get(int id) {
		return tenantDao.get(id);
	}
}
