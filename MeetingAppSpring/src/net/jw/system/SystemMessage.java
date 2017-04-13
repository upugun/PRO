package net.jw.system;

public class SystemMessage {

	public static String MSG_SUCCESSFULLY_SAVED = "Successfully Saved.";
	public static String MSG_ERROR_SAVING = "Error While Saving.";

	public static String MSG_BOOKING_TIME_NOT_AVAILABLE = "Booked time slots are not available.";
	public static String MSG_BOOKING_APPROVED = "Booking is approved.";
	public static String MSG_BOOKING_REJECTED = "Booking is rejected.";
	public static String MSG_BOOKING_DELETE = "Booking has been successfully deleted.";

	public static String MSG_USER_ACTIVATED = "User is Activated.";
	public static String MSG_USER_INACTIVATED = "User is inactivated.";
	public static String MSG_USER_REGISTERED = "Successfully registered.";

	public static String MSG_MROOM_ACTIVATED = "Meeting Room has been Successfully Activated.";
	public static String MSG_MROOM_INACTIVATED = "Meeting Room is Successfully deactivated.";

	public static String MSG_LOCATIION_ACTIVATED = "Location has been Successfully Activated.";
	public static String MSG_LOCATIION_INACTIVATED = "Location has been Successfully deactivated.";
	
	public static String MSG_FACILITY_ACTIVATED = "Facility has been Successfully Activated.";
	public static String MSG_FACILITY_INACTIVATED = "Facility has been Successfully deactivated.";
	
	public static String MSG_ROLE_ACTIVATED = "Role has been Successfully Activated.";
	public static String MSG_ROLE_INACTIVATED = "Role has been Successfully deactivated.";
	
	public static String MSG_OPTION_ACTIVATED = "Option has been Successfully Activated.";
	public static String MSG_OPTION_INACTIVATED = "Option has been Successfully deactivated.";
	
	public static String PASSWORD_MAIL_SUCCESS= "Password Reset link successfully sent to your email account";
	public static String PASSWORD_MAIL_UNSUCCESS = "Entered email address is not found.";

	public static void pringMessage(String str) {
		System.out.println("[[ERROR]] : " + str);
	}

}
