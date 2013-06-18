package com.zdonnell.androideveapi.corporation.member.security;

public class ApiSecurityRole {
	private long roleID;
	private String roleName;

	public long getRoleID() {
		return roleID;
	}

	public void setRoleID(long roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}