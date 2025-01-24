package util;

import java.util.Scanner;

public class Util {

	private static Scanner sc = new Scanner(System.in);

	/** print a message */
	public static void showMsg(String msg) {
		System.out.printf("[ %s ]\n", msg);
	}

	/** print an error message */
	public static void showErrorMsg(String msg) {
		System.err.printf("[ %s ]\n", msg);
	}

	/** print (string) and get valid integer input value between (start) and (end) */
	public static int getValue(String string, int start, int end) {
		System.out.printf("▶ %s[%d-%d] 입력 : ", string, start, end);
		while (true) {
			try {
				return getInteger(start, end);
			} catch (NullPointerException e) {
				showErrorMsg(String.format("[%d ~ %d] 범위의 값을 입력하세요.", start, end));
			} catch (Exception e) {
				showErrorMsg("유효하지 않은 입력입니다.");
			} finally {
				sc.nextLine();
			}
		}
	}

	/** get scanned integer value */
	private static int getInteger(int start, int end) throws Exception {
		int num = sc.nextInt();
		if (num < start || num > end)
			throw new NullPointerException();
		return num;
	}

	/** print (string) and get valid string input value */
	public static String getValue(String string) {
		System.out.print("▶ " + string + "입력 : ");
		while (true) {
			try {
				return getString();
			} catch (NullPointerException e) {
				showErrorMsg("빈칸은 입력 불가능합니다.");
			} catch (Exception e) {
				showErrorMsg("유효하지 않은 입력입니다.");
			}
		}
	}

	/** get scanned string value */
	private static String getString() throws Exception {
		String str = sc.nextLine();
		if (str.isBlank())
			throw new NullPointerException();
		return str;
	}

}
