package priv.xly.rentsys.model;

import java.util.Date;

public class Tenant {

	private long id;
	private String passwd;
	private String name;
	private String address;
	private String phoneNum;
	private Date birthday;
	private Integer sex;
	private String picUrl;

	public static Tenant factory(String passwd, String name, String picUrl, String address, String phoneNum,
			Integer sex, Date birthday) {
		Tenant tenant = new Tenant();
		tenant.passwd = passwd;
		tenant.picUrl = picUrl;
		tenant.name = name;
		tenant.picUrl = picUrl;
		tenant.address = address;
		tenant.phoneNum = phoneNum;
		tenant.birthday = birthday;
		tenant.sex = sex;
		return tenant;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
