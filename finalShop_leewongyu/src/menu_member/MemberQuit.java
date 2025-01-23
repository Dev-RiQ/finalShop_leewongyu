package menu_member;

import _mall.MenuCommand;
import dao.MemberDAO;
import util.Util;

public class MemberQuit implements MenuCommand {

	@Override
	public void init() {
		System.out.println("=================[ "+cont.getLoginId()+" 회원탈퇴 ]=================");
		System.out.println("회원 탈퇴 시 구매 내역이 사라집니다.\n정말 탈퇴하시겠습니까?");
		System.out.println("[1] 예\n[2] 뒤로가기\n[0] 종료");
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
			MemberDAO.getInstance().deleteMember(cont.getLoginId());
			cont.setLoginId(null);
			cont.setNext("MallMain");
		} else if (sel == 2) {
			cont.setNext("MemberMain");
		} 

		return false;
	}

}
