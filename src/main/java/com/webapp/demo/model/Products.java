package com.webapp.demo.model;

import javax.persistence.*;

@Entity
public class Products {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	/*
	 * @Column(name="photo", length = 1000) private byte[] photo;
	 */
	private String brand;
	private String description;
	private int useryr;
	private String category;
	private boolean oldd;
	private String condi;
	private long price;
	private boolean negotiable;
	private String loc;
	private String createddate;
	private int createdby;

	public int getCreatedby() {
		return createdby;
	}

	public void setCreatedby(int createdby) {
		this.createdby = createdby;
	}

	@Lob
	private Byte[] image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUseryr() {
		return useryr;
	}

	public void setUseryr(int useryr) {
		this.useryr = useryr;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isOldd() {
		return oldd;
	}

	public void setOldd(boolean oldd) {
		this.oldd = oldd;
	}

	public String getCondi() {
		return condi;
	}

	public void setCondi(String condi) {
		this.condi = condi;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public boolean isNegotiable() {
		return negotiable;
	}

	public void setNegotiable(boolean negotiable) {
		this.negotiable = negotiable;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}

	public Byte[] getImage() {
		return image;
	}

	public void setImage(Byte[] image) {
		this.image = image;
	}
}
