package priv.xly.rentsys.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import priv.xly.rentsys.model.Tenant;

public interface TenantService {

	long insert(MultipartFile file,  Map<String, String> param) throws Exception;
	void update(MultipartFile file, Map<String, Object> param) throws Exception;
	Tenant get(int id);
}
