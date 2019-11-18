package priv.xly.rentsys.model.house;

import java.util.ArrayList;
import java.util.List;


public class House {

	private int id;
	private String ownerId;
	private String picUrl;
	private int maxTenantNum;
	private float rent;
	private int state;
	private int type;
	private List<HouseLeaseRecord> leases;
	private List<HouseVisitRecord> visits;
	
	
	
	public House(String ownerId, int maxTenantNum, float rent, int type,String picUrl) {
		super();
		this.ownerId = ownerId;
		this.maxTenantNum = maxTenantNum;
		this.rent = rent;
		this.state = 0;
		this.type = type;
		this.picUrl=picUrl;
		this.leases = new ArrayList<HouseLeaseRecord>();
		this.visits = new ArrayList<HouseVisitRecord>();
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
	public List<HouseLeaseRecord> getLeases() {
		return leases;
	}
	public void setLeases(List<HouseLeaseRecord> leases) {
		this.leases = leases;
	}
	public List<HouseVisitRecord> getVisits() {
		return visits;
	}
	public void setVisits(List<HouseVisitRecord> visits) {
		this.visits = visits;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	
	
}
