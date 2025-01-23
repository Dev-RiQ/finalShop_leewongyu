package menu_member;

import _mall.MenuCommand;
import dao.MemberDAO;
import util.Util;

public class MemberInfo implements MenuCommand {

	@Override
	public void init() {
		System.out.println("=================[ 내정보 ]=================");
		printMyInfo();
		System.out.println("[1] 비밀번호변경\n[2] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
	}

	@Override
	public boolean update() {
		int sel = Util.getValue("메뉴", 0, 2);
		if (sel == 0) {
			Util.showMsg("프로그램 종료");
			cont.setNext(null);
		}
		else if (sel == 1) {
			MemberDAO.getInstance().setPw();
		} else if (sel == 2) {
			cont.setNext("MemberMain");
		} 

		return false;
	}

	private void printMyInfo() {
		MemberDAO.getInstance().printMyInfo();
		System.out.println("=====================");
	}
}
