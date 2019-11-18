package priv.xly.rentsys.model.house;

import java.util.Date;

public class HouseVisitRecord {
	
	private int id;
	private int houseId;
	private int ownerId;
	private int visterId;
	private int state; //待同意，待赴约，已取消，已完成
	private Date visitTime;
	
	
	public HouseVisitRecord(int ownerId,int houseId, int visterId,Date visitTime) {
		super();
		this.ownerId=ownerId;
		this.houseId = houseId;
		this.visterId = visterId;
		this.visitTime = visitTime;
		this.state=0;
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
	public int getVisterId() {
		return visterId;
	}
	public void setVisterId(int visterId) {
		this.visterId = visterId;
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
