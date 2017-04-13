package net.jw.meeting.model;

public class MeetingRoomResources {
	
	
	private int mrid;
	private int resourceId;
    private String status;
	
	
	
	public MeetingRoomResources(){

}

	public int getMrid() {
		return mrid;
	}



	public void setMrid(int mrid) {
		this.mrid = mrid;
	}



	public int getResourceId() {
		return resourceId;
	}



	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}

}