package com.javaex.vo;

public class UserVo {
	private int no;
	private String id;
	private String passward;
	private String name;
	private String gender;
	
	
	public UserVo() {
		
	}
	
	public UserVo(int no, String name) {
		this.no = no;
		this.name = name;
		
	}
	
	public UserVo(String id, String passward, String name, String gender) {

		this.id = id;
		this.passward = passward;
		this.name = name;
		this.gender = gender;
	}
	
	public UserVo(int no, String id, String passward, String name, String gender) {
		this.no = no;
		this.id = id;
		this.passward = passward;
		this.name = name;
		this.gender = gender;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "UserVo [no=" + no + ", id=" + id + ", passward=" + passward + ", name=" + name + ", gender=" + gender
				+ "]";
	}
	
	
	
	
}
