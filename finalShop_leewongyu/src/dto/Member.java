package dto;

public class Member {
	private static int num = 1000;
	private int memberNum;
	private String id;
	private String pw;
	private String memberName;

	public Member(String memberNum, String id, String pw, String memberName) {
		this.memberNum = Integer.parseInt(memberNum);
		this.id = id;
		this.pw = pw;
		this.memberName = memberName;
	}

	public static int getNum() {
		return num;
	}

	public int getMemberNum() {
		return memberNum;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public String getMemberName() {
		return memberName;
	}

	public static void setNum(int num) {
		Member.num = num;
	}

	public static void plusNum() {
		num++;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public String toString() {
		return String.format("[%-5d] [%10s] [%10s] [%10s]", memberNum, id, pw, memberName);
	}

}
