package net.jw.meeting.model;

import java.util.ArrayList;
import java.util.List;

public class Permission {
	
	private int oId;
	private String optionName;
	private String optionType;
	private String optionIcon;
	private String parentID;
	private String optionLink;
	private int optionOrder;
	
	
	private CommonDetails common;
	
	List<Permission> suboptions = new ArrayList<>();
	
	
	public Permission()
	{
		setCommon(new CommonDetails());
	}

	public int getoId() {
		return oId;
	}

	public void setoId(int oId) {
		this.oId = oId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getOptionIcon() {
		return optionIcon;
	}

	public void setOptionIcon(String optionIcon) {
		this.optionIcon = optionIcon;
	}

	public CommonDetails getCommon() {
		return common;
	}

	public void setCommon(CommonDetails common) {
		this.common = common;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getOptionLink() {
		return optionLink;
	}

	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	public List<Permission> getSuboptions() {
		return suboptions;
	}

	public void setSuboptions(List<Permission> suboptions) {
		this.suboptions = suboptions;
	}

	public int getOptionOrder() {
		return optionOrder;
	}

	public void setOptionOrder(int optionOrder) {
		this.optionOrder = optionOrder;
	}

	
	
	}
	
	
	

