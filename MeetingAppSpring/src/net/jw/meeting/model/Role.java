package net.jw.meeting.model;

public class Role {
	
	
	private int roleId;
	private String roleName;
	private CommonDetails common;
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public CommonDetails getCommon() {
		return common;
	}
	public void setCommon(CommonDetails common) {
		this.common = common;
	}
	
}
