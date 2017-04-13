package net.jw.meeting.model;

public class PermissionMatrix {
	
	private int roleId;
	private int permissionId;
	
	private CommonDetails common;
	
	public PermissionMatrix()
	{
		setCommon(new CommonDetails());
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public CommonDetails getCommon() {
		return common;
	}

	public void setCommon(CommonDetails common) {
		this.common = common;
	}
	
	

}
