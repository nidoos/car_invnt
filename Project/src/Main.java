import java.util.Scanner;

import ADMIN.AdminDAO;
import USER.UserDAO;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AdminDAO a = new AdminDAO();
		UserDAO u = new UserDAO();
		boolean isOk = true;
		
		System.out.println("------------���������α׷�------------");
		System.out.println();
		System.out.println("[1] ȸ������  \t  [2] �α���");
		System.out.print(">>>>>>>>>>>>>>>>>>>>>>  ");
		int i = sc.nextInt();
		if(i==1) {
			System.out.println();
			System.out.println("[1] ������ ȸ������ \t [2] ���� ȸ������");
			System.out.print(">>>>>>>>>>>>>>>>>> ");
			int j = sc.nextInt();
			if(j==1) {
				a.join();
			}else if(j==2) {
				u.join();
			}else {
				System.out.println("�ٽ� �������ּ���.");
			}
		}else {
			System.out.println("[1] ������ �α��� \t [2] ���� �α���");
			System.out.print(">>>>>>>>>>>>>>>>>> ");
			int j = sc.nextInt();
			if(j==1) {
				a.login();
			}else if(j==2) {
				u.login();
			}else {
				System.out.println("�ٽ� �������ּ���.");
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
