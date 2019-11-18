package priv.xly.rentsys.model;

import java.util.ArrayList;
import java.util.List;

import priv.xly.rentsys.model.house.House;

public class HouseOwner {

	private int id;
	private String passwd;
	private String name;
	private String address;
	private String phoneNum;
	private String picUrl;
	private List<House> houses;

	public HouseOwner(String passwd, String name) {
		super();
		this.passwd = passwd;
		this.name = name;
		this.houses = new ArrayList<House>();
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
