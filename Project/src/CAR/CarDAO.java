package CAR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CarDAO {
	Scanner sc = new Scanner(System.in);
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	String c_name;

	public void getConn() {
		try {// 코딩명령이 실행되었는데 오류떴을때
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";

			// 2. connection 생성
			conn = DriverManager.getConnection(url, db_id, db_pw);
			if (conn != null) {
				System.out.println("연결 성공");
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

	public void c_insert() {
		getConn();
		try {
			int choice = 0;
			System.out.println("차량을 추가 하시겠습니까?");
			System.out.println("추가[1] 종료[2]");
			choice = sc.nextInt();
			if (choice == 1) {
				do {

					System.out.print("차량 이름 : ");
					c_name = sc.next();
					System.out.print("출시년도 : ");
					int year = sc.nextInt();
					System.out.print("차종 : ");
					String c_type = sc.next();
					System.out.print("가격 : ");
					float price = sc.nextFloat();
					System.out.print("brand : ");
					String brand = sc.next();

					String sql = "insert into car values(r_num_seq.nextval,?,?,?,?,?)";

					psmt = conn.prepareStatement(sql);
					psmt.setString(1, c_name);
					psmt.setInt(2, year);
					psmt.setString(3, c_type);
					psmt.setFloat(4, price);
					psmt.setString(5, brand);

					int cnt = psmt.executeUpdate();

					if (cnt > 0)
						System.out.println("추가되었습니다.");
					else
						System.out.println("추가에 문제가 생겼습니다.");
					System.out.println("계속 하시겠습니까?");
					System.out.println("추가[1] 종료[2]");
					choice = sc.nextInt();
				} while (choice == 1);
				System.out.println("종료되었습니다.");
			} else
				System.out.println("종료되었습니다.");
		} catch (Exception e) {
			System.out.println("insert 오류");
			e.printStackTrace();
		} finally {
			close();
		}
		invnt();
	}

	public void invnt() {

		boolean isOk = false;
		String shipment = null;
		int invnt = 0;
		getConn();

		try {

			do {

				System.out.print("출하지 : ");
				shipment = sc.next();

				System.out.print("재고량 : ");
				invnt = sc.nextInt();

				if (invnt == 0) {
					System.out.println("잘못입력하셨습니다.");
					System.out.println();
				} else {
					isOk = true;
					
				}
			} while (isOk);

			String sql = "insert into grg values ((select r_num from car where c_name = ?),?,?)";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, c_name);
			psmt.setString(2, shipment);
			psmt.setInt(3, invnt);

			int cnt = psmt.executeUpdate();

			if (cnt > 0) {
				System.out.println("재고 추가 성공");
				
			} else {
				System.out.println("재고 추가 실패");
			}
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

	public void c_ovr() {
		getConn();
		try {
			int choice = 0;
			System.out.println("차량을 추가 하시겠습니까?");
			System.out.println("추가[1] 종료[2]");
			choice = sc.nextInt();
			if (choice == 1) {
				do {

					System.out.print("차량 이름 : ");
					c_name = sc.next();

					String sql = "select count(c_name) from car where c_name=?";

					psmt = conn.prepareStatement(sql);
					psmt.setString(1, c_name);
					
					rs=psmt.executeQuery();
					
					if(rs.getInt(1)>0) {
					System.out.println("중복된차량 입니다.");
					}	
				} while (choice == 1);
				System.out.println("종료되었습니다.");
			} else
				System.out.println("종료되었습니다.");
		} catch (Exception e) {
			System.out.println("insert 오류");
			e.printStackTrace();
		} finally {
			close();
		}
	}





}
