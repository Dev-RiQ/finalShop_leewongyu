package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import dto.Cart;
import dto.Item;
import util.Util;

public class ItemDAO {
	private List<Item> itemList;
	private Set<String> categoryList;

	private static ItemDAO instance;
	public static ItemDAO getInstance() {
		if(instance == null) instance = new ItemDAO();
		return instance;
	}
	
	private ItemDAO() {
		itemList = new ArrayList<>();
		categoryList = new TreeSet<>();
	}
	
	public String getCategoryName(int idx) {
		int num = 1;
		String name = "";
		for(String s : categoryList)
			if(num++ == idx)
				name = s;
		return name;
	}
	public String getCategoryName(Cart cart) {
		for(Item i : itemList)
			if(i.getItemNum() == cart.getItemNum())
				return i.getCategoryName();
		return null;
	}
	
	public void printCategoryMenu() {
		int num = 1;
		for(String s : categoryList)
			System.out.printf("[%d] %s\n",num++,s);
	}
	
	public int getCategoryListSize() {
		return categoryList.size();
	}
	
	public void setLoadData(String data) {
		String[] info = data.split("\n");
		for(int i = 0 ; i < info.length ; i++) {
			String[] temp = info[i].split("/");
			itemList.add(new Item(temp[0], temp[1], temp[2], temp[3]));
			categoryList.add(temp[1]);
		}
		Item.setNum(itemList.get(itemList.size() - 1).getItemNum() + 1);
	}
	
	public void getListSingleItem(String category) {
		int cnt = 1;
		for(Item i : itemList)
			if(i.getCategoryName().equals(category))
				System.out.printf("[%2d] %s\n",cnt++,i);
	}
	
	public String getItemName(String name) {
		for(Item i : itemList)
			if(i.getItemName().equals(name))
				return i.getItemName();
		return null;
	}
	
	public String getItemName(int itemNum) {
		for(Item i : itemList)
			if(i.getItemNum() == itemNum)
				return i.getItemName();
		return null;
	}
	
	public int getItemNum(String name) {
		for(Item i : itemList)
			if(i.getItemName().equals(name))
				return i.getItemNum();
		return -1;
	}
	
	public int getItemPrice(int itemNum) {
		for(Item i : itemList)
			if(i.getItemNum() == itemNum)
				return i.getPrice();
		return -1;
	}

	public void addItem() {
		String itemName = getItemName();
		if(itemName == null) return;
		String categoryName = getCategoryName();
		int price = Util.getValue("가격", 100, 10000000);
		itemList.add(new Item(Item.getNum()+"", categoryName, itemName, price+""));
		Item.plusNum();
		Util.showMsg("아이템 추가 완료");
	}

	private boolean hasItem() {
		if(itemList.size() > 0)
			return true;
		Util.showErrorMsg("등록된 아이템이 존재하지 않습니다.");
		return false;
	}
	
	private String getCategoryName() {
		String categoryName = Util.getValue("카테고리 이름");
		if(getItemName(categoryName) == null)
			categoryList.add(categoryName);
		return categoryName;
	}

	private String getItemName() {
		String itemName = Util.getValue("아이템 이름");
		if(getItemName(itemName) == null)
			return itemName;
		Util.showErrorMsg("이미 존재하는 아이템입니다.");
		return null;
	}

	public void deleteItem() {
		if(!hasItem()) return;
		printAll();
		int num = Util.getValue("삭제할 아이템 번호", 1, Item.getNum() - 1);
		if(hasNum(num)) {
			deleteItem(num);
		} else
			Util.showErrorMsg("삭제 실패");
	}
	
	private void deleteItem(int num) {
		CartDAO.getInstance().deleteAll(num);
		for(int i = 0 ; i < itemList.size() ; i++)
			if(itemList.get(i).getItemNum() == num) {
				String categoryName =itemList.get(i).getCategoryName(); 
				itemList.remove(i);
				deleteCategoryName(categoryName);
				break;
			}
		Util.showMsg("아이템 삭제 완료");
	}
	
	private void deleteCategoryName(String categoryName) {
		if(getItemName(categoryName) == null)
			categoryList.remove(categoryName);
	}
	
	private boolean hasNum(int num) {
		if(getItemName(num) != null)
			return true;
		System.out.println("이미 삭제된 번호입니다.");
		return false;
	}
	
	private void printAll() {
		System.out.println("====== 카테고리별 아이템 목록 =======");
		itemList.stream().sorted(this::sort).forEach(System.out::println);
		Util.showMsg("아이템 삭제 시 구매 내역이 사라집니다.");
	}
	
	private int sort(Item i, Item k) {
		if(sortCategory(k,i) == 0)
			return sortName(i, k);
		return sortCategory(k,i);
	}
	
	private int sortName(Item i, Item k) {
		return i.getItemName().compareTo(k.getItemName());
	}
	private int sortCategory(Item i, Item k) {
		return i.getCategoryName().compareTo(k.getCategoryName());
	}
	
	public String getSaveData() {
		String data = "";
		for(Item i : itemList)
			data += String.format("%d/%s/%s/%d\n",i.getItemNum(),i.getCategoryName(),i.getItemName(),i.getPrice());
		return data.substring(0,data.length() - 1);
	}
}
