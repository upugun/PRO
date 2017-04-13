package net.jw.meeting.model;

public class Resources {
	
	
	private int rid;
	private String rName;
	private String rType;
	private String owner;
	private String detail;
	private CommonDetails common;
	
	
	public Resources()
	{
		setCommon(new CommonDetails());
	}
	
	
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}


	public String getrName() {
		return rName;
	}


	public void setrName(String rName) {
		this.rName = rName;
	}


	public String getrType() {
		return rType;
	}


	public void setrType(String rType) {
		this.rType = rType;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public CommonDetails getCommon() {
		return common;
	}


	public void setCommon(CommonDetails common) {
		this.common = common;
	}

}
