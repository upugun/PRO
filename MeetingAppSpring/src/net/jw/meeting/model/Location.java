package net.jw.meeting.model;

public class Location {
	
	private int lid;
	private String locationName;
	private String building;
	private int floor;
	private String address;
	private CommonDetails common;
	
	public Location()
	{
		common = new CommonDetails();
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CommonDetails getCommon() {
		return common;
	}

	public void setCommon(CommonDetails common) {
		this.common = common;
	}
	
	
	

}
