package CAR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CarDAO {
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

	public void list() {
		getConn();
		System.out.println("=================================차량목록=================================");
		String sql = "select rpad(c_num,15,' ')as c_num, rpad(c_name,15,' ') as c_name, rpad(c_brand,15,' ') as c_brand, c_ivnt from car";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				int num = rs.getInt(1);
				String c_name = rs.getString(2);
				String c_brand = rs.getString(3);
				int ivnt = rs.getInt(4);
				System.out.println("  등록번호 : " + num + "\t이름 : " + c_name + "\t브랜드 : " + c_brand + "\t재고량 : " + ivnt);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		System.out.println("========================================================================");

	}

	public void c_in() {

		getConn();
		int c_invnt = 0;
		String c_name = null;
		String c_brand = null;
		try {

			do {
				System.out.print("추가할 차량을 입력하세요 >>>>>>>>>>>>>> ");
				c_name = sc.next();
				System.out.print("추가할 차량의 브랜드를 입력하세요 >>>>>>>> ");
				c_brand = sc.next();
				System.out.print("추가할 차량의 재고량을 입력하세요 >>>>>>>> ");
				c_invnt = sc.nextInt();

				String sql1 = "select count(c_name) from car where c_name=?";
				psmt = conn.prepareStatement(sql1);
				psmt.setString(1, c_name);
				rs = psmt.executeQuery();
				rs.next();
				if (rs.getInt(1) > 0) {
					System.out.println(">>>>>> 이미 재고가 있는 차량입니다.");
					System.out.println(">>>>>> 새로운 차량을 입력해주세요");
					System.out.println();
				} else {

					String sql = "insert into car values(c_num_seq.nextval, ?,?,?)";
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, c_name);
					psmt.setString(2, c_brand);
					psmt.setInt(3, c_invnt);

					int cnt = psmt.executeUpdate();
					if (cnt > 0) {
						System.out.println("정상적으로 입력되셨습니다.");
					} else {
						System.out.println("");
					}

					if (c_invnt == 0) {
						System.out.println("잘못입력하셨습니다.");
						System.out.println();
					} else {
						System.out.println("더 추가하시겠습니까?");
						System.out.println("[1] 예 \t [2] 아니오");
						System.out.print(">>>>>>>>>>>>>>>>>>>>>>>");
						int i = sc.nextInt();
						if (i == 2) {
							isOk = false;
						}
					}

				}
			} while (isOk);

		}

		catch (Exception e) {
			System.out.println("insert 오류");
			e.printStackTrace();
		}

		// 4. 연결닫기
		// conn, psmt 역순
		finally {
			close();
		}

	}

	public void c_invn() {
		getConn();

		try {
			do {
				System.out.println("추가할 차량의 등록번호를 입력하세요 >>>>>>>>>>>");
				int num = sc.nextInt();
				System.out.println("추가할 채고량을 입력하세요 >>>>>>>>>>>>>>>>>");
				int invn = sc.nextInt();

				String sql = "insert c_ivnt into car values(?) where c_num = ? ";

				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, num);
				psmt.setInt(2, invn);

				int cnt = psmt.executeUpdate();
				if (cnt > 0) {
					System.out.println("재고가 성공적으로 추가되었습니다.");
				} else {
					System.out.println();
				}

				if (cnt == 0) {
					System.out.println("재고를 잘못 입력하셨습니다.");
					System.out.println();
				} else {
					System.out.println("재고를 더 추가하시겠습니까?");
					System.out.println("[1] 예    [2] 아니오");
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
					int i = sc.nextInt();
					if (i == 2) {
						isOk = false;
					}
				}

			} while (isOk);
		} catch (Exception e) {
			System.out.println("재고추가 오류");

			e.printStackTrace();
		} finally {
			close();
		}

	}

	public void menu() {
		System.out.println();
		System.out.println("====================================MENU====================================");
		System.out.println(" [1] 차량 목록 \t [2] 재고추가 \t [3] 새상품등록 \t [4] 주문내역\t [5] 주문취소");
		System.out.println();
		System.out.print(">>>>>>>>>>>>>>>>>  ");
	}

}
