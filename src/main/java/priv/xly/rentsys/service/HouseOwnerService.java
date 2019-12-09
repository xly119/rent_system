package priv.xly.rentsys.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.model.HouseOwner;


public interface HouseOwnerService {

	long insert(MultipartFile file, Map<String, String> param) throws Exception;
	void update(MultipartFile file,Map<String, String> param) throws Exception;
	HouseOwner get(int id);
	HouseOwner loginCheck(String phoneNum,String passwd);
}
