package com.inventory.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Items {
	private int itemId;
	private String itemName;
	private int itemQuantity;
	private Calendar calendar;
	private SimpleDateFormat dateFormat;

	public Items() {
	}

	public Items(String itemName, int itemQuantity) {
		super();
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.calendar = Calendar.getInstance();
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}

	public int getId() {
		return itemId;
	}

	public void setId(int id) {
		itemId = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String name) {
		itemName = name;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int quantity) {
		itemQuantity = quantity;
	}
	
	public String getTime(){
		String formattedDate = dateFormat.format(calendar.getTime());
		return formattedDate;
	}

	@Override
	public String toString() {
		return "Item [id=" + itemId + ", name=" + itemName + ", quantity=" + itemQuantity
				+ "]";
	}
}
