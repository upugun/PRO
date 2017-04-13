package net.jw.meeting.model;

public class Login {
	
	private int uid;
	private String userName;
	private String password;
	
	private CommonDetails common;
	
	public Login()
	{
		common = new CommonDetails();
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public CommonDetails getCommon() {
		return common;
	}
	public void setCommon(CommonDetails common) {
		this.common = common;
	}
	
	

}
