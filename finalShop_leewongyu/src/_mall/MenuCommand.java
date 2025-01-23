package _mall;

import controller.MallController;

public interface MenuCommand {
	MallController cont = MallController.getInstance();
	public void init();
	public boolean update();
}
