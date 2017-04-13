package net.jw.meeting.model;

public class BookingFacilities {
	private int bookingId;
	private int facilityId;
	private int count;
	private String status;
	
	
	private Facilities facilities;
	
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Facilities getFacilities() {
		return facilities;
	}
	public void setFacilities(Facilities facilities) {
		this.facilities = facilities;
	}
	
}
	
	
	
	