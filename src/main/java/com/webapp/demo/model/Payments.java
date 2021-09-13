package com.webapp.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payments {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int buyerid;
	private long buyercardno;
	private long price;
	private int sellerid;
	private long sellercardno;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(int buyerid) {
		this.buyerid = buyerid;
	}
	public long getBuyercardno() {
		return buyercardno;
	}
	public void setBuyercardno(long buyercardno) {
		this.buyercardno = buyercardno;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getSellerid() {
		return sellerid;
	}
	public void setSellerid(int sellerid) {
		this.sellerid = sellerid;
	}
	public long getSellercardno() {
		return sellercardno;
	}
	public void setSellercardno(long sellercardno) {
		this.sellercardno = sellercardno;
	}
}
