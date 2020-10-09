package USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ADMIN.AdminDAO;

public class UserDAO extends AdminDAO {
	Scanner sc = new Scanner(System.in);
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	String c_name;
	boolean isOk = true;

	public void getConn() {
		try {// 코딩명령이 실행되었는데 오류떴을때
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";

			// 2. connection 생성
			conn = DriverManager.getConnection(url, db_id, db_pw);
			if (conn != null) {
				System.out.println();
			} else {
				System.out.println("연결 실패");
			}
		}
		// 에러가 날 경우 모든 에러들에 대해서 catch
		catch (Exception e) {
			System.out.println("getConn 오류!");
		}

	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void u_menu() {
		System.out.println();
		System.out.println("==========================MENU==========================");
		System.out.println(" [1] 차량 목록 \t [2] 주문내역 \t [3] 종료 ");
		System.out.println();
		System.out.print(">>>>>>>>>>>>>>>>>  ");
	}

	public void list() {
		getConn();
		System.out.println("=========================목록=========================");
		String sql = "select rpad(c_name,15, ' ') as c_name, rpad(c_brand,15,' ') as c_brand from car";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				String c_name = rs.getString(1);
				String c_brand = rs.getString(2);
				System.out.println("       차량이름 : " + c_name + "\t 브랜드 : " + c_brand);
			}
			System.out.println("=====================================================");
		} catch (Exception e) {

			System.out.println("오류");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void join() {
		getConn();
		try {
			System.out.println();
			System.out.println("=============회원가입=============");
			String sql = "insert into user values (?,?,?,?)";
			do {
				System.out.print("아이디를 입력하세요 : ");
				String u_id = sc.next();

				String sql1 = "select u_id from user where u_id=?";
				psmt = conn.prepareStatement(sql1);
				psmt.setString(1, u_id);
				rs = psmt.executeQuery();
				if (rs.next() == false) {
					System.out.print("패스워드를 입력하세요 : ");
					String u_pw = sc.next();
					System.out.print("이름을 입력하세요 : ");
					String u_name = sc.next();
					System.out.print("주소를 입력하세요 : ");
					String u_adr = sc.next();
					
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, u_id);
					psmt.setString(2, u_pw);
					psmt.setString(3, u_name);
					psmt.setString(4, u_adr);
					int cnt = psmt.executeUpdate();

					if (cnt > 0) {
						System.out.println("회원가입에 성공하셨습니다.");
						System.out.println();
						isOk = false;
					} else {
						System.out.println("회원가입에 실패하셨습니다.");
						System.out.println();
					}

				}else {
					System.out.println("이미 존재하는 아이디가 있습니다.");
					System.out.println();
				}
			} while (isOk);

		} catch (Exception e) {
			System.out.println("회원가입 오류");
			e.printStackTrace();
		} finally {
			close();
		}

	}

	public void login() {
		getConn();
		try {
			do {
				System.out.print("아이디를 입력하세요 >>>>> ");
				String u_id = sc.next();
				System.out.print("패스워드를 입력하세요 >>>>> ");
				String u_pw = sc.next();

				String sql = "select u_id, u_pw from user_t where u_id=? and u_pw=?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, u_id);
				psmt.setString(2, u_pw);
				rs = psmt.executeQuery();
				while (rs.next()) {
//					rs.getString(1);
//					rs.getString(2);
					if (rs.getString(1).equals(u_id)) {
						if (!rs.getString(2).equals(u_pw)) {
							System.out.println("비밀번호를 잘못입력하셨습니다.");
							System.out.println();
						}
					} else {
						System.out.println("아이디를 잘못입력하셨습니다.");
						System.out.println();
					}

					isOk = false;
				}
				String sql1 = "select u_name from user_t where u_id=?";
				psmt = conn.prepareStatement(sql1);
				psmt.setString(1, u_id);
				rs = psmt.executeQuery();
				while (rs.next()) {
					System.out.println();
					System.out.println(rs.getString("u_name") + "회원님 환영합니다!");
				}

			} while (isOk);
		}

		catch (Exception e) {
			System.out.println("로그인 오류");
			e.printStackTrace();
		} finally {
			close();
		}
	}
}