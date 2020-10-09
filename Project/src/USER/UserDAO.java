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
		try {// �ڵ������ ����Ǿ��µ� ����������
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_id = "hr";
			String db_pw = "hr";

			// 2. connection ����
			conn = DriverManager.getConnection(url, db_id, db_pw);
			if (conn != null) {
				System.out.println();
			} else {
				System.out.println("���� ����");
			}
		}
		// ������ �� ��� ��� �����鿡 ���ؼ� catch
		catch (Exception e) {
			System.out.println("getConn ����!");
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
		System.out.println(" [1] ���� ��� \t [2] �ֹ����� \t [3] ���� ");
		System.out.println();
		System.out.print(">>>>>>>>>>>>>>>>>  ");
	}

	public void list() {
		getConn();
		System.out.println("=========================���=========================");
		String sql = "select rpad(c_name,15, ' ') as c_name, rpad(c_brand,15,' ') as c_brand from car";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				String c_name = rs.getString(1);
				String c_brand = rs.getString(2);
				System.out.println("       �����̸� : " + c_name + "\t �귣�� : " + c_brand);
			}
			System.out.println("=====================================================");
		} catch (Exception e) {

			System.out.println("����");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void join() {
		getConn();
		try {
			System.out.println();
			System.out.println("=============ȸ������=============");
			String sql = "insert into user values (?,?,?,?)";
			do {
				System.out.print("���̵� �Է��ϼ��� : ");
				String u_id = sc.next();

				String sql1 = "select u_id from user where u_id=?";
				psmt = conn.prepareStatement(sql1);
				psmt.setString(1, u_id);
				rs = psmt.executeQuery();
				if (rs.next() == false) {
					System.out.print("�н����带 �Է��ϼ��� : ");
					String u_pw = sc.next();
					System.out.print("�̸��� �Է��ϼ��� : ");
					String u_name = sc.next();
					System.out.print("�ּҸ� �Է��ϼ��� : ");
					String u_adr = sc.next();
					
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, u_id);
					psmt.setString(2, u_pw);
					psmt.setString(3, u_name);
					psmt.setString(4, u_adr);
					int cnt = psmt.executeUpdate();

					if (cnt > 0) {
						System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�.");
						System.out.println();
						isOk = false;
					} else {
						System.out.println("ȸ�����Կ� �����ϼ̽��ϴ�.");
						System.out.println();
					}

				}else {
					System.out.println("�̹� �����ϴ� ���̵� �ֽ��ϴ�.");
					System.out.println();
				}
			} while (isOk);

		} catch (Exception e) {
			System.out.println("ȸ������ ����");
			e.printStackTrace();
		} finally {
			close();
		}

	}

	public void login() {
		getConn();
		try {
			do {
				System.out.print("���̵� �Է��ϼ��� >>>>> ");
				String u_id = sc.next();
				System.out.print("�н����带 �Է��ϼ��� >>>>> ");
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
							System.out.println("��й�ȣ�� �߸��Է��ϼ̽��ϴ�.");
							System.out.println();
						}
					} else {
						System.out.println("���̵� �߸��Է��ϼ̽��ϴ�.");
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
					System.out.println(rs.getString("u_name") + "ȸ���� ȯ���մϴ�!");
				}

			} while (isOk);
		}

		catch (Exception e) {
			System.out.println("�α��� ����");
			e.printStackTrace();
		} finally {
			close();
		}
	}
}