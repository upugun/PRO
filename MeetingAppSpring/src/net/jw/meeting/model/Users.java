package net.jw.meeting.model;

public class Users {
	
	
	private int uid;
	private String uName;
	private String uDep;
	private String uBuild;
	private String uFloor;
	private int uRoll;
	private String mobile;
	private String email;
	private String details;
	
	private CommonDetails common;
	
	private Login login;
	
	private Role role;
	
	public Users(){
		
		
		setRole(new Role());
		
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getuName() {
		return uName;
	}


	public void setuName(String uName) {
		this.uName = uName;
	}


	public String getuDep() {
		return uDep;
	}


	public void setuDep(String uDep) {
		this.uDep = uDep;
	}


	public String getuBuild() {
		return uBuild;
	}


	public void setuBuild(String uBuild) {
		this.uBuild = uBuild;
	}


	public String getuFloor() {
		return uFloor;
	}


	public void setuFloor(String uFloor) {
		this.uFloor = uFloor;
	}


	public int getuRoll() {
		return uRoll;
	}


	public void setuRoll(int uRoll) {
		this.uRoll = uRoll;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public CommonDetails getCommon() {
		return common;
	}


	public void setCommon(CommonDetails common) {
		this.common = common;
	}


	public Login getLogin() {
		return login;
	}


	public void setLogin(Login login) {
		this.login = login;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}

}
