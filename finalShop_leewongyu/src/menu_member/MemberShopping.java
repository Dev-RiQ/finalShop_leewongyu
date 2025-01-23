package menu_member;

import _mall.MenuCommand;
import dao.CartDAO;
import dao.ItemDAO;
import util.Util;

public class MemberShopping implements MenuCommand {

	ItemDAO dao = ItemDAO.getInstance();
	
	@Override
	public void init() {
		System.out.println("=================[ 상품 구매 ]=================");
		dao.printCategoryMenu();
		System.out.println("[0] 뒤로 가기");
		System.out.println("=====================");		
	}

	@Override
	public boolean update() {
		if(dao.getCategoryListSize() < 1)
			Util.showErrorMsg("등록된 카테고리가 없습니다.");
		int sel = Util.getValue("메뉴", 0, dao.getCategoryListSize());
		if (sel == 0) {
			cont.setNext("MemberMain");
		}else {
			dao.getListSingleItem(dao.getCategoryName(sel));
			buyItem();
		}
		return false;
	}

	private void buyItem() {
		while(true) {
			String name = Util.getValue("구매 아이템 이름");
			if(dao.getItemName(name) != null) {
				int cnt = Util.getValue("아이템 구매 수량",1,100);
				if(cnt == -1) continue;
				CartDAO.getInstance().addCart(name, cnt);
				break;
			}
			Util.showErrorMsg("입력 오류");
		}
		cont.setNext("MemberCart");
	}
}
