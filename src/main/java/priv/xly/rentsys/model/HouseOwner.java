package priv.xly.rentsys.model;

public class HouseOwner {

	private long id;
	private String passwd;
	private String name;
	private String address;
	private String phoneNum;
	private String picUrl;

	public static HouseOwner factory(String passwd, String name, String picUrl, String address, String phoneNum) {
		HouseOwner owner = new HouseOwner();
		owner.passwd = passwd;
		owner.name = name;
		owner.picUrl = picUrl;
		owner.address = address;
		owner.phoneNum = phoneNum;
		return owner;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
