package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import controller.MallController;
import dto.Cart;
import util.Util;

public class CartDAO {
	List<Cart> cartList;
	ItemDAO dao;
	MallController cont;

	private static CartDAO instance;
	public static CartDAO getInstance() {
		if(instance == null) instance = new CartDAO();
		return instance;
	}
	
	private CartDAO() {
		cartList = new ArrayList<>();
		dao = ItemDAO.getInstance();
		cont = MallController.getInstance();
	}
	
	private boolean hasCart() {
		if(cartList.size() > 0)
			return true;
		Util.showErrorMsg("장바구니 내역이 존재하지 않습니다.");
		return false;
	}
	
	public void setLoadData(String data) {
		String[] info = data.split("\n");
		for(int i = 0 ; i < info.length ; i++) {
			String[] temp = info[i].split("/");
			cartList.add(new Cart(temp[0], temp[1], temp[2], temp[3]));
		}
		Cart.setNum(cartList.get(cartList.size() - 1).getCartNum() + 1);
	}
	
	public void addCart(String name, int cnt) {
		cartList.add(new Cart(Cart.getNum()+"", cont.getLoginId(), dao.getItemNum(name)+"", cnt+""));
		Cart.plusNum();
		Util.showMsg(String.format("%s %d개 구매 완료", name,cnt));
	}
	
	public int getTotalCnt() {
		int cnt = 0;
		for(Cart c : cartList)
			if(c.getId().equals(cont.getLoginId()))
				cnt += c.getItemCnt();
		return cnt;
	}
	
	public int getTotalMoney() {
		int money = 0;
		for(Cart c : cartList)
			if(c.getId().equals(cont.getLoginId()))
				money += c.getItemCnt() * dao.getItemPrice(c.getItemNum());
		return money;
	}
	
	public int getMemberCartList() {
		int cnt = 0;
		for(Cart c : cartList)
			if(c.getId().equals(cont.getLoginId()))
				cnt += c.getItemCnt();
		return cnt;
	}
	
	public void printMemberCart() {
		if(!hasCart()) return;
		for(Cart c : cartList)
			if(c.getId().equals(cont.getLoginId()))
				System.out.printf(c.toString()+" 총 %d원\n", "",dao.getItemName(c.getItemNum()),dao.getItemPrice(c.getItemNum()),getCartMoney(c.getCartNum()));
		System.out.println("=====================");
		System.out.printf("총 %d 개 (%10d 원)\n",getTotalCnt(),getTotalMoney());
		System.out.println("=====================");	
	}
	
	public int getCartMoney(int no) {
		for(Cart c : cartList)
			if(c.getCartNum() == no)
				return c.getItemCnt() * dao.getItemPrice(c.getItemNum());
		return -1;
	}
	
	public void deleteAll(String id) {
		if(!hasCart()) return;
		for(int i = 0 ; i < cartList.size() ; i++)
			if(cartList.get(i).getId().equals(id))
				cartList.remove(i--);
		Util.showMsg("회원 구매 내역 삭제 완료");
	}
	public void deleteAll(int num) {
		if(!hasCart()) return;
		for(int i = 0 ; i < cartList.size() ; i++)
			if(cartList.get(i).getItemNum() == num)
				cartList.remove(i--);
		Util.showMsg("아이템 구매 내역 삭제 완료");
	}

	public void printCartList() {
		if(!hasCart()) return;
		System.out.println("========== 판매된 아이템 목록 ==========");
		totalCart().sorted(this::sort).forEach(this::printCartItem);
	}
	
	private Stream<Cart> totalCart(){
		List<Cart> list = new ArrayList<>();
		for(Cart c : cartList) {
			boolean hasItem = false;
			for(Cart c2 : list) {
				if(c.getItemNum() == c2.getItemNum()) {
					c2.setItemCnt(c.getItemCnt() + c2.getItemCnt());
					hasItem = true;
				}
			}
			if(!hasItem) {
				list.add(new Cart(c));
				list.get(list.size() - 1).setCartNum(list.size());
			}
		}
		return list.stream();
		
	}
	
	private int sort(Cart i, Cart k) {
		if(sortCnt(k,i) == 0)
			if(sortCategory(i,k) == 0)
				return sortName(i,k);
			else
				return sortCategory(i,k);
		return sortCnt(k,i);
	}
	private int sortName(Cart i, Cart k) {
		return dao.getItemName(i.getCartNum()).compareTo(dao.getItemName(k.getCartNum()));
	}
	private int sortCategory(Cart i, Cart k) {
		return dao.getCategoryName(i).compareTo(dao.getCategoryName(k));
	}
	private int sortCnt(Cart i, Cart k) {
		return i.getItemCnt() - k.getItemCnt();
	}
	private void printCartItem(Cart i) {
		System.out.printf(i.toString()+"\n", "["+dao.getCategoryName(i)+"]",dao.getItemName(i.getItemNum()),dao.getItemPrice(i.getItemNum()));
	}
	public String getSaveData() {
		String data = "";
		for(Cart c : cartList)
			data += String.format("%d/%s/%d/%d\n",c.getCartNum(),c.getId(),c.getItemNum(),c.getItemCnt());
		return data.substring(0,data.length() - 1);
	}
}
