package com.smart_contact_manager.smart_contact_manager.forms;

import org.springframework.web.multipart.MultipartFile;

public class ContactForm {
   public ContactForm(String name, String email, String phoneNumber, String adress, String description,
			boolean favorite, String instagram_link, String linkedInLink, MultipartFile picture,String pictureUrl) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.adress = adress;
		this.description = description;
		this.favorite = favorite;
		this.instagram_link = instagram_link;
		this.linkedInLink = linkedInLink;
		this.picture = picture;
		this.pictureUrl = pictureUrl;
	}
  
   private String name;
   
  
   private String email;
  // @Pattern(regexp="^[0-9]{10}$",message="Invalid Phone Number")
   private String phoneNumber;
  // @NotBlank
   private String adress;
   private String description;
   private boolean favorite;
   private String instagram_link;
   private String linkedInLink;
   private String pictureUrl;
   public String getPictureUrl() {
	return pictureUrl;
}

public void setPictureUrl(String pictureUrl) {
	this.pictureUrl = pictureUrl;
}

private MultipartFile picture;
   
public ContactForm() {}

@Override
public String toString() {
	return "ContactForm [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", adress=" + adress
			+ ", description=" + description + ", favorite=" + favorite + ", instagram_link=" + instagram_link
			+ ", linkedInLink=" + linkedInLink + ", picture=" + picture + "]";
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
public String getPhoneNumber() {
	return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}
public String getAdress() {
	return adress;
}
public void setAdress(String adress) {
	this.adress = adress;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public boolean isFavorite() {
	return favorite;
}
public void setFavorite(boolean favorite) {
	this.favorite = favorite;
}
public String getInstagram_link() {
	return instagram_link;
}
public void setInstagram_link(String instagram_link) {
	this.instagram_link = instagram_link;
}
public String getLinkedInLink() {
	return linkedInLink;
}
public void setLinkedInLink(String linkedInLink) {
	this.linkedInLink = linkedInLink;
}
public MultipartFile getPicture() {
	return picture;
}
public void setPicture(MultipartFile picture) {
	this.picture = picture;
}
   
   
}
