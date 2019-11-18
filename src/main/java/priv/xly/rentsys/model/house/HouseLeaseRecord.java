package priv.xly.rentsys.model.house;

import java.util.Date;

public class HouseLeaseRecord {

	private int id;
	private int houseId;
	private int tenantId;
	private Date startDate;
	private Date endDate;
	
	
	public HouseLeaseRecord(int houseId, int tenantId, Date startDate, Date endDate) {
		super();
		this.houseId = houseId;
		this.tenantId = tenantId;
		this.startDate = startDate;
		this.endDate = endDate;
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
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
	
}
