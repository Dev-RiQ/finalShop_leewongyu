package dto;

public class Cart {
	private static int num = 1;
	private int cartNum;
	private String id;
	private int itemNum;
	private int itemCnt;

	public Cart(Cart c) {
		this.cartNum = c.getCartNum();
		this.id = c.getId();
		this.itemNum = c.getItemNum();
		this.itemCnt = c.getItemCnt();
	}

	public Cart(String cartNum, String id, String itemNum, String itemCnt) {
		this.cartNum = Integer.parseInt(cartNum);
		this.id = id;
		this.itemNum = Integer.parseInt(itemNum);
		this.itemCnt = Integer.parseInt(itemCnt);
	}

	public static void plusNum() {
		num++;
	}

	public static int getNum() {
		return num;
	}

	public int getCartNum() {
		return cartNum;
	}

	public String getId() {
		return id;
	}

	public int getItemNum() {
		return itemNum;
	}

	public int getItemCnt() {
		return itemCnt;
	}

	public static void setNum(int num) {
		Cart.num = num;
	}

	public void setCartNum(int cartNum) {
		this.cartNum = cartNum;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public void setItemCnt(int itemCnt) {
		this.itemCnt = itemCnt;
	}

	@Override
	public String toString() {
		return String.format("[%-3d] %s %d개", cartNum, "%6s [%6s] [%9d원]", itemCnt);
	}

}
