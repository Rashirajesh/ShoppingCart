package com.servlets;

public class cartItem {

	int categoryid;
	String productname;
	String price;
	public cartItem(int categoryid, String productname, String price) {
		super();
		this.categoryid = categoryid;
		this.productname = productname;
		this.price = price;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
