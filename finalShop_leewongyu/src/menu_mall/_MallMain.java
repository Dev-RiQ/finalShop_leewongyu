package menu_mall;

import _mall.MenuCommand;
import controller.MallController;
import util.Util;

public class _MallMain implements MenuCommand {
	private MallController cont;

	@Override
	public void init() {
		cont = MallController.getInstance();
		System.out.println("=====[ 쇼핑몰 ]=====");
		System.out.println("[1] 회원가입\n[2] 로그인\n[0] 종료");
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
			cont.setNext("MallJoin");

		} else if (sel == 2) {
			cont.setNext("MallLogin");
		} 

		return false;
	}

}
