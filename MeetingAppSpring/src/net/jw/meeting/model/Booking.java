package net.jw.meeting.model;

import java.util.ArrayList;
import java.util.List;

public class Booking {
	
	private int bid;
	private int meetingRoomId;
	private String title;
	private String start;
	private String end;
	private boolean allDay;
	private String remarks;
	private boolean repeating;
	private String bookingStatus;
	
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	

	
	private MeetingRoom meetingRoom;
	private CommonDetails common;
	
	private BookingUsers bookingUser;
	
	
	private List<BookingResources> resourcesList = new ArrayList<>();
	private List<BookingFacilities> facilityList = new ArrayList<>();
	
	public Booking(){

		common = new CommonDetails();
		setMeetingRoom(new MeetingRoom());
		
		bookingUser = new BookingUsers();
		
		
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(int meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isRepeating() {
		return repeating;
	}

	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public CommonDetails getCommon() {
		return common;
	}

	public void setCommon(CommonDetails common) {
		this.common = common;
	}

	public BookingUsers getBookingUser() {
		return bookingUser;
	}

	public void setBookingUser(BookingUsers bookingUser) {
		this.bookingUser = bookingUser;
	}

	public List<BookingResources> getResourcesList() {
		return resourcesList;
	}

	public void setResourcesList(List<BookingResources> resourcesList) {
		this.resourcesList = resourcesList;
	}

	public List<BookingFacilities> getFacilityList() {
		return facilityList;
	}

	public void setFacilityList(List<BookingFacilities> facilityList) {
		this.facilityList = facilityList;
	}




}
