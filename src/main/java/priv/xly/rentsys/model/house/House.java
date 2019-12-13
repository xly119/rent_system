package priv.xly.rentsys.model.house;

public class House {

	private int id;
	private int ownerId;
	private String picUrl;
	private int maxTenantNum;
	private String address;
	private float rent;
	private int state;
	private int type;
	private int subs;
	private String title;
	
	public static House HouseFactory(int ownerId, int maxTenantNum, float rent, int type,String picUrl,String address,String title) {
		House house=new House();
		house.ownerId = ownerId;
		house.maxTenantNum = maxTenantNum;
		house.rent = rent;
		house.state = 0;
		house.type = type;
		house.picUrl=picUrl;
		house.address=address;
		house.title=title;
		return house;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public int getMaxTenantNum() {
		return maxTenantNum;
	}
	public void setMaxTenantNum(int maxTenantNum) {
		this.maxTenantNum = maxTenantNum;
	}
	public float getRent() {
		return rent;
	}
	public void setRent(float rent) {
		this.rent = rent;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSubs() {
		return subs;
	}

	public void setSubs(int subs) {
		this.subs = subs;
	}
	
	
	
}
