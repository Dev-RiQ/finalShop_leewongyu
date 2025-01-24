package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import util.Util;

public class FileDAO {

	private static final String FILE_PATH = System.getProperty("user.dir") + "\\src\\files\\";

	/** Define file name */
	enum FileName {
		BOARD("board.txt"), MEMBER("member.txt"), ITEM("item.txt"), CART("cart.txt");

		private String name;

		FileName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private FileDAO() {}

	private static FileDAO instance;

	static public FileDAO getInstance() {
		if (instance == null) instance = new FileDAO();
		return instance;
	}

	/** Load save data all in files package */
	public void loadAllFiles() {
		BoardDAO.getInstance().setLoadData(getLoadData(FileName.BOARD));
		CartDAO.getInstance().setLoadData(getLoadData(FileName.CART));
		ItemDAO.getInstance().setLoadData(getLoadData(FileName.ITEM));
		MemberDAO.getInstance().setLoadData(getLoadData(FileName.MEMBER));
	}

	/** Save run data all to files package */
	public void saveAllFiles() {
		saveData(FileName.BOARD, BoardDAO.getInstance().getSaveData());
		saveData(FileName.CART, CartDAO.getInstance().getSaveData());
		saveData(FileName.ITEM, ItemDAO.getInstance().getSaveData());
		saveData(FileName.MEMBER, MemberDAO.getInstance().getSaveData());
	}

	/** Load each file of (file name) */
	public String getLoadData(FileName name) {
		String data = "";
		try (FileReader fr = new FileReader(FILE_PATH + name.getName()); BufferedReader br = new BufferedReader(fr)) {
			while (true) {
				String temp = br.readLine();
				if (temp == null) break;
				data += temp + "\n";
			}
			Util.showMsg("== " + name + " 데이터 로드 완료 ==");
		} catch (Exception e) {
			Util.showMsg("== " + name + " 데이터 로드 실패 ==");
		}
		return data.length() == 0 ? null : data.substring(0, data.length() - 1);
	}

	/** Save each data to (file name) */
	public void saveData(FileName name, String data) {
		if (data == null) {
			Util.showMsg("== " + name + " 데이터 저장 실패 ==");
			return;
		}
		try (FileWriter fw = new FileWriter(FILE_PATH + name.getName())) {
			fw.write(data);
			Util.showMsg("== " + name + " 데이터 저장 완료 ==");
		} catch (Exception e) {
			Util.showMsg("== " + name + " 데이터 저장 실패 ==");
		}
	}
}
