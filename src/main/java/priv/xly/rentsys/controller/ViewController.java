package priv.xly.rentsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/rentsys")
public class ViewController {

	@RequestMapping("/login") // http://localhost:8101/rentsys/login
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping("/signup/tenant")  // http://localhost:8101/rentsys/signup/tenant
	public String tenantSignupPage() {
		return "signup-tenant";
	}
	
	@RequestMapping("/signup/owner") // http://localhost:8101/rentsys/signup/owner
	public String ownerSignupPage() {
		return "signup-owner";
	}
	
	@RequestMapping("/recommend") // http://localhost:8101/rentsys/recommend
	public String recommendPage() {
		return "house-recmmend";
	}
	
	@RequestMapping("/main/tenant")  // http://localhost:8101/rentsys/main/tenant
	public String tenantMainPage() {
		return "tenant-main";
	}
	
	@RequestMapping("/main/owner")// http://localhost:8101/rentsys/main/owner
	public String owenerMainPage() {
		return "owner-main";
	}
	
	@RequestMapping("/house/edit") // http://localhost:8101/rentsys/house/edit
	public String houseEditPage() {
		return "house-edit";
	}
	
	@RequestMapping("/house/info")  // http://localhost:8101/rentsys/house/info
	public String houseInfoPage() {
		return "house-info";
	}
	
	@RequestMapping("/house/new") // http://localhost:8101/rentsys/house/new
	public String houseNewPage() {
		return "house-new";
	}
	
	@RequestMapping("/house/pay")  // http://localhost:8101/rentsys/house/pay
	public String housePayPage() {
		return "house-pay";
	}
	
	@RequestMapping("/helper")  // http://localhost:8101/rentsys/helper
	public String helpPage() {
		return "help";
	}
}
