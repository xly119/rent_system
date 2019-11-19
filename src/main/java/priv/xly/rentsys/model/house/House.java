package priv.xly.rentsys.model.house;

public class House {

	private int id;
	private String ownerId;
	private String picUrl;
	private int maxTenantNum;
	private float rent;
	private int state;
	private int type;
	
	public static House HouseFactory(String ownerId, int maxTenantNum, float rent, int type,String picUrl) {
		House house=new House();
		house.ownerId = ownerId;
		house.maxTenantNum = maxTenantNum;
		house.rent = rent;
		house.state = 0;
		house.type = type;
		house.picUrl=picUrl;
		return house;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
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
	
	
	
}
