package menu_admin;

import _mall.MenuCommand;
import dao.FileDAO;
import util.Util;

public class _AdminMain implements MenuCommand {

	@Override
	public void init() {
		System.out.println("=================[ 관리자 ]=================");
		System.out.println("[1] 회원관리\n[2] 상품관리\n[3] 게시판관리\n[4] 로그아웃\n[5] 파일저장\n[0] 종료");
		System.out.println("=====================");
	}

	@Override
	public boolean update() {
		int sel = Util.getValue("메뉴", 0, 5);
		if (sel == 0) {
			Util.showMsg("프로그램 종료");
			cont.setNext(null);
		}
		else if (sel == 1) {
			cont.setNext("AdminMember");
		} else if (sel == 2) {
			cont.setNext("AdminItem");
		} else if (sel == 3) {
			cont.setNext("AdminBoard");
		} else if (sel == 4) {
			cont.setNext("MallMain");
			cont.setLoginId(null);
			Util.showMsg("로그아웃 성공");
		} else if (sel == 5) {
			FileDAO.getInstance().saveAllFiles();
		} 

		return false;
	}
}
