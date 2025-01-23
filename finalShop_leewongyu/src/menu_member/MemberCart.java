package menu_member;

import _mall.MenuCommand;
import dao.CartDAO;
import util.Util;

public class MemberCart implements MenuCommand {

	@Override
	public void init() {
		System.out.println("=================[ 구매 내역 ]=================");
		CartDAO.getInstance().printMemberCart();
		System.out.println("[1] 쇼핑하기\n[2] 뒤로가기\n[0] 종료");
		System.out.println("=====================");		
	}

	@Override
	public boolean update() {
		int sel = Util.getValue("메뉴", 0, 2);
		if (sel == 0) {
			cont.setNext(null);
		} else if (sel == 1) {
			cont.setNext("MemberShopping");
		} else if (sel == 2) {
			cont.setNext("MemberMain");
		} 
		return false;
	}
}
