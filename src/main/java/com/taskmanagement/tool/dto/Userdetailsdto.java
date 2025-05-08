package com.taskmanagement.tool.dto;

import java.time.LocalDateTime;

import com.taskmanagement.tool.model.Userdetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;

public class Userdetailsdto {

	
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String username;
    private String password;
    private String roles;
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
    	
  
}
