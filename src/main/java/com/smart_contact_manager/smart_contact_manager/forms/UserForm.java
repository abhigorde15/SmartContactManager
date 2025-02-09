package com.smart_contact_manager.smart_contact_manager.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserForm {
	public UserForm(String name, String email, String password, String about, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.phone = phone;
	}
    public UserForm() {}
    @NotBlank(message="username is required")
    @Size(min=3,message="Minimum 3 characters are required")
	private String name;
    @NotBlank(message="email is required")
    @Size(min=6,message="Minimum 6 characters are required")
	private String email;
    @NotBlank(message="password is required")
    @Size(min=6,message="Minimum 6 characters are required")
	private String password;
	private String about;
	@Size(min=8,max=12,message="invalid Phone Number")
	private String phone;

	@Override
	public String toString() {
		return "UserForm [name=" + name + ", email=" + email + ", password=" + password + ", about=" + about
				+ ", phone=" + phone + "]";
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
