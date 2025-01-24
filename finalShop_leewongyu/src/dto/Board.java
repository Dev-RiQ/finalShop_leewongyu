package dto;

public class Board {
	private static int num = 1;

	private int boardNum;
	private String title;
	private String id;
	private String date;
	private String contents;
	private int hits;

	public Board(String boradNum, String title, String contents, String id, String date, String hits) {
		this.boardNum = Integer.parseInt(boradNum);
		this.title = title;
		this.id = id;
		this.date = date;
		this.contents = contents;
		this.hits = Integer.parseInt(hits);
	}

	public static void plusNum() {
		num++;
	}

	public static int getNum() {
		return num;
	}

	public static void setNum(int num) {
		Board.num = num;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public String getContents() {
		return contents;
	}

	public String getDate() {
		return date;
	}

	public int getHits() {
		return hits;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return String.format("[ 제목 : %-10s 작성자 : %-7s 날짜 : %s 조회수 : %d ]", title, id, date, hits);
	}
}
