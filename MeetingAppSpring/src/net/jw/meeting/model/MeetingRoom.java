package net.jw.meeting.model;

import java.util.ArrayList;
import java.util.List;


public class MeetingRoom {
	
	
	private int mId;
	private String mRoomName;
	private int mLocationId;
	private int seatingCapacity;
	private int adminId;
	private int tp;
	private String notes;
	private boolean approval;
	
	private CommonDetails common;
	
	private Users admin;
	
	private Location location;
	
	private List<MeetingRoomResources> resourceList = new ArrayList<>();
	
	public MeetingRoom(){
		
		setCommon(new CommonDetails());
		setLocation(new Location());
		setAdmin(new Users());
		
		
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getmRoomName() {
		return mRoomName;
	}

	public void setmRoomName(String mRoomName) {
		this.mRoomName = mRoomName;
	}

	public int getmLocationId() {
		return mLocationId;
	}

	public void setmLocationId(int mLocationId) {
		this.mLocationId = mLocationId;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public int getTp() {
		return tp;
	}

	public void setTp(int tp) {
		this.tp = tp;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public CommonDetails getCommon() {
		return common;
	}

	public void setCommon(CommonDetails common) {
		this.common = common;
	}

	public Users getAdmin() {
		return admin;
	}

	public void setAdmin(Users admin) {
		this.admin = admin;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<MeetingRoomResources> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<MeetingRoomResources> resourceList) {
		this.resourceList = resourceList;
	}



	
}