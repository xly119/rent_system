package priv.xly.rentsys.model.house;

import java.util.Date;

public class HouseVisitRecord {
	
	private int id;
	private int houseId;
	private int ownerId;
	private int visiterId;
	private int state; //待同意，待赴约，已取消，已完成
	private Date visitTime;
	
	
	public static HouseVisitRecord factory(int ownerId,int houseId, int visiterId,Date visitTime) {
		HouseVisitRecord visit=new HouseVisitRecord();
		visit.ownerId=ownerId;
		visit.houseId = houseId;
		visit.visiterId = visiterId;
		visit.visitTime = visitTime;
		visit.state=0;
		return visit;
	}
	
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public int getVisiterId() {
		return visiterId;
	}
	public void setVisiterId(int visiterId) {
		this.visiterId = visiterId;
	}
	public Date getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
	

}
