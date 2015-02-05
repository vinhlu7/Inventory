package com.example.inventory;

public class Items {
	private int itemId;
	private String itemName;
	private int itemQuantity;

	public Items() {
	}

	public Items(String itemName, int itemQuantity) {
		super();
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
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

	@Override
	public String toString() {
		return "Item [id=" + itemId + ", name=" + itemName + ", quantity=" + itemQuantity
				+ "]";
	}
}
