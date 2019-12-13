package priv.xly.rentsys.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;

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
	@Value("${web.upload-path}")
	private String FILE_PATH;
	@Value("${web.defsult-pic-path}")
	private String DEFAULT_PIC_URL;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	/*
	 * 新增房客
	 * 
	 * @return 新增房客的id
	 * 
	 * @exception 文件异常，数据库操作异常
	 * 
	 * @see priv.xly.rentsys.service.TenantService#insert(org.springframework.web.
	 * multipart.MultipartFile, java.util.Map)
	 */
	@Override
	public long insert(MultipartFile file, Map<String, String> param) throws Exception {
		Tenant tenant = tenantDao.getByPhoneNum(param.get("phoneNum"));
		if(tenant!=null) {
			return -1;
		}
		String picName = DEFAULT_PIC_URL;
		if (file != null && file.getSize() > 0) {
			picName = oneImgUpload.saveFile(file).get("name");
		}
		tenant = Tenant.factory(param.get("passwd"), param.get("name"), picName, param.get("address"),
				param.get("phoneNum"), Integer.parseInt(param.get("sex")), format.parse(param.get("birthday")));
		tenantDao.insert(tenant);
		return tenant.getId();
	}

	/*
	 * 更新房屋信息
	 * 
	 * @exception 文件异常，数据库操作异常
	 * 
	 * @see priv.xly.rentsys.service.TenantService#update(org.springframework.web.
	 * multipart.MultipartFile, java.util.Map)
	 */
	@Override
	public void update(MultipartFile file, Map<String, String> param) throws Exception {
		int id = Integer.parseInt(param.get("id"));
		Tenant tenant = tenantDao.get(id);
		if (tenant != null) {
			if (file != null && file.getSize() > 0) {
				File oldPic = new File(FILE_PATH + tenant.getPicUrl());
				if (tenant.getPicUrl() != DEFAULT_PIC_URL && oldPic.exists() && oldPic.isFile()) {
					oldPic.delete();
				}
				tenant.setPicUrl(oneImgUpload.saveFile(file).get("name"));
			}
			tenant.setAddress(param.get("address"));
			tenant.setBirthday(format.parse(param.get("birthday")));
			tenant.setName(param.get("name"));
			tenant.setPhoneNum(param.get("phoneNum"));
			tenant.setPasswd(param.get("passwd"));
			tenant.setSex(Integer.parseInt(param.get("sex")));
			tenantDao.update(tenant);
		}
	}

	/*
	 * 根据id获取房客
	 * 
	 * @return 房客
	 * 
	 * @see priv.xly.rentsys.service.TenantService#get(int)
	 */
	@Override
	public Tenant get(int id) {
		return tenantDao.get(id);
	}

	/*
	 * 根据手机号码验证房客密码
	 * 
	 * @return 房客
	 * 
	 * @see priv.xly.rentsys.service.TenantService#getByPhoneNum(java.lang.String)
	 */
	@Override
	public Tenant loginCheck(String phoneNum,String passwd) {
		Tenant tenant = tenantDao.getByPhoneNum(phoneNum);
		if(tenant==null) {
			return null;
		}
		return tenant.getPasswd().equals(passwd)?tenant:null;
	}
}
