import java.util.Scanner;

import ADMIN.AdminDAO;
import USER.UserDAO;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AdminDAO a = new AdminDAO();
		UserDAO u = new UserDAO();
		boolean isOk = true;
		
		System.out.println("------------재고관리프로그램------------");
		System.out.println();
		System.out.println("[1] 회원가입  \t  [2] 로그인");
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>  ");
		int i = sc.nextInt();
		if(i==1) {
			System.out.println();
			System.out.println("[1] 관리자 회원가입 \t [2] 유저 회원가입");
			System.out.print(">>>>>>>>>>>>>>>>>> ");
			int j = sc.nextInt();
			if(j==1) {
				a.join();
			}else if(j==2) {
				u.join();
			}else {
				System.out.println("다시 선택해주세요.");
			}
		}else {
			System.out.println("[1] 관리자 로그인 \t [2] 유저 로그인");
			System.out.print(">>>>>>>>>>>>>>>>>> ");
			int j = sc.nextInt();
			if(j==1) {
				a.login();
			}else if(j==2) {
				u.login();
			}else {
				System.out.println("다시 선택해주세요.");
			}
		
//		u.list();
//		
//		
//		
//		
//		do {
//			a.a_menu();
//			int menu = sc.nextInt();
//			switch(menu) {
//			case 1 :
//				a.list();
//				break;
//			case 2:
//				a.c_invn();
//				break;
//			case 3 :
//				a.c_in();
//				break;
//			}
//			
//		}while(isOk);		
		
		
	}

	}
}
