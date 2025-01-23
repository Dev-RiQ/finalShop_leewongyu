package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.MallController;
import dto.Board;
import util.Util;

public class BoardDAO {
	List<Board> boardList;
	private int showCnt;
	MallController cont;

	private static BoardDAO instance;
	public static BoardDAO getInstance() {
		if(instance == null) instance = new BoardDAO();
		return instance;
	}
 	
	private BoardDAO() {
		boardList = new ArrayList<>();
		showCnt = 5;
		cont = MallController.getInstance();
	}
	
	public void setLoadData(String data) {
		String[] info = data.split("\n");
		for(int i = 0 ; i < info.length ; i++) {
			String[] temp = info[i].split("/");
			boardList.add(new Board(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]));
		}
		Board.setNum(boardList.get(boardList.size() - 1).getBoardNum() + 1);
	}

	public void showBoard() {
		if(!hasBoard()) return;
		int curPage = 1;
		int totalPage = (boardList.size() - 1) / showCnt + 1;
		while(true) {
			showList(curPage,totalPage);
			curPage = getCurPage(curPage,totalPage);
			if(curPage == -1) break;
		}
	}
	
	private boolean hasBoard() {
		if(boardList.size() > 0)
			return true;
		Util.showErrorMsg("게시된 글이 존재하지 않습니다");
		return false;
	}
	
	private void showList(int curPage, int totalPage) {
		System.out.printf("총 게시글 %d 개\n현재페이지 [%d / %d]\n",boardList.size(),curPage,totalPage);
		for(int i = 0 ; i < showCnt; i++) {
			int idx = i + showCnt * (curPage - 1); 
			if(idx >= boardList.size()) break;
			System.out.printf("[%3d] %s\n",idx + 1,boardList.get(idx));
		}
		System.out.println("[1] 이전\n[2] 이후\n[3] 게시글보기\n[4] 뒤로가기\n[0] 종료");
	}
	
	private int getCurPage(int curPage,int totalPage) {
		int sel = Util.getValue("메뉴", 0, 4);
		if (sel == 0) {
			Util.showMsg("프로그램 종료");
			cont.setNext(null);
		} else if (sel == 1) {
			return getBeforePage(curPage);
		} else if (sel == 2) {
			return getNextPage(curPage, totalPage);
		} else if (sel == 3) {
			showSelectBoard(curPage);
			return curPage;
		} else if (sel == 4) {
			cont.setNext(cont.getNext().equals("MemberBoard") ? "MemberBoard":"AdminBoard");
		}
		return -1;
	}
	
	private int getBeforePage(int curPage) {
		if(curPage != 1)
			return curPage - 1;
		Util.showErrorMsg("이전 페이지가 없습니다");
		return 1;
	}
	private int getNextPage(int curPage,int totalPage) {
		if(curPage != totalPage)
			return curPage + 1;
		Util.showErrorMsg("다음 페이지가 없습니다");
		return curPage;
	}
	private void showSelectBoard(int curPage) {
		int listNum = curPage * showCnt;
		int sel = Util.getValue("게시글 번호", listNum - 4 , listNum > boardList.size() ? boardList.size() : listNum);
		Board board = boardList.get(sel - 1);
		board.setHits(board.getHits() + 1);
		System.out.printf("[%3d] %s\n",board.getBoardNum(),board);
		System.out.println("-------------------------------");
		System.out.println("\t\t"+board.getContents()+"\n\n");
	}

	public void addBoard() {
		Util.showMsg("게시글 추가하기");
		String title = Util.getValue("게시글 제목");
		String contents = Util.getValue("게시글 내용");
		boardList.add(new Board(Board.getNum()+"", title, contents, cont.getLoginId(), LocalDate.now().toString(), 0+""));
		Board.plusNum();
		Util.showMsg("게시글 추가 완료");
	}

	public void deleteBoard() {
		if(!hasBoard()) return;
		showMyBoard();
		int sel = Util.getValue("선택", 0, 1);
		if(sel == 1 && deleteValidBoard())
			Util.showMsg("게시글 삭제 완료");
	}
	
	public void deleteAdminBoard() {
		if(!hasBoard()) return;
		showAllBoard();
		if(deleteValidBoard())
			Util.showMsg("게시글 삭제 완료");
	}
	
	private boolean deleteValidBoard() {
		int num = Util.getValue("삭제할 게시글 번호", 1, Board.getNum() - 1);
		int idx = 0;
		for(Board b : boardList) {
			if(isValidDelete(b, num)) {
				boardList.remove(idx);
				return true;
			}
			idx++;
		}
		printDeleteError();
		return false;
	}
	private void showAllBoard() {
		for(Board b : boardList)
			System.out.printf("[%3d] %s\n",b.getBoardNum(),b);

	}
	private boolean isValidDelete(Board board, int num) {
		if(board.getBoardNum() == num && (cont.getNext().equals("AdminBoard") || board.getId().equals(cont.getLoginId())))
			return true;
		return false;
	}
	
	private void printDeleteError() {
		if(MallController.getInstance().getNext().equals("AdminBoard"))
			Util.showErrorMsg("존재하지 않는 게시글입다.");
		else
			Util.showErrorMsg("존재하지 않거나 본인 게시글이 아닙니다.");		
	}
	
	private void showMyBoard() {
		System.out.println("----------- 내 게시글 목록 --------------");
		for(Board b : boardList)
			if(b.getId().equals(cont.getLoginId()))
				System.out.printf("[%3d] %s\n%s\n--------------------\n",b.getBoardNum(),b,b.getContents());
		System.out.println("[1] 삭제\n[0] 돌아가기");
	}

	public String getSaveData() {
		String data = "";
		for(Board b : boardList)
			data += String.format("%d/%s/%s/%s/%s/%d\n",b.getBoardNum(),b.getTitle(),b.getContents(),b.getId(),b.getDate(),b.getHits());
		return data.substring(0,data.length() - 1);
	}
	
}
