package dao;

import java.util.ArrayList;
import java.util.List;

import controller.MallController;
import dto.Member;
import util.Util;

public class MemberDAO {

	List<Member> memberList;

	private static MemberDAO instance;

	public static MemberDAO getInstance() {
		if (instance == null) instance = new MemberDAO();
		return instance;
	}

	private MemberDAO() {
		memberList = new ArrayList<>();
	}
	
	/** memberList has Member? */
	private boolean hasMember() {
		if (memberList.size() > 1) return true;
		Util.showErrorMsg("등록된 회원이 존재하지 않습니다.");
		return false;
	}

	/** Administer get delete valid id */
	private String getDeleteId() {
		String id = Util.getValue("삭제할 회원 아이디");
		if (isAdmin(id)) return null;
		for (Member m : memberList)
			if (m.getId().equals(id))
				return id;
		Util.showErrorMsg("존재하지 않는 아이디입니다.");
		return null;
	}

	/** check id equals admin */
	private boolean isAdmin(String id) {
		if (!id.equals("admin")) return false;
		Util.showErrorMsg("관리자 회원 삭제 불가능합니다.");
		return true;
	}

	/** get Member matching id */
	private Member getMember(String id) {
		for (Member m : memberList)
			if (m.getId().equals(id))
				return m;
		return null;
	}

	/** get ID matching PW */
	private String getId(String pw) {
		String id = isValidMember(MallController.getInstance().getLoginId(), pw);
		if (id == null)
			Util.showErrorMsg("비밀번호가 일치하지 않습니다.");
		return id;
	}

	/** get input PW is not equal parameter PW */
	private String getPw(String pw) {
		String newPw = Util.getValue("신규 패스워드");
		if (newPw.equals(pw)) {
			Util.showErrorMsg("기존과 다른 비밀번호를 입력해주세요.");
			return null;
		}
		return newPw;
	}
	
	/** get Member's id matching parameter id */
	public String getMemberById(String id) {
		for (Member m : memberList)
			if (m.getId().equals(id))
				return id;
		return null;
	}

	/** get matching login information id */
	public String isValidMember(String id, String pw) {
		for (Member m : memberList)
			if (getMemberById(id) != null && m.getPw().equals(pw))
				return id;
		return null;
	}
	
	/** change password */
	public void setPw() {
		String pw = Util.getValue("패스워드");
		String id = getId(pw);
		if (id == null) return;
		String newPw = getPw(pw);
		if (newPw == null) return;
		getMember(id).setPw(newPw);
		Util.showMsg("비밀번호 변경 완료");
	}
	
	/** add new Member */
	public boolean insertMember(String id, String pw, String name) {
		try {
			memberList.add(new Member(Member.getNum() + "", id, pw, name));
			Util.showMsg(memberList.get(memberList.size() - 1) + "추가 완료");
			Member.plusNum();
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	/** delete each member information in administer*/
	public void deleteMember() {
		if (!hasMember()) return;
		Util.showMsg("회원 삭제시 구매 내역이 사라집니다.");
		String id = getDeleteId();
		if (id != null) {
			deleteMember(id);
			return;
		}
		Util.showErrorMsg("삭제 실패");
	}
	
	/** delete each Member's information have parameter id */
	public void deleteMember(String id) {
		for (int i = 0; i < memberList.size(); i++)
			if (memberList.get(i).getId().equals(id)) {
				memberList.remove(i);
				CartDAO.getInstance().deleteAll(id);
				Util.showMsg("회원 탈퇴완료");
				return;
			}
	}

	/** print login id information */
	public void printMyInfo() {
		for (Member m : memberList)
			if (m.getId().equals(MallController.getInstance().getLoginId())) {
				System.out.println(m);
				break;
			}
	}

	/** print all member list */
	public void printAll() {
		if (!hasMember()) return;
		System.out.println("======= 전체 회원 목록 ========");
		for (Member m : memberList)
			System.out.println(m);
	}

	/** set VO from load data */
	public void setLoadData(String data) {
		String[] info = data.split("\n");
		for (int i = 0; i < info.length; i++) {
			String[] temp = info[i].split("/");
			memberList.add(new Member(temp[0], temp[1], temp[2], temp[3]));
		}
		Member.setNum(memberList.get(memberList.size() - 1).getMemberNum() + 1);
	}

	/** get run data to string by save format */
	public String getSaveData() {
		if (!hasMember()) return null;
		String data = "";
		for (Member m : memberList)
			data += String.format("%d/%s/%s/%s\n", m.getMemberNum(), m.getId(), m.getPw(), m.getMemberName());
		return data.substring(0, data.length() - 1);
	}

}
