package menu_member;

import _mall.MenuCommand;
import dao.BoardDAO;
import util.Util;

public class MemberBoard implements MenuCommand {

	BoardDAO dao = BoardDAO.getInstance();

	@Override
	public void init() {
		System.out.println("=================[ 게시판 ]=================");
		System.out.println("[1] 게시글보기\n[2] 게시글추가\n[3] 내게시글(삭제)\n[4] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
	}

	@Override
	public boolean update() {
		int sel = Util.getValue("메뉴", 0, 4);
		if (sel == 0) {
			Util.showMsg("프로그램 종료");
			cont.setNext(null);
		} else if (sel == 1)
			dao.showBoard();
		else if (sel == 2)
			dao.addBoard();
		else if (sel == 3)
			dao.deleteBoard();
		else if (sel == 4)
			cont.setNext("MemberMain");

		return false;
	}

}
