package menu_admin;

import _mall.MenuCommand;
import dao.CartDAO;
import dao.ItemDAO;
import util.Util;

public class AdminItem implements MenuCommand {
	@Override
	public void init() {
		System.out.println("=================[ 관리자 쇼핑몰관리 ]=================");
		System.out.println("[1] 아이템 추가\n[2] 아이템 삭제\n[3] 총 매출 아이템 갯수 출력(판매량 높은순)\n[4] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
	}

	@Override
	public boolean update() {
		int sel = Util.getValue("메뉴", 0, 4);
		if (sel == 0) {
			Util.showMsg("프로그램 종료");
			cont.setNext(null);
		} else if (sel == 1)
			ItemDAO.getInstance().addItem();
		else if (sel == 2)
			ItemDAO.getInstance().deleteItem();
		else if (sel == 3)
			CartDAO.getInstance().printCartList();
		else if (sel == 4)
			cont.setNext("AdminMain");

		return false;
	}

}
