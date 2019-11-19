package priv.xly.rentsys.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import priv.xly.rentsys.service.HouseOwnerService;
import priv.xly.rentsys.service.TenantService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private HouseOwnerService houseOwnerService;
	@Autowired
	private TenantService tenantService;

	@RequestMapping("/register/owner")
	public Object insertOwner(@RequestParam Map<String, String> params,
							  @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		return houseOwnerService.insert(file, params);
	}

	@RequestMapping("/register/tenant")
	public Object insertTenant(@RequestParam Map<String, String> params,
			                   @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		return tenantService.insert(file, params);
	}

	@RequestMapping("/login/owner")
	public Object owenerLoginCheck(@RequestParam(value = "passwd") String passwd,
								   @RequestParam(value = "id") int id) {
		return houseOwnerService.get(id).getPasswd().equals(passwd);
	}
	
	@RequestMapping("/login/tenant")
	public Object tenantLoginCheck(@RequestParam(value = "passwd") String passwd,
								   @RequestParam(value = "id") int id) {
		return tenantService.get(id).getPasswd().equals(passwd);
	}
	
	@RequestMapping("/update/ownerinfo")
	public void updateOwnerInfo(@RequestParam Map<String, String> params,
			 					@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		houseOwnerService.update(file, params);
	}

	@RequestMapping("/update/tenantInfo")
	public void updateTenantInfo(@RequestParam Map<String, Object> params,
							     @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		tenantService.update(file, params);
	}
	
	@RequestMapping("/get/tenant")
	public Object getTenant(@RequestParam(value = "id") int id) {
		return tenantService.get(id);
	}
	
	@RequestMapping("/get/owner")
	public Object getOwner(@RequestParam(value = "id") int id) {
		return houseOwnerService.get(id);
	}
}
