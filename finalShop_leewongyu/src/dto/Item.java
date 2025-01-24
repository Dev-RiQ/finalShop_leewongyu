package dto;

public class Item {
	private static int num = 1;
	private int itemNum;
	private String categoryName;
	private String itemName;
	private int price;

	public Item(String itemNum, String categoryName, String itemName, String price) {
		this.itemNum = Integer.parseInt(itemNum);
		this.categoryName = categoryName;
		this.itemName = itemName;
		this.price = Integer.parseInt(price);
	}

	public static void plusNum() {
		num++;
	}

	public static int getNum() {
		return num;
	}

	public int getItemNum() {
		return itemNum;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getItemName() {
		return itemName;
	}

	public int getPrice() {
		return price;
	}

	public static void setNum(int num) {
		Item.num = num;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("[%-3d] [%6s] [%6s] [%9d원]", itemNum, categoryName, itemName, price);
	}

}
