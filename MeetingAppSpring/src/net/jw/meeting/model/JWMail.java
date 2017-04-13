package net.jw.meeting.model;

public class JWMail {
	private int mailId;
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String message;
	private String senderName;
	private int senderId;
	private String recieverName;
	private int recieverId;
	private int readStatus;
	private CommonDetails common;
	
	
	
	
	public JWMail() {
		super();
		this.from 	= "";
		this.to 	= "";
		this.cc 	= null;
		this.bcc 	= null;
	}
	
	public CommonDetails getCommon() {
		return common;
	}
	public void setCommon(CommonDetails common) {
		this.common = common;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public String getRecieverName() {
		return recieverName;
	}
	public void setRecieverName(String recieverName) {
		this.recieverName = recieverName;
	}
	public int getRecieverId() {
		return recieverId;
	}
	public void setRecieverId(int recieverId) {
		this.recieverId = recieverId;
	}
	public int getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}


	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getMailId() {
		return mailId;
	}
	public void setMailId(int mailId) {
		this.mailId = mailId;
	}
	
	

}
