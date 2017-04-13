package net.jw.meeting.model;

public class Facilities {
	
	
	private int fid;
	private String fName;
	private int owner;
	private int ext;
	private String email;
	private String details;
	private boolean countable;
	private String status;
	private CommonDetails common;
	
	private Users admin;
	
	
	public Facilities(){
		
		
		
		
		
	}


	public int getFid() {
		return fid;
	}


	public void setFid(int fid) {
		this.fid = fid;
	}


	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public int getOwner() {
		return owner;
	}


	public void setOwner(int owner) {
		this.owner = owner;
	}


	public int getExt() {
		return ext;
	}


	public void setExt(int ext) {
		this.ext = ext;
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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	public CommonDetails getCommon() {
		return common;
	}

	public void setCommon(CommonDetails common) {
		this.common = common;
	}


	public boolean getCountable() {
		return countable;
	}


	public void setCountable(boolean countable) {
		this.countable = countable;
	}


	public Users getAdmin() {
		return admin;
	}


	public void setAdmin(Users admin) {
		this.admin = admin;
	}


	
	

}
