package com.smart_contact_manager.smart_contact_manager.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contact {
	public Contact() {}
	@Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String adress;
    private String picture;
    @Column(length=10000)
    private String description;
    private boolean favorite = false;
  //  private List<String>socialLinks = new ArrayList<>();
    private String instagram_link;
    private String linkedInList;
    private String cloudinaryImagePublicId;
    
    public String getCloudinaryImagePublicId() {
		return cloudinaryImagePublicId;
	}

	public void setCloudinaryImagePublicId(String cloudinaryImagePublicId) {
		this.cloudinaryImagePublicId = cloudinaryImagePublicId;
	}
	 @ManyToOne
	 @JoinColumn(name = "user_id", nullable = false) // Ensure this column exists in DB
	 @JsonIgnore 
	 private User user;
    
    @OneToMany(mappedBy="contact",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SocialLink> links = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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

	public String getLinkedInList() {
		return linkedInList;
	}

	public void setLinkedInList(String linkedInList) {
		this.linkedInList = linkedInList;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SocialLink> getLinks() {
		return links;
	}

	public void setLinks(List<SocialLink> links) {
		this.links = links;
	}
    
    
}
