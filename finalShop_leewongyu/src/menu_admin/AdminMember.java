package menu_admin;

import _mall.MenuCommand;
import dao.MemberDAO;
import util.Util;

public class AdminMember implements MenuCommand {

	@Override
	public void init() {
		System.out.println("=================[ 관리자 회원목록 ]=================");
		System.out.println("[1] 회원목록\n[2] 회원삭제\n[3] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
	}

	@Override
	public boolean update() {
		int sel = Util.getValue("메뉴", 0, 3);
		if (sel == 0) {
			Util.showMsg("프로그램 종료");
			cont.setNext(null);
		} else if (sel == 1)
			MemberDAO.getInstance().printAll();
		else if (sel == 2)
			MemberDAO.getInstance().deleteMember();
		else if (sel == 3)
			cont.setNext("AdminMain");

		return false;
	}

}
